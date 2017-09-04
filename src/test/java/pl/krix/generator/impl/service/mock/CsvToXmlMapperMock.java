package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.domain.xml.CorrectableAccountReportType;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.exception.InvalidMapperInputException;

/**
 * Created by krix on 15.08.2017.
 */
public class CsvToXmlMapperMock implements CsvToXmlMapper {

    ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public CorrectableAccountReportType map(Csv csvSource) throws InvalidMapperInputException {
        return objectFactory.createCorrectableAccountReportType();
    }
}
