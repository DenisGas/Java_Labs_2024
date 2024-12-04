package org.example;

import com.github.javafaker.Faker;
import org.example.processor.OrderProcessor;
import org.example.statics.Clothing;
import org.example.statics.Electronics;
import org.example.statics.Product;
import org.example.statics.Size;
import org.example.storage.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Faker faker = new Faker();
        OrderRepository repository = new OrderRepository();

        // Лічильники для відстеження кількості оброблених замовлень
        AtomicInteger electronicsProcessed = new AtomicInteger(0);
        AtomicInteger clothingProcessed = new AtomicInteger(0);

        // Генерація продуктів
        List<Electronics> electronicsList = generateProducts(10, fake ->
                Electronics.builder()
                        .name(fake.commerce().productName())
                        .price(Double.parseDouble(fake.commerce().price().replace(",", ".")))
                        .description(fake.lorem().sentence())
                        .build()
        );

        List<Clothing> clothingList = generateProducts(5, fake ->
                Clothing.builder()
                        .name(fake.commerce().productName())
                        .price(Double.parseDouble(fake.commerce().price().replace(",", ".")))
                        .size(Size.values()[fake.random().nextInt(Size.values().length)])
                        .build()
        );

        // Обробка замовлень
        processOrders(electronicsList, repository, electronicsProcessed, clothingProcessed);
        processOrders(clothingList, repository, electronicsProcessed, clothingProcessed);

        // Очікуємо завершення всіх потоків
        Thread.sleep(1200);

        // Загальна статистика
        System.out.println("Всього замовлень електроніки: " + electronicsList.size());
        System.out.println("Оброблено замовлень електроніки: " + electronicsProcessed.get());
        System.out.println("Всього замовлень одягу: " + clothingList.size());
        System.out.println("Оброблено замовлень одягу: " + clothingProcessed.get());

        // Виведення усіх оброблених замовлень
        System.out.println("\nУсі оброблені замовлення:");
        repository.getOrders().forEach((orderId, products) -> {
            System.out.println("Замовлення ID: " + orderId);
            products.forEach(product -> System.out.println(" - " + product));
        });

        // Приклад пошуку замовлення
        if (!repository.getOrders().isEmpty()) {
            UUID sampleOrderId = repository.getOrders().keySet().iterator().next(); // Беремо будь-який ID
            List<Product> foundProducts = repository.getOrder(sampleOrderId);

            System.out.println("\nЗнайдено замовлення: ID = " + sampleOrderId);
            foundProducts.forEach(product -> System.out.println(" - " + product));
        } else {
            System.out.println("\nЗамовлення ще не оброблені.");
        }
    }

    private static <T extends Product> List<T> generateProducts(int count, Function<Faker, T> generator) {
        Faker faker = new Faker();
        return IntStream.range(0, count)
                .mapToObj(i -> generator.apply(faker))
                .toList();
    }

    private static <T extends Product> void processOrders(List<T> products, OrderRepository repository, AtomicInteger electronicsProcessed, AtomicInteger clothingProcessed) {
        // Процес обробки замовлень з використанням OrderProcessor
        products.forEach(product -> {
            OrderProcessor<T> processor = new OrderProcessor<>(product);
            processor.processOrder(order -> {
                UUID orderId = UUID.randomUUID();
                synchronized (repository) {
                    // Оновлюємо лічильник залежно від типу продукту
                    if (order instanceof Electronics) {
                        electronicsProcessed.incrementAndGet();  // Збільшуємо лічильник для електроніки
                    } else if (order instanceof Clothing) {
                        clothingProcessed.incrementAndGet();  // Збільшуємо лічильник для одягу
                    }

                    // Друкуємо інформацію про поточне замовлення
                    System.out.println("Потік: " + Thread.currentThread().getName() +
                            " | ID замовлення: " + orderId +
                            " | Оброблений продукт: " + order.getName());

                    repository.saveOrder(orderId, List.of(order));  // Зберігаємо продукт у замовленні
                }
            });
        });
    }
}
