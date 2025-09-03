package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {
    private final Map<String, Item> inventory = new HashMap<>();

    public ReceiptGenerator() {
        inventory.put("12345", new Item("Milk", "12345", 250)); // Price in cents
        inventory.put("67890", new Item("Bread", "67890", 180));
        inventory.put("11223", new Item("Eggs", "11223", 300));
    }

    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = decodeToItems(barcodes);
        Receipt receipt = calculateCost(receiptItems);
        return renderReceipt(receipt);
    }
}
