package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptGenerator {
    private final Map<String, Item> inventory = new HashMap<>();

    public ReceiptGenerator() {
        inventory.put("ITEM000000", new Item("ITEM000000", "Coca-Cola", 3));
        inventory.put("ITEM000001", new Item("ITEM000001", "Sprite", 3));
        inventory.put("ITEM000004", new Item("ITEM000004", "Battery", 2));
    }

    public String printReceipt(List<String> barcodes) {
        List<ReceiptItem> receiptItems = decodeToItems(barcodes);
        Receipt receipt = calculateCost(receiptItems);
        return renderReceipt(receipt);
    }

    private List<ReceiptItem> decodeToItems(List<String> barcodes) {
        Map<String, Long> barcodeCounts = barcodes.stream()
                .collect(Collectors.groupingBy(Function.identity(),
                        LinkedHashMap::new,
                        Collectors.counting()));

        barcodeCounts.keySet().forEach(barcode -> {
            if (loadItem(barcode) == null) {
                throw new IllegalArgumentException("Unknown barcode detected: " + barcode);
            }
        });

        return barcodeCounts.entrySet().stream()
                .map(entry -> {
                    String barcode = entry.getKey();
                    int count = entry.getValue().intValue();
                    Item item = loadItem(barcode);
                    return new ReceiptItem(item.getName(), item.getBarcode(), count, item.getPrice());
                })
                .collect(Collectors.toList());
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
        return receiptItems.stream()
                .map(item -> String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)",
                        item.getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubTotal()))
                .collect(Collectors.joining("\n"));
    }

    private String generateReceipt(String itemsReceipt, int totalPrice) {
        return "***<store earning no money>Receipt***\n" +
                itemsReceipt +
                "\n----------------------\n" +
                String.format("Total: %d (yuan)\n", totalPrice) +
                "**********************";
    }
}
