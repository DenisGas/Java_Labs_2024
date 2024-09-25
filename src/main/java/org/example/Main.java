package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> products = getProducts();
        Cart cart = new Cart();
        OrderHistory orderHistory = new OrderHistory();

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Пошук товарів");
            System.out.println("7 - Історія замовлень");
            System.out.println("0 - Вийти");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    products.forEach(System.out::println);
                    break;

                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int id = scanner.nextInt() - 1;
                    if (id < 0 || id >= products.size()) {
                        System.out.println("Товар з таким ID не знайдено");
                    } else {
                        cart.addProduct(products.get(id));
                        System.out.println("Товар додано: " + products.get(id));
                    }
                    break;

                case 3:
                    System.out.println(cart);
                    break;

                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        System.out.println("Замовлення оформлено:\n" + order);
                        orderHistory.addOrder(order); // Додаємо замовлення в історію
                        cart.clear();
                    }
                    break;

                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній.");
                    } else {
                        System.out.println(cart);
                        System.out.println("Введіть номер товару для видалення з кошика:");
                        int delId = scanner.nextInt() - 1;
                        if (delId < 0 || delId >= cart.getProducts().size()) {
                            System.out.println("Товара з таким номером не знайдено");
                        } else {
                            System.out.println("Товар видалено: " + cart.getProducts().get(delId));
                            cart.removeProduct(cart.getProducts().get(delId));
                        }
                    }
                    break;

                case 6:
                    System.out.println("Введіть назву або категорію для пошуку:");
                    scanner.nextLine(); // Пропустити новий рядок
                    String query = scanner.nextLine();
                    List<Product> searchResults = searchProducts(query, products);
                    if (searchResults.isEmpty()) {
                        System.out.println("Товари не знайдені.");
                    } else {
                        searchResults.forEach(System.out::println);
                    }
                    break;

                case 7:
                    System.out.println(orderHistory);
                    break;

                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }

    public static List<Product> searchProducts(String query, List<Product> products) {
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                    product.getCategory().getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    private static List<Product> getProducts() {
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники", accessories);

        return List.of(product1, product2, product3);
    }
}
