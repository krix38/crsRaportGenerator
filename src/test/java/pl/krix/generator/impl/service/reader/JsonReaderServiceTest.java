package pl.krix.generator.impl.service.reader;

import org.junit.Test;
import pl.krix.generator.api.service.reader.JsonReader;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.JsonReaderException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 12.08.2017.
 */
public class JsonReaderServiceTest {

    private JsonReader jsonReader = new JsonReaderServiceImpl(TNaglowek.class);
    private Calendar calendar = Calendar.getInstance();


    private String correctJsonInput =
            "{" +
                    "\"idWiadomosci\": 1," +
                    "\"rok\": \"2017\"," +
                    "\"kodFormularza\": {" +
                    "                       \"value\": \"CRS_1\"," +
                    "                       \"kodSystemowy\": \"CRS-1 (1)\"," +
                    "                       \"wersjaSchemy\": \"1-0E\"" +
                    "                   }," +
                    "\"wariantFormularza\": 1," +
                    "\"idWiadomosciKorygowanej\": [\"A\", \"B\", \"C\"]" +
            "}";


    private String badJsonInput =
            "{" +
                    ";;;'''+++" +
            "}";

    @Test
    public void testCorrectJsonInputReading() throws JsonReaderException {
        InputStream inputStream = new ByteArrayInputStream(correctJsonInput.getBytes(StandardCharsets.UTF_8));
        TNaglowek header = (TNaglowek)jsonReader.read(inputStream);
        calendar.setTime(header.getRok());
        assertEquals("1", header.getIdWiadomosci());
        assertEquals(2017, calendar.get(Calendar.YEAR));
        assertEquals("CRS-1", header.getKodFormularza().getValue().value());
        assertEquals("CRS-1 (1)", header.getKodFormularza().getKodSystemowy());
        assertEquals("1-0E", header.getKodFormularza().getWersjaSchemy());
        assertEquals(1, header.getWariantFormularza());
        assertEquals("A", header.getIdWiadomosciKorygowanej().get(0));
        assertEquals("B", header.getIdWiadomosciKorygowanej().get(1));
        assertEquals("C", header.getIdWiadomosciKorygowanej().get(2));

    }

    @Test(expected = JsonReaderException.class)
    public void testBadJsonInputReading() throws JsonReaderException {
        InputStream inputStream = new ByteArrayInputStream(badJsonInput.getBytes(StandardCharsets.UTF_8));
        jsonReader.read(inputStream);
    }

    @Test(expected = JsonReaderException.class)
    public void testNullJsonInputReading() throws JsonReaderException {
        jsonReader.read(null);
    }
}
