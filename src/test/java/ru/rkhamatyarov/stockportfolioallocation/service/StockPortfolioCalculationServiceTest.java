package ru.rkhamatyarov.stockportfolioallocation.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClient;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClientImpl;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockLatestPriceDto;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockVolume;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockPortfolioCalculationServiceTest {

    @MockBean
    private IEXCloudClient iexCloudClient;

    @Autowired
    private StockPortfolioCalculationService stockPortfolioCalculationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(true);
    }

    @Test
    public void calculateStockProportionSuccess() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString())).thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString())).thenReturn(1.5);

        //when
        CompanyStockList companyStockList = new CompanyStockList();
        CompanyStockVolume companyStockVolume = new CompanyStockVolume();
        companyStockVolume.setSymbol("company");
        companyStockVolume.setVolume(40);
        companyStockList.setStocks(Collections.singletonList(companyStockVolume));

        StockPortfolio stockPortfolio = stockPortfolioCalculationService.calculateStockProportion(companyStockList);

        //then
        assertNotNull(stockPortfolio);
        assertEquals(stockCompanySectorDto.getSector(), stockPortfolio.getAllocations().get(0).getSector());
        assertEquals(Integer.valueOf(60), stockPortfolio.getAllocations().get(0).getAssetValue());
        assertEquals(Double.valueOf(100), stockPortfolio.getAllocations().get(0).getProportion());
    }

    @Test
    public void calculateStockProportionFail() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString())).thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString())).thenReturn(1.5);

        //when
        CompanyStockList companyStockList = new CompanyStockList();
        CompanyStockVolume companyStockVolume = new CompanyStockVolume();
        companyStockVolume.setSymbol("company");
        companyStockVolume.setVolume(-1);
        companyStockList.setStocks(Collections.singletonList(companyStockVolume));

        StockPortfolio stockPortfolio = stockPortfolioCalculationService.calculateStockProportion(companyStockList);

        //then
        assertNotNull(stockPortfolio);
        assertEquals(0, stockPortfolio.getAllocations().size());
    }

    @Test(expected = BusinessException.class)
    public void calculateStockProportionException() throws BusinessException {
        //given
        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
        stockCompanySectorDto.setSector("Healthcare");
        stockCompanySectorDto.setSymbol("MRSN");

        when(iexCloudClient.getSector(anyString())).thenReturn(stockCompanySectorDto);
        when(iexCloudClient.getLatestPrice(anyString())).thenReturn(1.5);

        //when
        CompanyStockList companyStockList = new CompanyStockList();

        StockPortfolio stockPortfolio = stockPortfolioCalculationService.calculateStockProportion(companyStockList);

        //then
        assertNotNull(stockPortfolio);
        assertEquals(stockCompanySectorDto.getSector(), stockPortfolio.getAllocations().get(0).getSector());
        assertEquals(Integer.valueOf(60), stockPortfolio.getAllocations().get(0).getAssetValue());
        assertEquals(Double.valueOf(100), stockPortfolio.getAllocations().get(0).getProportion());
    }
}