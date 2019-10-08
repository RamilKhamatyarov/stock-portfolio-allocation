package ru.rkhamatyarov.stockportfolioallocation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClient;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;
import ru.rkhamatyarov.stockportfolioallocation.domain.*;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class StockPortfolioCalculationServiceImpl implements StockPortfolioCalculationService {

    @Autowired
    private IEXCloudClient iexCloudClient;

    @Override
    public StockPortfolio calculateStockProportion(CompanyStockList companyStockList) throws BusinessException {
        if (companyStockList.getStocks() == null ||
                companyStockList.getStocks().isEmpty()) {
            String errorMessage = "Input stock is null.";
            throw new BusinessException(errorMessage);
        }


        List<StockCompany> stockCompanyList = companyStockList.getStocks().stream()
                .filter(companyStockVolume -> companyStockVolume.getVolume() > 0)
                // Получить для каждой акции портфеля текущую котировку (latestPrice) и сектор (sector), в котором работает компания,
                // используя API https://iexcloud.io/ (использовать бесплатный план на 500000 запросов в месяц)
                .map(companyStockVolume -> StockCompany.builder()
                        .sector(iexCloudClient.getSector(companyStockVolume.getSymbol()).getSector())
                        .companyName(companyStockVolume.getSymbol())
                        .latestPrice(iexCloudClient.getLatestPrice(companyStockVolume.getSymbol()))
                        .volume(companyStockVolume.getVolume())
                        .build())
                .collect(Collectors.toList());

        // Рассчитать для каждой акции ее текущую стоимость (assetValue) в составе портфеля по формуле volume * latestPrice.
        List<SectorProportion> sectorProportionList = stockCompanyList.stream()
                .map(stockCompany -> SectorProportion.builder()
                        .sector(stockCompany.getSector())
                        .assetValue(Double.valueOf(stockCompany.getLatestPrice() * stockCompany.getVolume()).intValue())
                        .build())
                .collect(Collectors.toList());

        Map<String, Integer> sectorAssetValueMap = sectorProportionList.stream()
                .collect(
                        Collectors.groupingBy(SectorProportion::getSector,
                                Collectors.summingInt(SectorProportion::getAssetValue))
                );

        // Посчитать суммарную стоимость портфеля (value), выполнив sum(assetValue) по всем акциям
        Integer value = sectorAssetValueMap.values().stream().reduce(0, Integer::sum);

        Map<String, Double> proportionValueMap = sectorAssetValueMap.entrySet().stream().collect(
                Collectors.toMap(
                        sector -> sector.getKey(),
                        assetValue -> {
                            Double proportion = assetValue.getValue() / new Double(value) * 100;
                            return proportion;
                        }
                )
        );

        Map<String, List<SectorProportion>> allocationMap = sectorProportionList.stream()
                .collect(
                        Collectors.groupingBy(SectorProportion::getSector)
                );

        return StockPortfolio.builder()
                .value(value)
                .allocations(
                        allocationMap.keySet().stream().map(i -> SectorProportion.builder()
                                .sector(i)
                                .assetValue(sectorAssetValueMap.get(i))
                                .proportion(proportionValueMap.get(i))
                                .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }
}
