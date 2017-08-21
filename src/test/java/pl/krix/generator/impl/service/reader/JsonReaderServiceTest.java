package pl.krix.generator.impl.service.reader;

import org.junit.Test;
import pl.krix.generator.api.service.reader.JsonReader;
import pl.krix.generator.domain.xml.Deklaracja;
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

    private JsonReader jsonReader = new JsonReaderServiceImpl(Deklaracja.class);
    private Calendar calendar = Calendar.getInstance();



    private String correctJsonInput =
                    "{" +
                    "    \"Naglowek\": {" +
                    "                    \"IdWiadomosci\": 1," +
                    "                    \"Rok\": \"2017\"," +
                    "                    \"KodFormularza\": {" +
                    "                                           \"value\": \"CRS-1\"," +
                    "                                           \"kodSystemowy\": \"CRS-1 (1)\"," +
                    "                                           \"wersjaSchemy\": \"1-0E\"" +
                    "                                       }," +
                    "                    \"WariantFormularza\": 1," +
                    "                    \"IdWiadomosciKorygowanej\": [\"A\", \"B\", \"C\"]" +
                    "                  }," +
                    "     \"CRS\":     [{" +
                    "                    \"ReportingFI\": {" +
                    "                                        \"DocSpec\": {" +
                    "                                                            \"DocTypeIndic\": \"OECD0\"," +
                    "                                                            \"DocRefId\": \"20173866688995361091\"," +
                    "                                                            \"CorrDocRefId\": \"A\"" +
                    "                                                     }," +
                    "                                        \"ResCountryCode\": \"PL\"," +
                    "                                        \"IN\": \"4942118399\"," +
                    "                                        \"Name\": {" +
                    "                                                            \"value\": \"bank\"" +
                    "                                                  }," +
                            "      \"Address\" : {" +
                            "        \"content\" : [ {" +
                            "          \"name\" : \"{http://crd.gov.pl/wzor/2017/07/12/4274/}CountryCode\"," +
                            "          \"declaredType\" : \"java.lang.String\"," +
                            "          \"scope\" : \"pl.krix.generator.domain.xml.AddressType\"," +
                            "          \"value\" : \"PL\"" +
                            "        }, {" +
                            "          \"name\" : \"{http://crd.gov.pl/wzor/2017/07/12/4274/}AddressFree\"," +
                            "          \"declaredType\" : \"java.lang.String\"," +
                            "          \"scope\" : \"pl.krix.generator.domain.xml.AddressType\"," +
                            "          \"value\" : \"a\"" +
                            "        } ]," +
                            "        \"legalAddressType\" : \"OECD_301\"" +
                            "      }" +
                    "                                     }" +
                    "                   }]," +
                    "     \"Podmiot1\": {" +
                    "                    \"NazwaPodmiotu\": \"bank\"," +
                    "                    \"NIP\": \"1234567\"" +
                    "                   }," +
                    "     \"version\": \"2.0\"" +
                    "}";


    private String badJsonInput =
            "{" +
                    ";;;'''+++" +
            "}";

    @Test
    public void testCorrectJsonInputReading() throws JsonReaderException {
        InputStream inputStream = new ByteArrayInputStream(correctJsonInput.getBytes(StandardCharsets.UTF_8));
        Deklaracja declaration = (Deklaracja) jsonReader.read(inputStream);
        calendar.setTime(declaration.getNaglowek().getRok());
        assertEquals("1", declaration.getNaglowek().getIdWiadomosci());
        assertEquals(2017, calendar.get(Calendar.YEAR));
        assertEquals("CRS-1", declaration.getNaglowek().getKodFormularza().getValue().value());
        assertEquals("CRS-1 (1)", declaration.getNaglowek().getKodFormularza().getKodSystemowy());
        assertEquals("1-0E", declaration.getNaglowek().getKodFormularza().getWersjaSchemy());
        assertEquals(1, declaration.getNaglowek().getWariantFormularza());
        assertEquals("A", declaration.getNaglowek().getIdWiadomosciKorygowanej().get(0));
        assertEquals("B", declaration.getNaglowek().getIdWiadomosciKorygowanej().get(1));
        assertEquals("C", declaration.getNaglowek().getIdWiadomosciKorygowanej().get(2));
        assertEquals("bank", declaration.getPodmiot1().getNazwaPodmiotu());
        assertEquals("1234567", declaration.getPodmiot1().getNIP());
        assertEquals("2.0", declaration.getVersion());
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
