package main.java.com.trading.feed;

import main.java.com.trading.core.Portfolio;
import main.java.com.trading.core.Position;

interface PortfolioManager {
    void addPosition(Position position);
    void removePosition(String symbol);
    Portfolio getPortfolio();
}

