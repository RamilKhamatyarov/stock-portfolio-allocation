package ru.rkhamatyarov.stockportfolioallocation.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockCompany {

    /**
     * Industry or market sector.
     */
    private String sector;

    /**
     * Company name.
     */
    private String companyName;

    /**
     * Latest stock price.
     */
    private Double latestPrice;

    /**
     * Trading volume.
     */
    private Integer volume;

}
