package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
public class SectorProportion {
    private String sector;
    private Integer assetValue;
    private Double proportion;
}
