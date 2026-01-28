package sk.drake_solutions.converter.service;

import sk.drake_solutions.converter.model.InputRecord;
import sk.drake_solutions.converter.model.OutputRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Stream;

/**
 * Riadi spracovavanie vstupnych / vystupnych suborov.
 */
public class FileProcessor {
    private final JsonReader jsonReader = new JsonReader();
    private final ValidationService validationService = new ValidationService();
    private final XmlWriter xmlWriter = new XmlWriter();

    private Path outputDir;
    private Path inputDir;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Metoda spusta spracovanie vstupnych, vystupnych parametrov a datumov.
     * @param inputDirStr Cesta k vstupnemu adresaru.
     * @param outputDirStr Cesta k vystupnemu adresaru.
     * @param startDateStr Datum.
     * @param endDateStr Datum.
     */
    public void process(
            String inputDirStr,
            String outputDirStr,
            String startDateStr,
            String endDateStr
    ) {
        if (!parseAndValidateInputs(inputDirStr, outputDirStr, startDateStr, endDateStr)) {
            return;
        }

        processFiles();
    }

    /**
     * Validuje a v pripade uspesnej validacie parsuje vstupne udaje pre spracovanie suborov.
     * @param inputDirStr Cetsa k vstupnemu adresaru.
     * @param outputDirStr Cesta k vystupnemu adresaru.
     * @param startDateStr Datum.
     * @param endDateStr Datum.
     * @return Vracia vysledok validacie. Ak su vsetky vstupy validne true, v opacnom pripade false.
     */
    private boolean parseAndValidateInputs(String inputDirStr, String outputDirStr, String startDateStr, String endDateStr) {
        try {
            this.inputDir = Paths.get(inputDirStr);
        } catch (InvalidPathException e) {
            System.out.println("Cesta k JSON je v nespravnom tvare");
            return false;
        }

        try {
            this.outputDir = Paths.get(outputDirStr);
        } catch (InvalidPathException e) {
            System.out.println("Cesta pre vystup je v nespravnom tvare");
            return false;
        }

        try {
            this.startDate = LocalDate.parse(startDateStr);
        } catch (DateTimeParseException ex) {
            System.out.println("Datum 'Platnost od' je v nespravnom tvare");
            return false;
        }

        try {
            this.endDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException ex) {
            System.out.println("Datum 'Platnost do' je v nespravnom tvare");
            return false;
        }

        if (!Files.isDirectory(inputDir)) {
            System.out.println("Vstupna cesta: '" + inputDir + "' sa nenasla");
            return false;
        }

        try (Stream<Path> files = Files.list(inputDir)) {
            boolean hasJson = files
                    .anyMatch(path -> path.toString().toLowerCase().endsWith(".json"));

            if (!hasJson) {
                System.out.println("Adresar '" + inputDir.toAbsolutePath() + "' neobsahuje žiadne JSON subory");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Chyba pri citani priecinka: " + e.getMessage());
            return false;
        }

        if(Files.notExists(outputDir)) {
            System.out.println("Vystupna cesta: '" + outputDir + "' sa nenasla");
            return false;
        }

        return true;
    }

    /**
     * Metoda spracuje vsetky JSON subory zo zadaneho adresara.
     */
    private void processFiles(){
        try (Stream<Path> files = Files.list(inputDir)) {
            files
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(this::processSingleJsonFile);
        } catch (IOException e) {
            System.out.println("Chyba pri citani vstupneho adresara");
            e.printStackTrace();
        }
    }

    /**
     * Spracuje jeden konkretny JSON subor.
     * @param jsonFile Cesta ku konkretnemu JSON suboru.
     */
    private void processSingleJsonFile(Path jsonFile) {
        try {
            List<InputRecord> inputRecords = jsonReader.read(jsonFile.toFile());

            List<OutputRecord> outputRecords =
                    validationService.validateAndMap(
                            inputRecords,
                            startDate,
                            endDate,
                            jsonFile.getFileName().toString()
                    );

            if (outputRecords.isEmpty()) {
                System.out.println("Ziadne platne zaznamy v subore: " + jsonFile.getFileName());
                return;
            }

            File outputFile = buildOutputFile(jsonFile);
            xmlWriter.write(outputRecords, outputFile);

        } catch (Exception e) {
            System.out.println("Chyba pri spracovani suboru: " + jsonFile.getFileName());
            e.printStackTrace();
        }
    }

    /**
     * Metoda vytvori file pripraveny na zapis XML suboru z JSON s rovnakym nazvom, ale koncovkou .xml.
     * @param jsonFile Cesta k JSON suboru.
     * @return Vrati file do ktoreho sa bude zapisovat obsah JSON.
     */
    private File buildOutputFile(Path jsonFile) {
        String fileName = jsonFile.getFileName().toString().replace(".json", ".xml");
        return outputDir.resolve(fileName).toFile();
    }
}
