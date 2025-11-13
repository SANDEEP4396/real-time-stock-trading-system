package main.java.com.trading.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portfolio {
    private double cashBalance;
    private List<Position> positions;

    public Portfolio(final List<Position> positions, final double cashBalance) {
        this.cashBalance = cashBalance;
        this.positions = new ArrayList<>(positions);
    }

    public List<Position> getPositions(){
        return Collections.unmodifiableList(positions);
    }
}
