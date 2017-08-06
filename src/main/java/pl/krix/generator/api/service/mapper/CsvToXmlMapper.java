package pl.krix.generator.api.service.mapper;

import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.csv.Csv;

/**
 * Created by krix on 05.08.2017.
 */
public interface CsvToXmlMapper {
    Deklaracja map(Csv csvSource);
}
