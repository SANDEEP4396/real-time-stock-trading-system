package main.java.com.trading.core;

import java.util.Map;
import java.util.Queue;

public class OrderBookSnapShort {
    private final Map<Double, Queue<Order>> buyOrders;
    private final Map<Double, Queue<Order>> sellOrders;

    public OrderBookSnapShort(Map<Double, Queue<Order>> buyOrders, Map<Double, Queue<Order>> sellOrders) {
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }

    public Map<Double, Queue<Order>> getBuyOrders() {
        return buyOrders;
    }

    public Map<Double, Queue<Order>> getSellOrders() {
        return sellOrders;
    }
}
