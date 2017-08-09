package pl.krix.generator.impl.service.mapper;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.exception.InvalidMapperInputException;
import pl.krix.generator.exception.MissingMappingException;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 05.08.2017.
 */
public class MapperServiceTest {

    private CsvToXmlMapper mapper;

    private Csv correctCsvMock = new Csv();

    @Before
    public void setup() throws FileNotFoundException, MissingMappingException {
        mapper =  new CsvToXmlMapperImpl();
        correctCsvMock.setMessageId("1");
    }

    @Test
    public void mapperTest() throws InvalidMapperInputException {
        Deklaracja deklaracja = mapper.map(correctCsvMock);
        assertEquals("1", deklaracja.getNaglowek().getIdWiadomosci());
    }

    @Test(expected = MissingMappingException.class)
    public void mapperMappingFileMissingTest() throws FileNotFoundException, MissingMappingException {
        CsvToXmlMapper mapper = new CsvToXmlMapperImpl("unexisting_file");
    }

    @Test(expected = InvalidMapperInputException.class)
    public void mapperMappingNullInput() throws InvalidMapperInputException {
        mapper.map(null);
    }

}
