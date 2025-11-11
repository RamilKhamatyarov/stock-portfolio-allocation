package ru.rkhamatyarov.stockportfolioallocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class StockPortfolioAllocationApplication {

    // Private constructor to prevent instantiation
    protected StockPortfolioAllocationApplication() {
        // This constructor is intentionally empty
    }

    /**
     * Entry point for the Stock Portfolio Allocation application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(
                StockPortfolioAllocationApplication.class,
                args
        );
    }

}
