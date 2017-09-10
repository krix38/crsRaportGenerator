package pl.krix.generator.impl.service.mapper;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.domain.xml.*;
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
        BasicConfigurator.configure();
        mapper =  new CsvToXmlMapperImpl();
        correctCsvMock.setDocTypeIndic("OECD_1");
        correctCsvMock.setAccountNumber("1234");
        correctCsvMock.setAccountHolderName("JAN");
        correctCsvMock.setAccountHolderLastName("KOWALSKI");
        correctCsvMock.setAccountHolderBirthCountry("PL");
        correctCsvMock.setAccountHolderLegalAddressType("OECD_301");
    }

    @Test
    public void mapperTest() throws InvalidMapperInputException {
        CorrectableAccountReportType accountReport = mapper.map(correctCsvMock);
        assertEquals(OECDDocTypeIndicEnumType.OECD_1, accountReport.getDocSpec().getDocTypeIndic());
        assertEquals("1234", accountReport.getAccountNumber().getValue());
        assertEquals("JAN", accountReport.getAccountHolder().getIndividual().getName().getFirstName());
        assertEquals("KOWALSKI", accountReport.getAccountHolder().getIndividual().getName().getLastName());
        assertEquals("PL", accountReport.getAccountHolder().getIndividual().getBirthInfo().getCountryInfo().getCountryCode());
        assertEquals(OECDLegalAddressTypeEnumType.OECD_301, accountReport.getAccountHolder().getIndividual().getAddress().get(0).getLegalAddressType());
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
