package main.java.com.trading.feed;

import main.java.com.trading.core.Order;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class OrderBook {

    private final ConcurrentSkipListMap<Double, Queue<Order>> buyOrders;
    private final ConcurrentSkipListMap<Double, Queue<Order>> sellOrders;
    private final ReadWriteLock lock;

    public OrderBook() {
        // Reverse order for buy orders( highest first)
        this.buyOrders = new ConcurrentSkipListMap<>(Comparator.reverseOrder());
        // Natural order for sell orders (lowest first)
        this.sellOrders = new ConcurrentSkipListMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void addOrder(Order order){
        lock.writeLock().lock(); // Acquire write lock
        try {
            ConcurrentSkipListMap<Double, Queue<Order>> orders = order.getSide() == Order.Side.BUY ? buyOrders : sellOrders;
            orders.computeIfAbsent(order.getPrice(), k -> new ConcurrentLinkedQueue<>())
                    .add(order);
        } finally {
            lock.writeLock().unlock(); // ALWAYS Unlock in finally
        }
    }

    public List<Order> matchOrders(){
        lock.writeLock().lock();
        try{
            List<Order> matches = new ArrayList<>();
            // keep matching while prices overlap
            while (!buyOrders.isEmpty() && !sellOrders.isEmpty()){
                double highestBid = buyOrders.firstKey();
                double lowestAsk = sellOrders.firstKey();

                //Can we match?
                if(highestBid >= lowestAsk){
                    Queue<Order> buyQueue = buyOrders.get(highestBid);
                    Queue<Order> sellQueue = sellOrders.get(lowestAsk);

                    Order buyOrder = buyQueue.poll();
                    Order sellOrder = sellQueue.poll();

                    if(buyQueue.isEmpty()) buyOrders.remove(highestBid);
                    if(sellQueue.isEmpty()) sellOrders.remove(highestBid);

                    matches.add(buyOrder);
                    matches.add(sellOrder);
                }else {
                    break;
                }
            }
            return matches;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public Map<Double, Integer> getSnapshot() {
        lock.readLock().lock();
        try {
            // Return defensive copy
            return new HashMap<>(buyOrders.keySet().stream()
                    .collect(Collectors.toMap(
                            price -> price,
                            price -> buyOrders.get(price).size())));
        }finally {
            lock.readLock().unlock();
        }
    }
}
