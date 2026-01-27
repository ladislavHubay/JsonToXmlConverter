package sk.drake_solutions.converter.model;

import java.math.BigDecimal;

/**
 * Zaznam zo vstupnych udajov.
 */
public class InputRecord {

    private String id;
    private String type;
    private String created;
    private BigDecimal amount;
    private int vat;

    public InputRecord() {
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
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
}
