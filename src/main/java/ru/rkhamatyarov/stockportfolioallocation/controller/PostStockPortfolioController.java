package ru.rkhamatyarov.stockportfolioallocation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;
import ru.rkhamatyarov.stockportfolioallocation.service.StockPortfolioCalculationService;

@Slf4j
@RestController
@RequestMapping("/stock-portfolio-allocation/v1")
public class PostStockPortfolioController {

    @Autowired
    private StockPortfolioCalculationService stockPortfolioCalculationService;

    @PostMapping("/stock-portfolio/calculate")
    public StockPortfolio getStockPortfolio(@RequestBody CompanyStockList companyStockList) throws BusinessException {
        return stockPortfolioCalculationService.calculateStockProportion(companyStockList);
    }

}
