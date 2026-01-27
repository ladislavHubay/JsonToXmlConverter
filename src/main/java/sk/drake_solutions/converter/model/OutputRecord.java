package sk.drake_solutions.converter.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Zaznam pripraveny na vystup.
 */
public class OutputRecord {

    private String id;
    private String type;
    private LocalDate created;
    private BigDecimal amount;
    private int vat;
    private BigDecimal amountWithVat;

    public OutputRecord(String id, String type, LocalDate created, BigDecimal amount, int vat, BigDecimal amountWithVat) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public BigDecimal getAmountWithVat() {
        return amountWithVat;
    }

    public void setAmountWithVat(BigDecimal amountWithVat) {
        this.amountWithVat = amountWithVat;
    }
}
