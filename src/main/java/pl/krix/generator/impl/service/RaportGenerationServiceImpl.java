package pl.krix.generator.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.api.service.builder.DeclarationBuilder;
import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.HeaderJsonFileNotFoundException;
import pl.krix.generator.exception.ProcessingCsvInputException;
import pl.krix.generator.impl.service.builder.DeclarationBuilderImpl;
import pl.krix.generator.impl.service.deserializer.CsvDeserializerServiceImpl;
import pl.krix.generator.impl.service.mapper.CsvToXmlMapperImpl;
import pl.krix.generator.impl.service.marshaller.XmlMarshallerImpl;
import pl.krix.generator.impl.service.reader.HeaderServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by krix on 13.08.2017.
 */
public class RaportGenerationServiceImpl implements RaportGenerationService {

    private ObjectFactory objectFactory = new ObjectFactory();

    private CsvDeserializerService deserializerService;
    private CsvToXmlMapper mapper;
    private XmlMarshaller marshaller;
    private HeaderReader headerReader;
    private DeclarationBuilder declarationBuilder;

    private FileInputStream jsonHeaderInputFile;

    private static final Logger logger = LoggerFactory.getLogger(RaportGenerationServiceImpl.class);


    private static final String DEFAULT_HEADER_CONFIGURATION_PATH = "header.json";

    public RaportGenerationServiceImpl()  {
        this(new CsvDeserializerServiceImpl(),
                new CsvToXmlMapperImpl(),
                new XmlMarshallerImpl(Deklaracja.class),
                new HeaderServiceImpl(),
                new DeclarationBuilderImpl());
    }

    public RaportGenerationServiceImpl(CsvDeserializerService deserializerService, CsvToXmlMapper mapper, XmlMarshaller marshaller, HeaderReader headerReader, DeclarationBuilder declarationBuilder) {
        this.deserializerService = deserializerService;
        this.mapper = mapper;
        this.marshaller = marshaller;
        this.headerReader = headerReader;
        this.declarationBuilder = declarationBuilder;

        try {
            this.jsonHeaderInputFile = new FileInputStream(new File(DEFAULT_HEADER_CONFIGURATION_PATH));
        } catch (FileNotFoundException e) {
            throw new HeaderJsonFileNotFoundException("Could not open header.json file");
        }

    }


    @Override
    public void generate(String csvInputPath){
        try (Stream<String> stream = Files.lines(Paths.get(csvInputPath))) {
            processCsvStream(stream);
        } catch (IOException e) {
            throw new ProcessingCsvInputException("Failed to process csv input", e);
        }
    }

    //TODO: refactor limit crsBodyTypeList to 500 elements per declaration
    private void processCsvStream(Stream<String> csvStream){
        TNaglowek header = headerReader.readHeder(jsonHeaderInputFile);
        List<CrsBodyType> crsBodyTypeList = deserializeCsvStreamToCrsBodyType(csvStream);
        Deklaracja declaration = generateDeclaration(header, crsBodyTypeList);
        marshaller.marshallToXml(declaration, System.out);
    }

    private List<CrsBodyType> deserializeCsvStreamToCrsBodyType(Stream<String> csvStream){
        return csvStream
                .map(deserializerService::deserializeToCsv)
                .map(mapper::map)
                .collect(Collectors.toList());
    }


    private Deklaracja generateDeclaration(TNaglowek header, List<CrsBodyType> crsBodyTypeList){
        return declarationBuilder
                .header(header)
                .crsBodyList(crsBodyTypeList)
                .build();
    }

}
