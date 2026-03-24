package ru.rkhamatyarov.stockportfolioallocation.service;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.*;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClient;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockVolume;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class StockPortfolioCalculationServiceTest {

    @MockitoBean
    private IEXCloudClient iexCloudClient;

    @Autowired
    private StockPortfolioCalculationService stockPortfolioCalculationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(true);
    }

    /**
     * Test calculating proportion success
     * @throws BusinessException - custom exception
     */
    @Test
    public void calculateStockProportionSuccess() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString()))
                .thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString()))
                .thenReturn(1.5);

        //when
        CompanyStockList companyStockList = new CompanyStockList();
        CompanyStockVolume companyStockVolume = new CompanyStockVolume();
        companyStockVolume.setSymbol("company");
        companyStockVolume.setVolume(40);
        companyStockList.setStocks(Collections.singletonList(companyStockVolume));

        StockPortfolio stockPortfolio = stockPortfolioCalculationService
                .calculateStockProportion(companyStockList);

        //then
        assertNotNull(stockPortfolio);
        assertEquals(
                stockCompanySectorDto.getSector(),
                stockPortfolio.getAllocations().getFirst().getSector()
        );
        assertEquals(
                Integer.valueOf(60),
                stockPortfolio.getAllocations().getFirst().getAssetValue()
        );
        assertEquals(
                Double.valueOf(100),
                stockPortfolio.getAllocations().getFirst().getProportion()
        );
    }

    /**
     * Failed stock proportion failing
     * @throws BusinessException - custom exception
     */
    @Test
    public void calculateStockProportionFail() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString()))
                .thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString()))
                .thenReturn(1.5);

        //when
        CompanyStockList companyStockList = new CompanyStockList();
        CompanyStockVolume companyStockVolume = new CompanyStockVolume();
        companyStockVolume.setSymbol("company");
        companyStockVolume.setVolume(-1);
        companyStockList.setStocks(
                Collections.singletonList(companyStockVolume)
        );

        StockPortfolio stockPortfolio = stockPortfolioCalculationService
                .calculateStockProportion(companyStockList);

        //then
        assertNotNull(stockPortfolio);
        assertEquals(0, stockPortfolio.getAllocations().size());
    }

    /**
     * Stock proportion falling with exception
     * @throws BusinessException - custom exception
     */
    @Test
    public void calculateStockProportionException() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString()))
                .thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString()))
                .thenReturn(1.5);

        CompanyStockList companyStockList = new CompanyStockList();

        //when & then
        assertThrows(BusinessException.class, () ->
                stockPortfolioCalculationService.calculateStockProportion(companyStockList)
        );
    }
}
