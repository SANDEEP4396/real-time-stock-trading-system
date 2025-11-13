package main.java.com.trading.core;

import java.util.Map;

public class RiskMetrics {
    private final double totalValue;
    private final double concentrationRisk;
    private final Map<String, Double> allocations;

    public RiskMetrics(double totalValue, double concentrationRisk, Map<String, Double> allocations) {
        this.totalValue = totalValue;
        this.concentrationRisk = concentrationRisk;
        this.allocations = allocations;
    }

    public double getConcentrationRisk() {
        return concentrationRisk;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public Map<String, Double> getAllocations() {
        return allocations;
    }
}
