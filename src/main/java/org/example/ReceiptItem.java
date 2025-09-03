package org.example;

public class ReceiptItem {
    private final String name;
    private final String barcode;
    private final int quantity;
    private final int unitPrice;
    private int subTotal;

    public ReceiptItem(String name, String barcode, int quantity, int unitPrice) {
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = quantity * unitPrice;
    }

    public String getName() { return name; }
    public String getBarcode() { return barcode; }
    public int getQuantity() { return quantity; }
    public int getUnitPrice() { return unitPrice; }
    public int getSubTotal() { return subTotal; }

    public void setSubTotal(int subTotal) { this.subTotal = subTotal; }
}
