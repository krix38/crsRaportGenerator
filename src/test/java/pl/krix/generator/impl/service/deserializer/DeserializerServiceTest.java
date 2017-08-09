package pl.krix.generator.impl.service.deserializer;

import org.junit.Test;
import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidDeserializerInputException;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 09.08.2017.
 */
public class DeserializerServiceTest {

    CsvDeserializerService csvDeserializerService = new CsvDeserializerServiceImpl();

    @Test
    public void deserializerSingleLineTest() throws InvalidDeserializerInputException {
        Csv csv = csvDeserializerService.deserializeToCsv("1;test");
        assertEquals("1", csv.getMessageId());
        assertEquals("test", csv.getSomeData());
    }

    @Test
    public void deserializerMultiLineTest() throws InvalidDeserializerInputException {
        String multilineCsv =
                "1;test\n" +
                "2;foo\n" +
                "3;bar";
        List<Csv> csvList = csvDeserializerService.deserializeToCsvList(multilineCsv);
        assertEquals("1", csvList.get(0).getMessageId());
        assertEquals("test", csvList.get(0).getSomeData());
        assertEquals("2", csvList.get(1).getMessageId());
        assertEquals("foo", csvList.get(1).getSomeData());
        assertEquals("3", csvList.get(2).getMessageId());
        assertEquals("bar", csvList.get(2).getSomeData());
    }

    @Test(expected = InvalidDeserializerInputException.class)
    public void deserializerNullInputTest() throws InvalidDeserializerInputException {
        csvDeserializerService.deserializeToCsv(null);
    }


}
