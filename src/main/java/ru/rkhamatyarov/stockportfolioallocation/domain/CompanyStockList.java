package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

import java.util.List;

@Data
public class CompanyStockList {
    private List<CompanyStockVolume> stocks;
}
