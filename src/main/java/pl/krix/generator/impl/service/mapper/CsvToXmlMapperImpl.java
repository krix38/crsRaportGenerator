package pl.krix.generator.impl.service.mapper;

import org.dozer.DozerBeanMapper;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.MissingMappingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by krix on 05.08.2017.
 */
public class CsvToXmlMapperImpl implements CsvToXmlMapper {

    private static final String DOZER_MAPPING_FILE_PATH = "mappings/mappings.xml";

    private DozerBeanMapper mapper;

    public CsvToXmlMapperImpl() throws FileNotFoundException, MissingMappingException {
        File mapping = getDozerMappingFile();
        mapper = new DozerBeanMapper();
        mapper.addMapping(new FileInputStream(mapping));
    }

    private File getDozerMappingFile() throws MissingMappingException {
        URL resource = this.getClass().getClassLoader().getResource(DOZER_MAPPING_FILE_PATH);
        if(resource != null){
            return new File(resource.getFile());
        }else {
            throw new MissingMappingException(String.format("Could not find mapping: %s", DOZER_MAPPING_FILE_PATH));
        }
    }

    @Override
    public Deklaracja map(Csv csvSource){
        return mapper.map(csvSource, Deklaracja.class);
    }


}
