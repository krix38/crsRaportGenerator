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
import pl.krix.generator.exception.MissingMappingException;
import pl.krix.generator.exception.RaportGenerationException;
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

    public RaportGenerationServiceImpl() throws FileNotFoundException, MissingMappingException, JAXBException {
        this(new CsvDeserializerServiceImpl(),
                new CsvToXmlMapperImpl(),
                new XmlMarshallerImpl(JAXBContext.newInstance(Deklaracja.class)),
                new HeaderReaderImpl());
    }

    public RaportGenerationServiceImpl(CsvDeserializerService deserializerService, CsvToXmlMapper mapper, XmlMarshaller marshaller, HeaderReader headerReader) throws FileNotFoundException {
        this.deserializerService = deserializerService;
        this.mapper = mapper;
        this.marshaller = marshaller;
        this.headerReader = headerReader;

        this.jsonHeaderInputFile = new FileInputStream(new File(DEFAULT_HEADER_CONFIGURATION_PATH));

    }


    @Override
    public void generate(String csvInputPath){
        try (Stream<String> stream = Files.lines(Paths.get(csvInputPath))) {
            processCsvStream(stream);
        } catch (IOException e) {
            System.err.printf("Error while reading input csv: %s%n", e.getMessage());
        }
    }


    //TODO: refactor
    private void processCsvStream(Stream<String> csvStream){
        Deklaracja declaration = objectFactory.createDeklaracja();
        try{

            declaration.setNaglowek(headerReader.readHeder(jsonHeaderInputFile));

            List<CrsBodyType> crsBodyTypeList = csvStream
                    .map(deserializerService::deserializeToCsv)
                    .map(mapper::map)
                    .collect(Collectors.toList());

            declaration.getCRS().addAll(crsBodyTypeList);
            marshaller.marshallToXml(declaration, new FileOutputStream(new File("raport.xml")));
        }catch (RaportGenerationException exception){

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
