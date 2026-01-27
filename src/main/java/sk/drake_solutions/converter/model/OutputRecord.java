package sk.drake_solutions.converter.model;

import java.time.LocalDate;

/**
 * Zaznam vystupu do XML.
 */
public class OutputRecord {

    private String id;
    private String type;
    private LocalDate created;
    private double amount;
    private int vat;
    private double amountWithVat;

    public OutputRecord(String id, String type, LocalDate created, double amount, int vat, double amountWithVat) {
        this.id = id;
        this.type = type;
        this.created = created;
        this.amount = amount;
        this.vat = vat;
        this.amountWithVat = amountWithVat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public double getAmountWithVat() {
        return amountWithVat;
    }

    public void setAmountWithVat(double amountWithVat) {
        this.amountWithVat = amountWithVat;
    }
}
