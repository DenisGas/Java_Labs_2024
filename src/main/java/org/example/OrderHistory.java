package org.example;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory {
    private final List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Історія замовлень:\n");
        for (Order order : orders) {
            sb.append(order).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
