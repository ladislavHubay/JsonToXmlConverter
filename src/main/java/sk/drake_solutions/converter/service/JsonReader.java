package sk.drake_solutions.converter.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import sk.drake_solutions.converter.model.InputRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Nacitava data zo suboru JSON.
 */
public class JsonReader {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<InputRecord> read(File jsonFile) throws IOException {
        return objectMapper.readValue(
                jsonFile,
                new TypeReference<List<InputRecord>>() {}
        );
    }
}
