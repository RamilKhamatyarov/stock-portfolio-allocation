package ru.rkhamatyarov.stockportfolioallocation.client;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;

@Slf4j
@Component
public class IEXCloudClientImpl implements IEXCloudClient {
    private final String TOKEN_TEMPLATE = "?token=";
    private final RestClient restClient;
    private final String iexCloudHost;
    private final String sectorServicePath;
    private final String latestPriceServicePath;
    private final String securityToken;

    public IEXCloudClientImpl(
            RestClient restClient,
            @Value("${iex.cloud.host}") String iexCloudHost,
            @Value("${iex.cloud.sector.service}") String sectorServicePath,
            @Value("${iex.cloud.price.service}") String latestPriceServicePath,
            @Value("${iex.cloud.token}") String securityToken
    ) {
        this.restClient = restClient;
        this.iexCloudHost = iexCloudHost;
        this.sectorServicePath = sectorServicePath;
        this.latestPriceServicePath = latestPriceServicePath;

        checkToken(securityToken);
        this.securityToken = securityToken;
    }

    @Override
    public StockCompanySectorDto getSector(@NonNull String companyName) {
        String restUri = buildUri(iexCloudHost + sectorServicePath, companyName);

        return restClient
                .get()
                .uri(restUri + TOKEN_TEMPLATE + securityToken)
                .retrieve()
                .body(StockCompanySectorDto.class);
    }

    @Override
    public Double getLatestPrice(@NonNull String companyName) {
        String restUri = buildUri(iexCloudHost + latestPriceServicePath, companyName);

        return restClient
                .get()
                .uri(restUri + TOKEN_TEMPLATE + securityToken)
                .retrieve()
                .body(Double.class);
    }

    private String buildUri(String baseUri, String companyName) {
        return baseUri.replace("{companyName}", companyName);
    }

    private void checkToken(String securityToken) throws IllegalStateException {
        if (securityToken == null || securityToken.isEmpty() ||
                securityToken.contains("<") || securityToken.contains(">")) {
            throw new IllegalStateException("Set your IEX Cloud token.");
        }
    }
}
