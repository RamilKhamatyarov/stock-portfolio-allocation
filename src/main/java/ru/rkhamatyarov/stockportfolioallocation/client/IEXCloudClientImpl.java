package ru.rkhamatyarov.stockportfolioallocation.client;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.rkhamatyarov.stockportfolioallocation.client.dto
        .StockCompanySectorDto;

@Slf4j
@Component
public final class IEXCloudClientImpl implements IEXCloudClient {

    /**
     * URL query parameter template for API token.
     */
    private static final String TOKEN_TEMPLATE = "?token=";

    /**
     * REST client for making HTTP requests.
     */
    private final RestClient restClient;

    /**
     * IEX Cloud API host URL.
     */
    private final String iexCloudHost;

    /**
     * Sector service path endpoint.
     */
    private final String sectorServicePath;

    /**
     * Latest price service path endpoint.
     */
    private final String latestPriceServicePath;

    /**
     * IEX Cloud security token for authentication.
     */
    private final String securityToken;

    /**
     * Constructs IEXCloudClientImpl with required dependencies.
     *
     * @param client the REST client
     * @param host the IEX Cloud host URL
     * @param sectorService the sector service path
     * @param priceService the latest price service path
     * @param token the security token
     * @throws IllegalStateException if security token is invalid
     */
    public IEXCloudClientImpl(
            final RestClient client,
            @Value("${iex.cloud.host}") final String host,
            @Value("${iex.cloud.sector.service}")
            final String sectorService,
            @Value("${iex.cloud.price.service}")
            final String priceService,
            @Value("${iex.cloud.token}") final String token
    ) {
        this.restClient = client;
        this.iexCloudHost = host;
        this.sectorServicePath = sectorService;
        this.latestPriceServicePath = priceService;

        validateToken(token);
        this.securityToken = token;
    }

    @Override
    public StockCompanySectorDto getSector(
            @NonNull final String companyName
    ) {
        String restUri = buildUri(
                iexCloudHost + sectorServicePath,
                companyName
        );

        return restClient
                .get()
                .uri(restUri + TOKEN_TEMPLATE + securityToken)
                .retrieve()
                .body(StockCompanySectorDto.class);
    }

    @Override
    public Double getLatestPrice(
            @NonNull final String companyName
    ) {
        String restUri = buildUri(
                iexCloudHost + latestPriceServicePath,
                companyName
        );

        return restClient
                .get()
                .uri(restUri + TOKEN_TEMPLATE + securityToken)
                .retrieve()
                .body(Double.class);
    }

    /**
     * Builds URI by replacing company name placeholder.
     *
     * @param baseUri the base URI template
     * @param companyName the company name to replace
     * @return the complete URI
     */
    private String buildUri(
            final String baseUri,
            final String companyName
    ) {
        return baseUri.replace("{companyName}", companyName);
    }

    /**
     * Validates security token format.
     *
     * @param token the security token to validate
     * @throws IllegalStateException if token is invalid
     */
    private void validateToken(final String token)
            throws IllegalStateException {
        if (token == null
                || token.isEmpty()
                || token.contains("<")
                || token.contains(">")) {
            throw new IllegalStateException(
                    "Set your IEX Cloud token."
            );
        }
    }
}
