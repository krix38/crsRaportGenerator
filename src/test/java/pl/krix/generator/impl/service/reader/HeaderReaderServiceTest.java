package pl.krix.generator.impl.service.reader;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.junit.Test;
import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.HeaderReaderException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 12.08.2017.
 */
public class HeaderReaderServiceTest {

    private HeaderReader headerReader = new HeaderReaderImpl();

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
    public void testCorrectJsonInputReading() throws HeaderReaderException {
        InputStream inputStream = new ByteArrayInputStream(correctJsonInput.getBytes(StandardCharsets.UTF_8));
        TNaglowek header = headerReader.readHeder(inputStream);
        assertEquals("1", header.getIdWiadomosci());
        assertEquals(2017, header.getRok().getYear());
        assertEquals("CRS-1", header.getKodFormularza().getValue().value());
        assertEquals("CRS-1 (1)", header.getKodFormularza().getKodSystemowy());
        assertEquals("1-0E", header.getKodFormularza().getWersjaSchemy());
        assertEquals(1, header.getWariantFormularza());
        assertEquals("A", header.getIdWiadomosciKorygowanej().get(0));
        assertEquals("B", header.getIdWiadomosciKorygowanej().get(1));
        assertEquals("C", header.getIdWiadomosciKorygowanej().get(2));

    }

    @Test(expected = HeaderReaderException.class)
    public void testBadJsonInputReading() throws HeaderReaderException {
        InputStream inputStream = new ByteArrayInputStream(badJsonInput.getBytes(StandardCharsets.UTF_8));
        headerReader.readHeder(inputStream);
    }

    @Test(expected = HeaderReaderException.class)
    public void testNullJsonInputReading() throws HeaderReaderException {
        headerReader.readHeder(null);
    }
}
