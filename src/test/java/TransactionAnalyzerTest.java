import org.example.Transaction;
import org.example.TransactionCSVReader;
import org.example.TransactionAnalyzer;
import org.example.TransactionReportGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;

class TransactionAnalyzerTest {
    @Test
    public void testReadTransactionsFromFile() {
        String filePath = "C:/Users/denis/Downloads/pr2.csv"; // Переконайтеся, що файл існує

        List<Transaction> transactions = TransactionCSVReader.readTransactions(filePath);

        Assertions.assertNotNull(transactions, "Транзакції не були зчитані"); // Перевірка, що список не null
        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій порожній"); // Перевірка, що список не порожній
    }

    @Test
    public void testReadTransactionsFromUrl() {
        String testUrl = "https://informer.com.ua/dut/java/pr2.csv"; // Переконайтеся, що URL доступний

        List<Transaction> transactions = TransactionCSVReader.readTransactions(testUrl);

        Assertions.assertNotNull(transactions, "Список транзакцій не має бути null"); // Перевірка, що список не null
        Assertions.assertFalse(transactions.isEmpty(), "Список транзакцій не має бути порожнім"); // Перевірка, що список не порожній
    }

    @Test
    public void testReadTransactionsFromInvalidFile() {
        String invalidFilePath = "C:/Users/denis/Downloads/invalid.csv"; // Неправильний шлях до файлу

        List<Transaction> transactions = TransactionCSVReader.readTransactions(invalidFilePath);

        Assertions.assertNotNull(transactions, "Список транзакцій не має бути null"); // Список повинен бути не null
        Assertions.assertTrue(transactions.isEmpty(), "Список транзакцій має бути порожнім"); // Список має бути порожнім
    }

    @Test
    public void testReadTransactionsFromInvalidUrl() {
        String invalidUrl = "https://invalid-url.com/pr2.csv"; // Неправильний URL

        List<Transaction> transactions = TransactionCSVReader.readTransactions(invalidUrl);

        Assertions.assertNotNull(transactions, "Список транзакцій не має бути null"); // Список повинен бути не null
        Assertions.assertTrue(transactions.isEmpty(), "Список транзакцій має бути порожнім"); // Список має бути порожнім
    }

    @Test
    public void testCalculateTotalBalance() {
        Transaction transaction1 = new Transaction("01-01-2023", 100.0, "Дохід");
        Transaction transaction2 = new Transaction("02-01-2023", -50.0, "Витрата");
        Transaction transaction3 = new Transaction("03-01-2023", 150.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        double result = TransactionAnalyzer.calculateTotalBalance(transactions);

        Assertions.assertEquals(200.0, result, "Розрахунок загального балансу неправильний");
    }

    @Test
    public void testCountTransactionsByMonth() {
        Transaction transaction1 = new Transaction("01-02-2023", 50.0, "Дохід");
        Transaction transaction2 = new Transaction("15-02-2023", -20.0, "Витрата");
        Transaction transaction3 = new Transaction("05-03-2023", 100.0, "Дохід");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        int countFeb = TransactionAnalyzer.countTransactionsByMonth("02-2023", transactions);
        int countMar = TransactionAnalyzer.countTransactionsByMonth("03-2023", transactions);

        Assertions.assertEquals(2, countFeb, "Кількість транзакцій за лютий неправильна");
        Assertions.assertEquals(1, countMar, "Кількість транзакцій за березень неправильна");
    }

    @Test
    public void testFindTopExpenses() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("01-01-2023", -200.0, "Витрата"),
                new Transaction("02-01-2023", -500.0, "Витрата"),
                new Transaction("03-01-2023", -300.0, "Витрата"),
                new Transaction("04-01-2023", -400.0, "Витрата"),
                new Transaction("05-01-2023", -100.0, "Витрата"),
                new Transaction("06-01-2023", -600.0, "Витрата"),
                new Transaction("07-01-2023", -700.0, "Витрата"),
                new Transaction("08-01-2023", -800.0, "Витрата"),
                new Transaction("09-01-2023", -900.0, "Витрата"),
                new Transaction("10-01-2023", -1000.0, "Витрата"),
                new Transaction("11-01-2023", -1100.0, "Витрата"),
                new Transaction("12-01-2023", 200.0, "Дохід")  // Це не витрата, не повинно включатися
        );

        List<Transaction> topExpenses = TransactionAnalyzer.findTopExpenses(transactions);

        Assertions.assertEquals(10, topExpenses.size(), "Кількість найбільших витрат неправильна");
        Assertions.assertTrue(topExpenses.get(0).getAmount() <= topExpenses.get(1).getAmount(), "Витрати не відсортовані за спаданням");
        Assertions.assertEquals(-1100.0, topExpenses.get(0).getAmount(), "Найбільша витрата неправильна");
        Assertions.assertEquals(-200.0, topExpenses.get(9).getAmount(), "Десята витрата неправильна");
    }

    @Test
    public void testPrintExpenseSummary() {
        Transaction transaction1 = new Transaction("01-01-2023", -500.0, "Витрата1");
        Transaction transaction2 = new Transaction("15-01-2023", -300.0, "Витрата2");
        Transaction transaction3 = new Transaction("01-02-2023", -200.0, "Витрата3");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TransactionReportGenerator.printExpenseSummary(transactions);

        System.setOut(System.out);

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Сумарні витрати по категоріях:"), "Не знайдено заголовка для витрат по категоріях");
        Assertions.assertTrue(output.contains("Витрата1: 500.0₴"), "Не знайдено витрату Витрата1");
        Assertions.assertTrue(output.contains("Витрата2: 300.0₴"), "Не знайдено витрату Витрата2");
        Assertions.assertTrue(output.contains("Сумарні витрати по місяцях:"), "Не знайдено заголовка для витрат по місяцях");
    }

    @Test
    public void testPrintLargestExpense() {
        Transaction transaction1 = new Transaction("01-01-2023", -500.0, "Витрата1");
        Transaction transaction2 = new Transaction("15-01-2023", -300.0, "Витрата2");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TransactionReportGenerator.printLargestExpense(transactions, "01-2023");

        System.setOut(System.out);

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Найбільша витрата за 01-2023: -500.0₴ (Витрата1)"), "Неправильний вихід для найбільшої витрати");
    }

    @Test
    public void testPrintSmallestExpense() {
        Transaction transaction1 = new Transaction("01-01-2023", -500.0, "Витрата1");
        Transaction transaction2 = new Transaction("15-01-2023", -300.0, "Витрата2");
        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TransactionReportGenerator.printSmallestExpense(transactions, "01-2023");

        System.setOut(System.out);

        String output = outContent.toString();
        Assertions.assertTrue(output.contains("Найменша витрата за 01-2023: -300.0₴ (Витрата2)"), "Неправильний вихід для найменшої витрати");
    }
}
