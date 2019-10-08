package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockCompany {
    private String sector;
    private String companyName;
    private Double latestPrice;
    private Integer volume;
}
