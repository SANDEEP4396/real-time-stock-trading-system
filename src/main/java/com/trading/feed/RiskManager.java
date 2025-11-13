package main.java.com.trading.feed;

import main.java.com.trading.core.Order;
import main.java.com.trading.core.Portfolio;

public interface RiskManager {
    boolean validateOrder(Order order, Portfolio portfolio);
    double calculatePositionSize(String symbol, double accountBalance);
}
