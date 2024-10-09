package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class TransactionCSVReader {

    public static List<Transaction> readTransactions(String filePathOrUrl) {
        List<Transaction> transactions = new ArrayList<>();
        BufferedReader br = null;

        try {
            // Перевірка, чи це URL
            try {
                URL url = new URL(filePathOrUrl);  // Якщо це URL, буде успішне створення URL-об'єкта
                br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            } catch (MalformedURLException e) {
                // Якщо це не URL, то це локальний файл
                File file = new File(filePathOrUrl);
                br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Transaction transaction = new Transaction(values[0], Double.parseDouble(values[1]), values[2]);
                transactions.add(transaction);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return transactions;
    }
}
