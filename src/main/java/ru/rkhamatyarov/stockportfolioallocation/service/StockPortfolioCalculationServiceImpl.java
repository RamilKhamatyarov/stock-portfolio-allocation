package ru.rkhamatyarov.stockportfolioallocation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClient;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.SectorProportion;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockCompany;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

@Service
@Slf4j
@AllArgsConstructor
public final class StockPortfolioCalculationServiceImpl
        implements StockPortfolioCalculationService {

    private static final int PERCENTAGE_MULTIPLIER = 100;

    /**
     * IEX Cloud API client for fetching stock data.
     */
    private final IEXCloudClient iexCloudClient;

    @Override
    public StockPortfolio calculateStockProportion(
            final CompanyStockList companyStockList
    ) throws BusinessException {
        if (companyStockList.getStocks() == null
                || companyStockList.getStocks().isEmpty()) {
            throw new BusinessException("Input stock is null.");
        }

        List<StockCompany> stockCompanyList = companyStockList.getStocks()
                .stream()
                .filter(companyStockVolume -> companyStockVolume
                        .getVolume() > 0)
                .map(companyStockVolume -> StockCompany.builder()
                        .sector(iexCloudClient
                                .getSector(companyStockVolume.getSymbol())
                                .getSector())
                        .companyName(companyStockVolume.getSymbol())
                        .latestPrice(iexCloudClient
                                .getLatestPrice(companyStockVolume.getSymbol()))
                        .volume(companyStockVolume.getVolume())
                        .build())
                .toList();

        List<SectorProportion> sectorProportionList = stockCompanyList
                .stream()
                .map(stockCompany -> SectorProportion.builder()
                        .sector(stockCompany.getSector())
                        .assetValue(Double.valueOf(
                                stockCompany.getLatestPrice()
                                        * stockCompany.getVolume()
                        ).intValue())
                        .build())
                .toList();

        Map<String, Integer> sectorAssetValueMap = sectorProportionList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                SectorProportion::getSector,
                                Collectors.summingInt(SectorProportion::getAssetValue)
                        )
                );

        Integer value = sectorAssetValueMap.values()
                .stream()
                .reduce(0, Integer::sum);

        Map<String, Double> proportionValueMap = sectorAssetValueMap
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                assetValue -> assetValue.getValue()
                                        / Double.valueOf(value) * PERCENTAGE_MULTIPLIER
                        )
                );

        Map<String, List<SectorProportion>> allocationMap =
                sectorProportionList
                        .stream()
                        .collect(
                                Collectors.groupingBy(SectorProportion::getSector)
                        );

        return StockPortfolio.builder()
                .value(value)
                .allocations(
                        allocationMap.keySet()
                                .stream()
                                .map(sector -> SectorProportion.builder()
                                        .sector(sector)
                                        .assetValue(sectorAssetValueMap.get(sector))
                                        .proportion(proportionValueMap.get(sector))
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
