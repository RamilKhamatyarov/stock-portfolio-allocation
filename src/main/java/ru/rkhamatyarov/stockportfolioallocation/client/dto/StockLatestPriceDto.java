package ru.rkhamatyarov.stockportfolioallocation.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class StockLatestPriceDto {
    private BigDecimal latestPrice;
}
