package main.java.com.trading.core;

public class TradeSignal {
    public enum Action {
        BUY,
        SELL,
        HOLD
    }

    private final String symbol;
    private final double price;
    private final Action action;

    public TradeSignal(String symbol, double price, Action action) {
        this.symbol = symbol;
        this.price = price;
        this.action = action;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public Action getAction() {
        return action;
    }
}
