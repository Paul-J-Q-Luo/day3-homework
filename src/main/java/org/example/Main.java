package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReceiptGenerator generator = new ReceiptGenerator();
        List<String> barcodes = List.of("ITEM000000", "ITEM000001", "ITEM000004", "ITEM000000");
        String receipt = generator.printReceipt(barcodes);
        System.out.println(receipt);
    }
}