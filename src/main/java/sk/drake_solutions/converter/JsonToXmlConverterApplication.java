package sk.drake_solutions.converter;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.model.OutputRecord;
import sk.drake_solutions.converter.service.JsonReader;
import sk.drake_solutions.converter.service.ValidationService;
import sk.drake_solutions.converter.service.XmlWriter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Spustanie aplikacie.
 */
public class JsonToXmlConverterApplication {

    public static void main(String[] args) throws IOException {

        // Len na ucel testovania

        File inputJson = new File("D:/JsonToXmlConverter/test_1.json");
        File outputXml = new File("D:/JsonToXmlConverter/output.xml");

        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 31);

        JsonReader jsonReader = new JsonReader();
        ValidationService validationService = new ValidationService();
        XmlWriter xmlWriter = new XmlWriter();

        List<InputRecord> inputRecords = jsonReader.read(inputJson);
        List<OutputRecord> outputRecords = validationService.validateAndMap(inputRecords, startDate, endDate);
        xmlWriter.write(outputRecords, outputXml);
    }
}
