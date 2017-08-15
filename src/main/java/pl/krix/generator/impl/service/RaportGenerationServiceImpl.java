package pl.krix.generator.impl.service;

import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.*;
import pl.krix.generator.impl.service.deserializer.CsvDeserializerServiceImpl;
import pl.krix.generator.impl.service.mapper.CsvToXmlMapperImpl;
import pl.krix.generator.impl.service.marshaller.XmlMarshallerImpl;
import pl.krix.generator.impl.service.reader.HeaderReaderImpl;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.*;
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

    private FileInputStream jsonHeaderInputFile;

    private static final String DEFAULT_HEADER_CONFIGURATION_PATH = "header.json";

    public RaportGenerationServiceImpl()  {
        this(new CsvDeserializerServiceImpl(),
                new CsvToXmlMapperImpl(),
                new XmlMarshallerImpl(Deklaracja.class),
                new HeaderReaderImpl());
    }

    public RaportGenerationServiceImpl(CsvDeserializerService deserializerService, CsvToXmlMapper mapper, XmlMarshaller marshaller, HeaderReader headerReader) {
        this.deserializerService = deserializerService;
        this.mapper = mapper;
        this.marshaller = marshaller;
        this.headerReader = headerReader;

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
        Deklaracja declaration = objectFactory.createDeklaracja();
        declaration.setNaglowek(header);
        declaration.getCRS().addAll(crsBodyTypeList);
        return declaration;
    }

}
