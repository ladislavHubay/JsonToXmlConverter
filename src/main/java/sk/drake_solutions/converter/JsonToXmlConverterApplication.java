package sk.drake_solutions.converter;

import sk.drake_solutions.converter.cli.ArgumentParser;
import sk.drake_solutions.converter.service.FileProcessor;

import java.util.Scanner;

/**
 * Spustanie aplikacie.
 */
public class JsonToXmlConverterApplication {

    public static void main(String[] args) {
        String inputDir;
        String outputDir;
        String startDate;
        String endDate;

        if (args.length > 0) {
            ArgumentParser parser = new ArgumentParser(args);
            inputDir = parser.getInputDir();
            outputDir = parser.getOutputDir();
            startDate = parser.getStartDate();
            endDate = parser.getEndDate();
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Zadaj cestu k vstupnemu adresaru: ");
            inputDir = scanner.nextLine();

            System.out.print("Zadaj cestu k vystupnemu adresaru: ");
            outputDir = scanner.nextLine();

            System.out.print("Zadaj platnost od (YYYY-MM-DD): ");
            startDate = scanner.nextLine();

            System.out.print("Zadaj platnost do (YYYY-MM-DD): ");
            endDate = scanner.nextLine();
        }

        FileProcessor fileProcessor = new FileProcessor();
        fileProcessor.process(
                inputDir,
                outputDir,
                startDate,
                endDate
        );
    }
}
