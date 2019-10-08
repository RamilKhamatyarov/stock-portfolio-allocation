package ru.rkhamatyarov.stockportfolioallocation.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties({ "CEO", "companyName", "exchange", "industry", "website", "description",
"securityName", "issueType", "primarySicCode", "primarySicCode", "employees", "address", "address2",
"state", "city", "zip", "country", "phone", "tags"})
public class StockCompanySectorDto {
    private String symbol;
    private String sector;
}
