package pl.krix.generator.api.service.deserializer;

import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidDeserializerInputException;

import java.util.List;

/**
 * Created by krix on 09.08.2017.
 */
public interface CsvDeserializerService {
    Csv deserializeToCsv(String csvInput) throws InvalidDeserializerInputException;

    List<Csv> deserializeToCsvList(String csvInput) throws InvalidDeserializerInputException;
}
