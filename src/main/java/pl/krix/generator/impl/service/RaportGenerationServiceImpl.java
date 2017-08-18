package pl.krix.generator.impl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.api.service.builder.DeclarationFactory;
import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.api.service.mapper.CsvToXmlMapper;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.exception.ProcessingCsvInputException;
import pl.krix.generator.impl.service.builder.DeclarationFactoryImpl;
import pl.krix.generator.impl.service.deserializer.CsvDeserializerServiceImpl;
import pl.krix.generator.impl.service.mapper.CsvToXmlMapperImpl;
import pl.krix.generator.impl.service.marshaller.XmlMarshallerImpl;

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
    private DeclarationFactory declarationFactory;

    private static final Logger logger = LoggerFactory.getLogger(RaportGenerationServiceImpl.class);

    public RaportGenerationServiceImpl()  {
        this(new CsvDeserializerServiceImpl(),
                new CsvToXmlMapperImpl(),
                new XmlMarshallerImpl(Deklaracja.class),
                new DeclarationFactoryImpl());
    }

    public RaportGenerationServiceImpl(CsvDeserializerService deserializerService, CsvToXmlMapper mapper, XmlMarshaller marshaller, DeclarationFactory declarationFactory) {
        this.deserializerService = deserializerService;
        this.mapper = mapper;
        this.marshaller = marshaller;
        this.declarationFactory = declarationFactory;
    }


    @Override
    public void generate(String csvInputPath){
        try (Stream<String> stream = Files.lines(Paths.get(csvInputPath))) {
            processCsvStream(stream);
        } catch (IOException e) {
            throw new ProcessingCsvInputException("Failed to process csv input", e);
        }
    }

    private void processCsvStream(Stream<String> csvStream){
        Deklaracja declaration = declarationFactory.generateDeclaration(deserializeCsvStreamToCrsBodyType(csvStream));
        marshaller.marshallToXml(declaration, System.out);
    }

    private List<CrsBodyType> deserializeCsvStreamToCrsBodyType(Stream<String> csvStream){
        return csvStream
                .map(deserializerService::deserializeToCsv)
                .map(mapper::map)
                .collect(Collectors.toList());
    }



}
