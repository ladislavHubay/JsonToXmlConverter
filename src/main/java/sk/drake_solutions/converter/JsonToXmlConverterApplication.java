package sk.drake_solutions.converter;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.service.JsonReader;

import java.io.File;
import java.util.List;

/**
 * Spustanie aplikacie.
 */
public class JsonToXmlConverterApplication {

    public static void main(String[] args) {

        // len pre uceli otestovania funkcnosti
        try {
            File jsonFile = new File("D:\\JsonToXmlConverter\\test_1.json");

            System.out.println("Cesta: " + jsonFile.getAbsolutePath());
            System.out.println("Existuje: " + jsonFile.exists());

            JsonReader jsonReader = new JsonReader();
            List<InputRecord> records = jsonReader.read(jsonFile);

            System.out.println("Pocet zaznamov: " + records.size());

            for (InputRecord record : records) {
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
