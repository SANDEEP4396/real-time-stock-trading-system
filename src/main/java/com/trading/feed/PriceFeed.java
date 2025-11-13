package main.java.com.trading.feed;

// S- Single Responsibility Principle
// Each class has ONE reason to change
interface PriceFeed {
    void subscribe(String symbol, PriceUpdateListener listener);

    void unsubscribe(String symbol);

    void start();

    void stop();
}
