package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectorProportion {

    /**
     * Stock of sector
     */
    private String sector;

    /**
     * Stock asset value
     */
    private Integer assetValue;

    /**
     * Stock proportion
     */
    private Double proportion;
}
