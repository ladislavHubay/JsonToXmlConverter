package sk.drake_solutions.converter.cli;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Spracovavanie a validacia vstupnych parametrov.
 */
public class ArgumentParser {

    private final String inputDir;
    private final String outputDir;
    private final String startDate;
    private final String endDate;

    /**
     * Konstruktor extrahuje z pola argumentov (args) vyextrahuje hodnoty pre vstupny / vystupny adresar, datumy od / do.
     * @param args Pole argumentov.
     */
    public ArgumentParser(String[] args) {
        Map<String, String> params = parseArgs(args);

        try {
            this.inputDir = getRequired(params, "--inputDir");
            this.outputDir = getRequired(params, "--outputDir");
            this.startDate = getRequired(params, "--platnostOd");
            this.endDate = getRequired(params, "--platnostDo");
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Datum musi byt vo formate YYYY-MM-DD");
        }
    }

    /**
     * Spracuje pole argumentov do mapy kde kazdy parameter sparuje s hodnotou.
     * @param args Pole argumentov.
     * @return Vrati mapu.
     */
    private Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < args.length - 1; i += 2) {
            map.put(args[i], args[i + 1]);
        }

        return map;
    }

    /**
     * Z mapy vyberie na zaklade konkretneho parametra hodnotu.
     * @param map Mapa argumentov.
     * @param key Nazov parametra.
     * @return Vrati hodnotu parametra.
     */
    private String getRequired(Map<String, String> map, String key) {
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException("Chyba povinny parameter: " + key);
        }
        return map.get(key);
    }

    /**
     * Cesta k vstupnemu adresaru.
     * @return vratcia cestu k vstupnemu adresaru.
     */
    public String getInputDir() {
        return inputDir;
    }

    /**
     * Cesta k vystupnemu adresaru.
     * @return Vracia cestu k vystupnemu adresaru.
     */
    public String getOutputDir() {
        return outputDir;
    }

    /**
     * Datum od ktoreho sa ma zacat filtrovat.
     * @return Datum.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Datum po ktory sa ma filtrovat.
     * @return Datum.
     */
    public String getEndDate() {
        return endDate;
    }
}
