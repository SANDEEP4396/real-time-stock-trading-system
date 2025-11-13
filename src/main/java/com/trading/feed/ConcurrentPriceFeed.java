package main.java.com.trading.feed;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConcurrentPriceFeed implements PriceFeed {

    // Why ConcurrentHashMap? Multiple threads can read/write simultaneously
    private final ConcurrentHashMap<String, CopyOnWriteArrayList<PriceUpdateListener>> listeners;
    //For periodic tasks(price updates every 1sec)
    private final ScheduledExecutorService scheduler;
    // Lock free thread safe boolean flag
    private final AtomicBoolean running;

    public ConcurrentPriceFeed(final int threadPoolSize) {
        this.listeners = new ConcurrentHashMap<>();
        this.scheduler = Executors.newScheduledThreadPool(threadPoolSize);
        this.running = new AtomicBoolean(false);
    }

    @Override
    public void subscribe(final String symbol, final PriceUpdateListener listener) {
        // User computeIfAbsent - thread-safe "get or create"
        listeners.computeIfAbsent(symbol, k -> new CopyOnWriteArrayList<>())
                .add(listener);
    }

    @Override
    public void unsubscribe(final String symbol) {

    }

    @Override
    public void start() {
        // Use CompareAndSet to ensure only one thread starts it
        if (running.compareAndSet(false, true)) {
            listeners.keySet().forEach(symbol -> {
                scheduler.scheduleAtFixedRate(
                        () -> generatePriceUpdate(symbol),
                        0,
                        1,
                        TimeUnit.SECONDS
                );
            });
        }
    }

    private void generatePriceUpdate(String symbol) {
        //Generate random price
        double price = 100 + Math.random() * 100;
        List<PriceUpdateListener> symbolListeners = listeners.get(symbol);
        if (symbolListeners != null) {
            //Notify all listeners in parallel
            symbolListeners.parallelStream()
                    .forEach(listener -> {
                        try {
                            listener.onPriceUpdate(symbol, price);
                        } catch (Exception e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void stop() {
        if(running.compareAndSet(true, false)){
            scheduler.shutdown();
            try {
                if(!scheduler.awaitTermination(5, TimeUnit.SECONDS)){
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
