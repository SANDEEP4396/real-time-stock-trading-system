package main.java.com.trading.core;

public class Order {
    public enum Side{
        BUY,
        SELL
    }
    private final Side side;
    private final String id;
    private final String symbol;
    private final double price;
    private final int quantity;

    public Order(String id, String symbol, double price, int quantity, Side side) {
        if(price <=0 ){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if(quantity<=0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.id = id;
        this.side = side;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
    }

    public Side getSide() {
        return side;
    }

    public String getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
