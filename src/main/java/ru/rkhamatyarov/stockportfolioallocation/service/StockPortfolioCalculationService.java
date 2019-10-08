package ru.rkhamatyarov.stockportfolioallocation.service;

import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

public interface StockPortfolioCalculationService {


    /**
     * @param companyStockList list of stocks with company name and total volume
     * @return
     *      total stock portfolio
     * @throws BusinessException
     */
    StockPortfolio calculateStockProportion(CompanyStockList companyStockList) throws BusinessException;
}
