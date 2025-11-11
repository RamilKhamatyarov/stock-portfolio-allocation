package ru.rkhamatyarov.stockportfolioallocation.client.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockLatestPriceDto {

    /**
     * Latest price of the stock.
     */
    private BigDecimal latestPrice;
}
