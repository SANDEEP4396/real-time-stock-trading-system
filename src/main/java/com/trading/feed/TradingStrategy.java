package main.java.com.trading.feed;

import main.java.com.trading.core.StockPrice;
import main.java.com.trading.core.TradeSignal;

import java.util.List;
import java.util.Optional;

//O- Open for extension, closed for modification -> Open/Closed Principle
public abstract class TradingStrategy {
    protected final String name;
    public TradingStrategy(String name){
        this.name = name;
    }

    public abstract Optional<TradeSignal> analyze(StockPrice currentPrice, List<StockPrice> historicalPrices);

    public String getName() {
        return name;
    }
}
