package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

import java.util.List;

@Data
public class CompanyStockList {

    /**
     * Company volume collections
     */
    private List<CompanyStockVolume> stocks;
}
