package ru.rkhamatyarov.stockportfolioallocation.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({
        "CEO",
        "companyName",
        "exchange",
        "industry",
        "website",
        "description",
        "securityName",
        "issueType",
        "primarySicCode",
        "employees",
        "address",
        "address2",
        "state",
        "city",
        "zip",
        "country",
        "phone",
        "tags"
})
public class StockCompanySectorDto {

    /**
     * Stock symbol of the company.
     */
    private String symbol;

    /**
     * Industry sector of the company.
     */
    private String sector;
}
