package ru.rkhamatyarov.stockportfolioallocation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.rkhamatyarov.stockportfolioallocation.config.TestRestClientConfig;

@SpringBootTest
@Import(TestRestClientConfig.class)
@TestPropertySource(properties = "iex.cloud.token=test")
public class StockPortfolioAllocationApplicationTests {

	@Test
	public void contextLoads() {
	}

}
