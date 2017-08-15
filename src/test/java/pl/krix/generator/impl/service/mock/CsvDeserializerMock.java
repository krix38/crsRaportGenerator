package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidDeserializerInputException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class CsvDeserializerMock implements CsvDeserializerService {
    @Override
    public Csv deserializeToCsv(String csvInput) throws InvalidDeserializerInputException {
        Csv csv = new Csv();
        csv.setMessageId("1");
        csv.setSomeData("2");
        return csv;
    }

    @Override
    public List<Csv> deserializeToCsvList(String csvInput) throws InvalidDeserializerInputException {
        Csv csv = new Csv();
        csv.setMessageId("1");
        csv.setSomeData("2");
        ArrayList<Csv> csvs = new ArrayList<>();
        csvs.add(csv);
        return csvs;
    }
}
