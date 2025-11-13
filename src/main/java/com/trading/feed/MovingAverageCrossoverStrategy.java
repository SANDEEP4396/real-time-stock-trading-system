package main.java.com.trading.feed;

import main.java.com.trading.core.StockPrice;
import main.java.com.trading.core.TradeSignal;

import java.util.List;
import java.util.Optional;

// L - Liskov Substitution Principle
// Subtypes must be substitutable for their base types
class MovingAverageCrossoverStrategy extends TradingStrategy{
    private final int shortPeriod;
    private final int longPeriod;

    public MovingAverageCrossoverStrategy(final int shortPeriod,
                                          final int longPeriod) {
        super("MA-Crossover");
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
    }

    @Override
    public Optional<TradeSignal> analyze(final StockPrice currentPrice, final List<StockPrice> historicalPrices) {
        if(historicalPrices.size() < longPeriod){
            return Optional.empty();
        }
        double shortMA = calculateMA(historicalPrices, shortPeriod);
        double longMA = calculateMA(historicalPrices, longPeriod);
        if(shortMA> longMA && currentPrice.getPrice() > shortMA){
            return Optional.of(new TradeSignal(currentPrice.getSymbol(), currentPrice.getPrice(), TradeSignal.Action.BUY));
        }else if (shortMA < longMA && currentPrice.getPrice() <shortMA){
            return Optional.of(new TradeSignal(currentPrice.getSymbol(), currentPrice.getPrice(), TradeSignal.Action.SELL));
        }
        return Optional.empty();
    }

    public double calculateMA(List<StockPrice> prices, int period){
        return prices.stream()
                .skip(Math.max(0, prices.size() - period))
                .mapToDouble(StockPrice::getPrice)
                .average()
                .orElse(0.0);
    }
}
