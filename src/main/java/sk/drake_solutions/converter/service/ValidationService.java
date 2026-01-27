package sk.drake_solutions.converter.service;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.model.OutputRecord;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Validuje data podla zadefinovanych poziadaviek.
 */
public class ValidationService {

    /**
     * Metoda Validuje vstupne data podla zadefinovanych poziadaviek.
     * @param inputs Zoznam vstupnych udajov.
     * @param startDate Ohranicenie OD akeho datumu musi byt datum v vstupnych datach.
     * @param endDate Ohranicenie DO akeho datumu musi byt datum v vstupnych datach.
     * @return Vracia zoznam iba tych udajov ktore splnaju zadefnovane poziadavky.
     */
    public List<OutputRecord> validateAndMap(List<InputRecord> inputs, LocalDate startDate, LocalDate endDate){

        // Po prvom logu sa dalsie data nevaliduju. - Mozno sa bude menit podla spresnenie zadania.
        List<OutputRecord> outputs = new ArrayList<>();

        for (InputRecord input : inputs){
            if(input.getId() == null || input.getId().isBlank()){
                System.out.println("Zaznam bol preskoceny: chybajuce alebo prazdne ID");
                continue;
            }

            LocalDate createdDate;
            try {
                createdDate = LocalDate.parse(input.getCreated());
            } catch (DateTimeParseException | NullPointerException e) {
                System.out.println("Zaznam bol preskoceny: neplatny alebo chybajuci datum vytvorenia");
                continue;
            }

            if(input.getAmount() <= 0){
                System.out.println("Zaznam bol preskoceny: suma musi byt kladne cislo vacsie ako nula");
                continue;
            }

            if(input.getVat() <= 0){
                System.out.println("Zaznam bol preskoceny: hodnota DPH musi byt kladne cislo vacsie ako nula");
                continue;
            }

            if (createdDate.isBefore(startDate) || createdDate.isAfter(endDate)) {
                System.out.println("Zaznam bol preskoceny: datum vytvorenia je mimo zadaneho intervalu");
                continue;
            }

            double amountWithVat = input.getAmount() * (1 + input.getVat() / 100.0);

            outputs.add(new OutputRecord(
                    input.getId(),
                    input.getType(),
                    createdDate,
                    input.getAmount(),
                    input.getVat(),
                    amountWithVat
            ));
        }

        return outputs;
    }
}
