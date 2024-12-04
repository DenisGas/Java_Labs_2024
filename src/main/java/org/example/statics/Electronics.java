package org.example.statics;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Electronics extends Product {
    private final String description;

    @Builder
    public Electronics(String name, double price, String description) {
        super(name, price);
        this.description = description;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", description='" + description + '\'' +
                '}';
    }
}
