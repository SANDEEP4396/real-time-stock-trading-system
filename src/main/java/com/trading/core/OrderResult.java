package main.java.com.trading.core;


public class OrderResult {
    private final boolean success;
    private final String orderId;
    private final double executedPrice;
    private final String message;

    public OrderResult(boolean success, String orderId, double executedPrice, String message) {
        this.success = success;
        this.orderId = orderId;
        this.executedPrice = executedPrice;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getExecutedPrice() {
        return executedPrice;
    }

    public String getMessage() {
        return message;
    }

   public static OrderResult success(String orderId, double price){
        return new OrderResult(true, orderId, price, "Order placed Successfully");
    }

    public static OrderResult rejected(String reason){
        return new OrderResult(false, null, 0.0, "Failed to execute the order..Try Again" );
    }
}
