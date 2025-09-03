package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptGenerator {
    private final Map<String, Item> inventory = new HashMap<>();

    public ReceiptGenerator() {
        inventory.put("12345", new Item("12345", "Milk", 3));
        inventory.put("67890", new Item("67890", "Bread", 3));
        inventory.put("11223", new Item("11223", "Eggs", 2));
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
            if (item == null) {
                throw new IllegalArgumentException("Unknown barcode detected: " + barcode);
            }
            receiptItems.add(new ReceiptItem(item.getName(), item.getBarcode(), count, item.getPrice()));
        });

        return receiptItems;
    }

    private Item loadItem(String barcode) {
        return inventory.get(barcode);
    }

    private Receipt calculateCost(List<ReceiptItem> receiptItems) {
        calculateItemsCost(receiptItems);
        int totalPrice = calculateTotalPrice(receiptItems);
        return new Receipt(receiptItems, totalPrice);
    }

    private void calculateItemsCost(List<ReceiptItem> receiptItems) {
        for (ReceiptItem item : receiptItems) {
            item.setSubTotal(item.getQuantity() * item.getUnitPrice());
        }
    }

    private int calculateTotalPrice(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .mapToInt(ReceiptItem::getSubTotal)
                .sum();
    }

    private String renderReceipt(Receipt receipt) {
        String itemsReceipt = generateItemsReceipt(receipt.getReceiptItems());
        return generateReceipt(itemsReceipt, receipt.getTotalPrice());
    }

    private String generateItemsReceipt(List<ReceiptItem> receiptItems) {
        StringBuilder itemsReceipt = new StringBuilder();
        for (ReceiptItem item : receiptItems) {
            itemsReceipt.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    item.getName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubTotal()));
        }
        return itemsReceipt.toString();
    }

    private String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***\n" +
                itemsReceipt +
                "----------------------\n" +
                String.format("Total: %d (yuan)\n", totalPrice) +
                "**********************";
    }
}
