package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

@Data
public class CompanyStockVolume {

    /**
     * Stock symbol of the company.
     */
    private String symbol;

    /**
     * Trading volume of the stock.
     */
    private Integer volume;
}
