package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

import java.util.List;

@Data
public class CompanyStockList {

    /**
     * List of company stocks with their volumes.
     */
    private List<CompanyStockVolume> stocks;
}
