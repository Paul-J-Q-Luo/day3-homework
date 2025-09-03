package org.example;

import java.util.ArrayList;
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

    private List<ReceiptItem> decodeToItems(List<String> barcodes) {
        Map<String, Integer> barcodeCounts = new HashMap<>();
        for (String barcode : barcodes) {
            barcodeCounts.put(barcode, barcodeCounts.getOrDefault(barcode, 0) + 1);
        }

        List<ReceiptItem> receiptItems = new ArrayList<>();
        barcodeCounts.forEach((barcode, count) -> {
            Item item = loadItem(barcode);
            if (item != null) {
                receiptItems.add(new ReceiptItem(item.getName(), item.getBarcode(), count, item.getPrice()));
            }
        });

        return receiptItems;
    }
}
