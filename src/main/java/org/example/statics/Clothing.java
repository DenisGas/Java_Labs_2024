package org.example.statics;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Clothing extends Product {
    private final Size size;

    @Builder
    public Clothing(String name, double price, Size size) {
        super(name, price);
        this.size = size;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", size=" + size +
                '}';
    }
}
