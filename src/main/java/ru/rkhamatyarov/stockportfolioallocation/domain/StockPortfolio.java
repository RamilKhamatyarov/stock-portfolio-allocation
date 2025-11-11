package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StockPortfolio {

    /**
     * Total portfolio value.
     */
    private Integer value;

    /**
     * List of sector allocations in the portfolio.
     */
    private List<SectorProportion> allocations;
}
