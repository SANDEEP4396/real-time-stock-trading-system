package main.java.com.trading.core;

public class Position {
    private final String symbol;
    private final double avgPrice;
    private final int quantity;

    public Position(String symbol, int quantity, double avgPrice)  {
        this.symbol = symbol;
        this.avgPrice = avgPrice;
        this.quantity = quantity;
    }

    public double getTotalValue(double currentPrice){
        return currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAvgPrice() {
        return avgPrice;
    }
}
