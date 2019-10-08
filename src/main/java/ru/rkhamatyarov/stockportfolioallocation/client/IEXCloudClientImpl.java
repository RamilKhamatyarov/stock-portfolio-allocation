package ru.rkhamatyarov.stockportfolioallocation.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;

import java.net.URLEncoder;

@Slf4j
@Component
public class IEXCloudClientImpl implements IEXCloudClient {
    private final String TOKEN_TEMPLATE = "?securityToken=={securityToken}";

    private final RestTemplate restTemplate;
    private final String iexCloudHost;
    private final String sectorServicePath;
    private final String latestPriceServicePath;
    private final String securityToken;

    public IEXCloudClientImpl(
            RestTemplate restTemplate,
            @Value("iex.cloud.host") String iexCloudHost,
            @Value("iex.cloud.sector.service") String sectorServicePath,
            @Value("iex.cloud.price.service") String latestPriceServicePath,
            @Value("iex.cloud.token") String securityToken
    ) {
        this.restTemplate = restTemplate;
        this.iexCloudHost = iexCloudHost;
        this.sectorServicePath = sectorServicePath;
        this.latestPriceServicePath = latestPriceServicePath;
        this.securityToken = securityToken;
    }

    @Override
    public StockCompanySectorDto getSector(String companyName) {
        log.debug("Get sector url {} ", URLEncoder.encode(iexCloudHost + sectorServicePath));
        return restTemplate.getForObject(
                iexCloudHost + sectorServicePath + TOKEN_TEMPLATE, // https://cloud.iexapis.com/stable/stock/MRSN/company?token=<your-token>
                StockCompanySectorDto.class,
                companyName,
                securityToken
        );
    }

    @Override
    public Double getLatestPrice(String companyName) {
        log.debug("Get sector url {} ", URLEncoder.encode(iexCloudHost + latestPriceServicePath));
        return restTemplate.getForObject(
                iexCloudHost + latestPriceServicePath + TOKEN_TEMPLATE,
                Double.class,
                companyName,
                securityToken
        );
    }
}
