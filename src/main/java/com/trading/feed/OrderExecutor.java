package main.java.com.trading.feed;

import main.java.com.trading.core.Order;
import main.java.com.trading.core.OrderResult;

import java.util.concurrent.CompletableFuture;

//I - Interface Segregation Principle
// Clients shouldn't depend on interfaces they do no use
interface OrderExecutor {
    CompletableFuture<OrderResult> executeOrder(Order order);
}
