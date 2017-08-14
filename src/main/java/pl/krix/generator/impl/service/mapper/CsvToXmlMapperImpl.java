package pl.krix.generator.impl.service.mapper;

import org.dozer.DozerBeanMapper;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidMapperInputException;
import pl.krix.generator.exception.MissingMappingException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by krix on 05.08.2017.
 */
public class CsvToXmlMapperImpl implements CsvToXmlMapper {

    private static final String DEFAULT_DOZER_MAPPING_FILE_PATH = "mappings/mappings.xml";


    private DozerBeanMapper mapper;


    public CsvToXmlMapperImpl(String mappingFilePath) throws FileNotFoundException, MissingMappingException {
        File mapping = getDozerMappingFile(mappingFilePath);
        mapper = new DozerBeanMapper();
        mapper.addMapping(new FileInputStream(mapping));
    }

    public CsvToXmlMapperImpl() throws FileNotFoundException, MissingMappingException {
        this(DEFAULT_DOZER_MAPPING_FILE_PATH);
    }

    private File getDozerMappingFile(String mappingFilePath) throws MissingMappingException {
        URL resource = this.getClass().getClassLoader().getResource(mappingFilePath);
        if(resource != null){
            return new File(resource.getFile());
        }else {
            throw new MissingMappingException(String.format("Could not find mapping: %s", mappingFilePath));
        }
    }

    @Override
    public CrsBodyType map(Csv csvSource) throws InvalidMapperInputException {
        checkInput(csvSource);
        return mapper.map(csvSource, CrsBodyType.class);
    }

    private void checkInput(Csv csvSource) throws InvalidMapperInputException {
        if(csvSource == null){
            throw new InvalidMapperInputException("Mapper input cant be null");
        }
    }

}
