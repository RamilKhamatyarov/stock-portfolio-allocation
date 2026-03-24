package ru.rkhamatyarov.stockportfolioallocation.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.SectorProportion;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.service.StockPortfolioCalculationService;
import tools.jackson.databind.json.*;


import java.nio.charset.*;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Java6Assertions.assertThat;

@WebMvcTest(PostStockPortfolioController.class)
public class PostStockPortfolioControllerTest {

    @MockitoBean
    private StockPortfolioCalculationService stockPortfolioCalculationService;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<StockPortfolio> jsonStockPortfolio;

    @BeforeEach
    public void setUp() throws Exception {
        JsonMapper jsonMapper = JsonMapper.builder().build();
        JacksonTester.initFields(this, jsonMapper);
    }

    @Test
    public void getStockPortfolio() throws Exception {
        // given
        CompanyStockList companyStockList = new CompanyStockList();


        SectorProportion bestSector = SectorProportion.builder().sector("sectore").assetValue(50).proportion(100d).build();
        StockPortfolio stockPortfolio = StockPortfolio.builder().value(42).allocations(Collections.singletonList(bestSector)).build();

        given(stockPortfolioCalculationService.calculateStockProportion(companyStockList)).willReturn(stockPortfolio);

        // when
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", StandardCharsets.UTF_8);
        MockHttpServletResponse rs = mockMvc.perform(
                post("/stock-portfolio-allocation/v1/stock-portfolio/calculate")
                        .accept(MEDIA_TYPE_JSON_UTF8)
                        .contentType(MEDIA_TYPE_JSON_UTF8)
                        .content(jsonStockPortfolio.write(stockPortfolio).getJson()))
                .andReturn().getResponse();

        // assert
        assertThat(rs.getStatus()).isEqualTo(OK.value());
        assertThat(rs.getContentAsString()).isEqualTo(jsonStockPortfolio.write(stockPortfolio).getJson());
    }
}