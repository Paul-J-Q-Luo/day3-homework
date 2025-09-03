package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReceiptGenerator generator = new ReceiptGenerator();
        List<String> barcodes = List.of("12345", "67890", "12345", "11223");
        String receipt = generator.printReceipt(barcodes);
        System.out.println(receipt);
    }
}