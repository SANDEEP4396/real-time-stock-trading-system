package main.java.com.trading.feed;

@FunctionalInterface // Makes it lambda compatible
public interface PriceUpdateListener {
    void onPriceUpdate(String symbol, double price);
}
