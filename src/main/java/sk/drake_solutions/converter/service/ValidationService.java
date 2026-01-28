package sk.drake_solutions.converter.service;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.model.OutputRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Validuje data podla zadefinovanych poziadaviek.
 */
public class ValidationService {

    /**
     * Metoda Validuje vstupne data podla zadefinovanych poziadaviek a zapise do zoznamu pre vystup.
     * @param inputs Zoznam vstupnych udajov.
     * @param startDate Ohranicenie od akeho datumu musi byt datum vo vystupnych datach.
     * @param endDate Ohranicenie do akeho datumu musi byt datum vo vystupnych datach.
     * @return Vracia zoznam iba tych udajov ktore splnaju zadefnovane poziadavky.
     */
    public List<OutputRecord> validateAndMap(List<InputRecord> inputs, LocalDate startDate, LocalDate endDate, String jsonFileName) {

        // Po prvom logu sa dalsie data nevaliduju. - Mozno sa bude menit podla spresnenie zadania.
        List<OutputRecord> outputs = new ArrayList<>();

        for (InputRecord input : inputs){
            if(input.getId() == null || input.getId().isBlank()){
                System.out.println("Subor: " + jsonFileName + " - Zaznam bol preskoceny: chybajuce alebo prazdne ID");
                continue;
            }

            LocalDate createdDate;
            try {
                createdDate = LocalDate.parse(input.getCreated());
            } catch (DateTimeParseException | NullPointerException e) {
                System.out.println("Subor: " + jsonFileName + " - Zaznam bol preskoceny: neplatny alebo chybajuci datum vytvorenia");
                continue;
            }

            if(input.getAmount().compareTo(BigDecimal.ZERO) <= 0){
                System.out.println("Subor: " + jsonFileName + " - Zaznam bol preskoceny: suma musi byt kladne cislo vacsie ako nula");
                continue;
            }

            int vat;
            try {
                vat = input.getVat().intValueExact();
                if (vat < 0 || vat > 100) {
                    System.out.println("Subor: " + jsonFileName + " - Záznam bol preskočený: hodnota DPH musí byť v intervale 0 - 100");
                    continue;
                }
            } catch (ArithmeticException e) {
                System.out.println("Subor: " + jsonFileName + " - Záznam bol preskočený: hodnota DPH musí byť celé číslo");
                continue;
            }

            if (createdDate.isBefore(startDate) || createdDate.isAfter(endDate)) {
                System.out.println("Subor: " + jsonFileName + " - Zaznam bol preskoceny: datum vytvorenia je mimo zadaneho intervalu");
                continue;
            }

            BigDecimal amountWithVat = calculateAmountWithVat(input.getAmount(), vat);

            outputs.add(new OutputRecord(
                    input.getId(),
                    input.getType(),
                    createdDate,
                    input.getAmount(),
                    vat,
                    amountWithVat
            ));
        }

        return outputs;
    }

    /**
     * Metoda vypocita celkovu hodnotu.
     * @param amount Hodnota.
     * @param vat DPH.
     * @return Vrati celkovu hodnotu.
     */
    private BigDecimal calculateAmountWithVat(BigDecimal amount, int vat) {
        BigDecimal vatPercent = BigDecimal.valueOf(vat);                    // int -> BigDecimal
        BigDecimal vatRate = vatPercent.divide(BigDecimal.valueOf(100),     // vatRate / 100
                4,                                                          // 4 desatinne miesta
                RoundingMode.HALF_UP);                                      // klasicke zaokruhlovanie

        BigDecimal multiplier = BigDecimal.ONE.add(vatRate);                // vatRate + 1

        BigDecimal amountWithVat = amount.multiply(multiplier)              // amount * multiplier
                        .setScale(2, RoundingMode.HALF_UP);        // klasicke matematicke zaokruhlenie na 2 desatinne miesta

        return amountWithVat;
    }
}