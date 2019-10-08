package ru.rkhamatyarov.stockportfolioallocation.client;

import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;

public interface IEXCloudClient {

    /**
     * @param companyName name of company
     * @return
     *     dto with name of economic sector
     */
    StockCompanySectorDto getSector(String companyName);

    /**
     * @param companyName name of company
     * @return
     *      dto with stock latest price
     */
    Double getLatestPrice(String companyName);
}
