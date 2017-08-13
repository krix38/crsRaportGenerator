package pl.krix.generator.impl.service;

import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.csv.Csv;
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

/**
 * Created by krix on 13.08.2017.
 */
public class RaportGenerationServiceImpl {

    private ObjectFactory objectFactory = new ObjectFactory();

    private CsvDeserializerService deserializerService;
    private CsvToXmlMapper mapper;
    private XmlMarshaller marshaller;
    private HeaderReader headerReader;

    private FileInputStream jsonHeaderInputFile;
    private FileInputStream csvInputFile;

    private static final String DEFAULT_HEADER_CONFIGURATION_PATH = "header.json";

    public RaportGenerationServiceImpl() throws FileNotFoundException, MissingMappingException, JAXBException {
        this(new CsvDeserializerServiceImpl(),
                new CsvToXmlMapperImpl(),
                new XmlMarshallerImpl(JAXBContext.newInstance(Deklaracja.class)),
                new HeaderReaderImpl()  );
    }

    public RaportGenerationServiceImpl(CsvDeserializerService deserializerService, CsvToXmlMapper mapper, XmlMarshaller marshaller, HeaderReader headerReader) {
        this.deserializerService = deserializerService;
        this.mapper = mapper;
        this.marshaller = marshaller;
        this.headerReader = headerReader;
    }


    //TODO: refactor
    public void generate(String csvInputPath){
        Deklaracja raport = objectFactory.createDeklaracja();
        setInputFiles(csvInputPath, DEFAULT_HEADER_CONFIGURATION_PATH);
        BufferedReader csvBufferedReader = new BufferedReader(new InputStreamReader(csvInputFile));
        try{
            raport.setNaglowek(headerReader.readHeder(jsonHeaderInputFile));
            String line;
            Csv csv;
            while ((line = csvBufferedReader.readLine()) != null){
                csv = deserializerService.deserializeToCsv(line);
                //TODO: mapper...
            }
        }catch (RaportGenerationException exception){

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInputFiles(String csvInputPath, String defaultHeaderConfigurationPath) {
        try {
            this.jsonHeaderInputFile = new FileInputStream(new File(defaultHeaderConfigurationPath));
            this.csvInputFile = new FileInputStream(new File(csvInputPath));
        } catch (FileNotFoundException e) {
            System.err.printf("Input file not found: %s", e.getMessage());
        }

    }
}
