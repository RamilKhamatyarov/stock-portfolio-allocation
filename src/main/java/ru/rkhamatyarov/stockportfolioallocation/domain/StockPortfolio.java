package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StockPortfolio {
    private Integer value;
    private List<SectorProportion> allocations;
}
