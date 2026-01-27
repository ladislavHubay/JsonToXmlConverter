package sk.drake_solutions.converter;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.model.OutputRecord;
import sk.drake_solutions.converter.service.JsonReader;
import sk.drake_solutions.converter.service.ValidationService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * Spustanie aplikacie.
 */
public class JsonToXmlConverterApplication {

    public static void main(String[] args) {

        // len pre uceli otestovania funkcnosti
        try {
            File jsonFile = new File("D:\\JsonToXmlConverter\\test_1.json");

            JsonReader jsonReader = new JsonReader();
            List<InputRecord> records = jsonReader.read(jsonFile);

            ValidationService validationService = new ValidationService();
            List<OutputRecord> records1 = validationService.validateAndMap(records, LocalDate.parse("2026-01-01"), LocalDate.parse("2026-01-31"));

            for (OutputRecord record : records1) {
                System.out.println(
                        record.getId() + "\n" +
                        record.getType() + "\n" +
                        record.getCreated() + "\n" +
                        record.getAmount() + "\n" +
                        record.getVat()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
