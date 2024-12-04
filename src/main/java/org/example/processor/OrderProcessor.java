package org.example.processor;

import lombok.AllArgsConstructor;
import org.example.statics.Product;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@AllArgsConstructor
public class OrderProcessor<T extends Product> {
    private final T product;

    public void processOrder(Consumer<T> processingStrategy) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                processingStrategy.accept(product);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new OrderProcessingException("Processing interrupted", e);
            }
        });
    }
}
