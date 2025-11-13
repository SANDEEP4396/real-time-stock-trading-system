package main.java.com.trading.feed;

import main.java.com.trading.core.Portfolio;
import main.java.com.trading.core.Position;
import main.java.com.trading.core.RiskMetrics;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PortfolioAnalyzer {
    private final Portfolio portfolio;

    public PortfolioAnalyzer(final Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public double calculateTotalValue(Function<String, Double> priceProvider){
        return portfolio.getPositions().stream()
                .mapToDouble(pos -> pos.getQuantity() * priceProvider.apply(pos.getSymbol()))
                .sum();
    }

    public List<Position> findPositions(Predicate<Position> criteria) {
        return portfolio.getPositions().stream()
                .filter(criteria)
                .collect(Collectors.toList());
    }

    public Map<String, Double> getPositionsValues(Function<String, Double> priceProvider){
        return portfolio.getPositions().stream()
                .collect(Collectors.toMap(
                        Position::getSymbol,
                        pos -> pos.getQuantity() * priceProvider.apply(pos.getSymbol())
                ));
    }

    public Map<String , Double> calculateReturns(){
        return portfolio.getPositions().parallelStream()
                .collect(Collectors.toMap(
                        Position::getSymbol,
                        this::calculateReturn
                ));
    }

    private double calculateReturn(Position position){
        // Complex calculation - benefits from paralle execution
        return 0.0;
    }

    public RiskMetrics calculateRisk(Function<String, Double> priceProvider){
        return portfolio.getPositions().stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        positions -> {
                          final double total = positions.stream()
                                    .mapToDouble(p -> p.getQuantity() * priceProvider.apply(p.getSymbol()))
                                    .sum();

                            final Map<String, Double> maxAllocation = positions.stream()
                                    .collect(Collectors.toMap(
                                            Position::getSymbol,
                                            p -> (p.getQuantity() * priceProvider.apply(p.getSymbol()))/total
                                    ));
                            final double concentrationRisk = maxAllocation.values().stream()
                                    .mapToDouble(Double::doubleValue)
                                    .max()
                                    .orElse(0.0);

                            return new RiskMetrics(total,concentrationRisk ,maxAllocation);
                        }
                ));
    }
}
