package org.example;

import java.util.List;

public class Receipt {
    private final List<ReceiptItem> receiptItems;
    private final int totalPrice;

    public Receipt(List<ReceiptItem> receiptItems, int totalPrice) {
        this.receiptItems = receiptItems;
        this.totalPrice = totalPrice;
    }

    // Getters
    public List<ReceiptItem> getReceiptItems() { return receiptItems; }
    public int getTotalPrice() { return totalPrice; }
}
