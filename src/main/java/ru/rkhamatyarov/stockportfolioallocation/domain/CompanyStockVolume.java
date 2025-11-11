package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

@Data
public class CompanyStockVolume {

    /**
     * symbol of stock
     */
    private String symbol;

    /**
     * Volume of stock
     */
    private Integer volume;
}
