package main.java.com.trading.core;

public class PerformanceMetrics {
    private final double avgReturn;
    private int dataPoints;

    public PerformanceMetrics(double avgReturn, int dataPoints){
        this.avgReturn  = avgReturn;
        this.dataPoints = dataPoints;
    }

    public double getAvgReturn() {
        return avgReturn;
    }

    public int getDataPoints() {
        return dataPoints;
    }
}
