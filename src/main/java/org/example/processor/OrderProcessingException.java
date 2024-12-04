package org.example.processor;

public class OrderProcessingException extends RuntimeException {
    public OrderProcessingException(String message,Throwable cause) {
        super(message, cause);
    }
}
