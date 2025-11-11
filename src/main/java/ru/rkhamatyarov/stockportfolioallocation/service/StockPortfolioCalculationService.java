package ru.rkhamatyarov.stockportfolioallocation.service;

import ru.rkhamatyarov.stockportfolioallocation.domain.CompanyStockList;
import ru.rkhamatyarov.stockportfolioallocation.domain.StockPortfolio;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

public interface StockPortfolioCalculationService {

    /**
     * Calculates stock proportion for the given company stock list.
     *
     * @param companyStockList list of stocks with company name and volume
     * @return total stock portfolio with calculated allocations
     * @throws BusinessException if calculation fails
     */
    StockPortfolio calculateStockProportion(
            CompanyStockList companyStockList
    ) throws BusinessException;
}
