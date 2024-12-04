package org.example.storage;

import org.example.statics.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OrderRepository {
    private final ConcurrentHashMap<UUID, List<Product>> orders = new ConcurrentHashMap<>();

    public void saveOrder(UUID orderId, List<Product> products) {
        orders.put(orderId, products);
    }

    public List<Product> getOrder(UUID orderId) {
        return orders.get(orderId);
    }

    public Map<UUID, List<Product>> getOrders() {
        return Map.copyOf(orders); // Возвращаем немодифицируемую копию
    }
}
