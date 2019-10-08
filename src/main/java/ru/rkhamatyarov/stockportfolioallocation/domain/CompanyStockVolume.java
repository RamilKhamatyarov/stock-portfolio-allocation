package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CompanyStockVolume {
    private String symbol;
    private Integer volume;
}
