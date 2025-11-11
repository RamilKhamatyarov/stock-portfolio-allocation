package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectorProportion {

    /**
     * Industry or market sector.
     */
    private String sector;

    /**
     * Asset value of the sector in the portfolio.
     */
    private Integer assetValue;

    /**
     * Proportion percentage of the sector.
     */
    private Double proportion;
}
