package ru.rkhamatyarov.stockportfolioallocation.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.rkhamatyarov.stockportfolioallocation.client.IEXCloudClient;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockLatestPriceDto;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

public class StockPortfolioCalculationServiceTest {

    @Mock
    private IEXCloudClient iexCloudClient;

    @Autowired
    private StockPortfolioCalculationService stockPortfolioCalculationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(true);
    }

    @Test
    public void calculateStockProportion() throws BusinessException {
        //given
//        StockCompanySectorDto stockCompanySectorDto = new StockCompanySectorDto();
//        stockCompanySectorDto.setSector("Healthcare");
//        stockCompanySectorDto.setSymbol("MRSN");
//        given(iexCloudClient.getSector(anyString())).willReturn(stockCompanySectorDto);
//
//        given(iexCloudClient.getLatestPrice(anyString())).willReturn(1.465);
//
//        //when
//        CompanyStockList companyStockList = new CompanyStockList();
//        StockPortfolio stockPortfolio = stockPortfolioCalculationService.calculateStockProportion(companyStockList);
//
//        //then
//        assertNotNull(stockPortfolio);
//        assertEquals(stockCompanySectorDto.getSector(), stockPortfolio.getAllocations().get(0).getSector());
//        assertEquals(Integer.valueOf(1), stockPortfolio.getAllocations().get(0).getAssetValue());
//        assertEquals(Double.valueOf(100), stockPortfolio.getAllocations().get(0).getProportion());
    }
}