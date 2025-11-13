package main.java.com.trading.feed;

import main.java.com.trading.core.Order;
import main.java.com.trading.core.OrderResult;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncOrderExecutor implements OrderExecutor {

    private final ExecutorService executorService;
    private final OrderBook orderBook;

    public AsyncOrderExecutor(int threadPoolSize, OrderBook orderBook) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        this.orderBook = orderBook;
    }

    @Override
    public CompletableFuture<OrderResult> executeOrder(Order order) {
        return CompletableFuture.supplyAsync(() -> {
                    //Validate
                    validateOrder(order);
                    orderBook.addOrder(order);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                    return OrderResult.success(order.getId(), order.getPrice());
                }, executorService)
                .exceptionally(ex -> OrderResult.rejected(ex.getMessage()));
    }

    private void validateOrder(final Order order) {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        if (order.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid Price");
        }
    }

}
