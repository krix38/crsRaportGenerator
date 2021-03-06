package pl.krix.generator.api.service.mapper;

import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidMapperInputException;

/**
 * Created by krix on 05.08.2017.
 */
public interface CsvToXmlMapper {
    CrsBodyType map(Csv csvSource) throws InvalidMapperInputException;
}
