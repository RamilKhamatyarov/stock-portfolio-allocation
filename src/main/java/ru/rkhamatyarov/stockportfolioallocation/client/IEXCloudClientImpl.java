package ru.rkhamatyarov.stockportfolioallocation.client;

import com.sun.jersey.api.client.WebResource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rkhamatyarov.stockportfolioallocation.client.dto.StockCompanySectorDto;
import ru.rkhamatyarov.stockportfolioallocation.exception.BusinessException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class IEXCloudClientImpl implements IEXCloudClient {
    private final String TOKEN_TEMPLATE = "?token=";


    private final Client client;
    private final String iexCloudHost;
    private final String sectorServicePath;
    private final String latestPriceServicePath;
    private final String securityToken;

    public IEXCloudClientImpl(
            @Value("${iex.cloud.host}") String iexCloudHost,
            @Value("${iex.cloud.sector.service}") String sectorServicePath,
            @Value("${iex.cloud.price.service}") String latestPriceServicePath,
            @Value("${iex.cloud.token}") String securityToken
    ) {
        this.client = ClientBuilder.newClient();
        this.iexCloudHost = iexCloudHost;
        this.sectorServicePath =sectorServicePath;
        this.latestPriceServicePath = latestPriceServicePath;

        checkToken(securityToken);
        this.securityToken = securityToken;
    }

    @Override
    public StockCompanySectorDto getSector(@NonNull String companyName) {
        String restUri = iexCloudHost + sectorServicePath;

        return client
                .target(mapVariablesToUri(restUri, companyName) + TOKEN_TEMPLATE + securityToken)
                .path(companyName)
                .request(MediaType.APPLICATION_JSON)
                .get(StockCompanySectorDto.class);

    }

    @Override
    public Double getLatestPrice(@NonNull String companyName) {
        String restUri = iexCloudHost + latestPriceServicePath;

        return client
                .target(mapVariablesToUri(restUri, companyName) + TOKEN_TEMPLATE + securityToken)
                .path(companyName)
                .request(MediaType.APPLICATION_JSON)
                .get(Double.class);
    }


    private URI mapVariablesToUri(String restUri, String companyName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("companyName", companyName);
        UriBuilder builder = UriBuilder.fromPath(restUri);
        return builder.buildFromMap(parameters);
    }

    private void checkToken(String securityToken) throws IllegalStateException {
        if (securityToken == null || securityToken.isEmpty() ||
                securityToken.contains("<") || securityToken.contains(">")) {
            throw new IllegalStateException("Set your IEX Cloud token.");
        }
    }
}
