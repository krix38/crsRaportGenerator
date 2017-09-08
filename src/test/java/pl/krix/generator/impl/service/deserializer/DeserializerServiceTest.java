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
        Csv csv = csvDeserializerService.deserializeToCsv("OECD1;1234;JAN;KOWALSKI;PL;OECD301;PL;a;PL;PL;PL;PL;JAN;KOWALSKI;OECD301;PL;a;123123123;ALL");
        assertEquals("123123123", csv.getAccountBalance());
        assertEquals("KOWALSKI", csv.getAccountHolderLastname());
    }

    @Test
    public void deserializerMultiLineTest() throws InvalidDeserializerInputException {
        String multilineCsv =
                "OECD1;1234;JAN;KOWALSKI;PL;OECD301;PL;a;PL;PL;PL;PL;JAN;KOWALSKI;OECD301;PL;a;123123123;ALL\n" +
                "OECD1;1234;JAN;KOWALSKI;PL;OECD301;PL;a;PL;PL;PL;PL;JAN;KOWALSKI;OECD301;PL;a;123123123;ALL\n" +
                "OECD1;1234;JAN;KOWALSKI;PL;OECD301;PL;a;PL;PL;PL;PL;JAN;KOWALSKI;OECD301;PL;a;123123123;ALL\n";
        List<Csv> csvList = csvDeserializerService.deserializeToCsvList(multilineCsv);
        assertEquals("123123123", csvList.get(0).getAccountBalance());
        assertEquals("KOWALSKI", csvList.get(0).getAccountHolderLastname());
        assertEquals("123123123", csvList.get(1).getAccountBalance());
        assertEquals("KOWALSKI", csvList.get(1).getAccountHolderLastname());
        assertEquals("123123123", csvList.get(2).getAccountBalance());
        assertEquals("KOWALSKI", csvList.get(2).getAccountHolderLastname());
    }

    @Test(expected = InvalidDeserializerInputException.class)
    public void deserializerNullInputTest() throws InvalidDeserializerInputException {
        csvDeserializerService.deserializeToCsv(null);
    }


}
