import main.java.com.trading.core.Order;
import main.java.com.trading.core.OrderResult;
import main.java.com.trading.core.Portfolio;
import main.java.com.trading.core.Position;
import main.java.com.trading.feed.AsyncOrderExecutor;
import main.java.com.trading.feed.ConcurrentPriceFeed;
import main.java.com.trading.feed.OrderBook;
import main.java.com.trading.feed.PortfolioAnalyzer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RealTimeStockTradingSystem {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Real Time Stock Trading System");
        System.out.println("Let's Get started");

        //1. Create Components
        OrderBook orderBook = new OrderBook();
        ConcurrentPriceFeed priceFeed = new ConcurrentPriceFeed(4);
        AsyncOrderExecutor executor = new AsyncOrderExecutor(10, orderBook);

        // 2. Create portfolio
        Portfolio portfolio = new Portfolio(
                Arrays.asList(
                        new Position("AAPL", 100, 150.0),
                        new Position("NVD", 150, 28000.0)
                ),
                50000.0
        );

        //3. Subscribe to Prices (lambda)
        priceFeed.subscribe("AAPL", (symbol, price) -> {
            System.out.printf("AAPL: $%.2f%n", price);
        });

        // 4. Start feed
        priceFeed.start();
        Thread.sleep(5000);

        //5. Execute Orders concurrently
        List<CompletableFuture<OrderResult>> futures = Arrays.asList(
                executor.executeOrder(new Order("1", "AAPL", 151, 10, Order.Side.BUY)),
                executor.executeOrder(new Order("2", "NVD", 200, 200, Order.Side.BUY)),
                executor.executeOrder(new Order("3", "GOOGL", 2458, 5, Order.Side.SELL))
        );

        //6. Wait for all orders
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> System.out.println("All orders completed !"))
                .get();
        //7. Analyze Portfolio
        PortfolioAnalyzer analyzer = new PortfolioAnalyzer(portfolio);
        double totalValue = analyzer.calculateTotalValue(
                symbol -> 150.0 //Simple price provider
        );
        System.out.printf("Portfolio value: $%.2f%n", totalValue);

        //8. Cleanup
        priceFeed.stop();
    }
}
