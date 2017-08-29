package pl.krix.generator.impl.service.marshaller;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallerCreationException;
import pl.krix.generator.exception.MarshallerException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by krix on 07.08.2017.
 */
public class XmlMarshallerImpl implements XmlMarshaller {

    private static final String MARSHALLER_ENCODING = "UTF-8";

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private static final String DEFAULT_SCHEMA_LOCATION = "xsd/schemat-CRS-11-wersja-ostateczna.xsd";

    public XmlMarshallerImpl(Class tClass) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(tClass);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaSource = getSchemaSource();
            this.marshaller = jaxbContext.createMarshaller();
            this.unmarshaller = jaxbContext.createUnmarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, MARSHALLER_ENCODING);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setSchema(schemaFactory.newSchema(schemaSource));
        } catch (JAXBException | SAXException e) {
            throw new MarshallerCreationException("Failed to create marshaller", e);
        }
    }

    private Source getSchemaSource() {
        return new StreamSource(this.getClass().getClassLoader()
                .getResourceAsStream(DEFAULT_SCHEMA_LOCATION));
    }

    @Override
    public <InputType> void  marshallToXml(InputType inputObject, OutputStream outputStream) throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        checkArguments(inputObject, outputStream);
        try {
            marshaller.marshal(inputObject, outputStream);
        } catch (JAXBException exception) {
            if(exception.getLinkedException() instanceof SAXParseException){
                throw new MarshallerException(String.format("Output marshalling error: %s", exception.getLinkedException().getMessage()), exception);
            }
            throw new MarshallerException("Output marshalling error", exception);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <InputType> InputType unmarshallFromXml(InputStream inputStream){
        checkArguments(inputStream);
        try {
            return (InputType) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException exception) {
            throw new MarshallerException("Input unmarshalling error", exception);
        }
    }

    private void checkArguments(InputStream inputStream) {
        if(inputStream == null){
            throw new InvalidMarshallerInputException("Marshaller input cant be null");
        }
    }

    private <InputType> void checkArguments(InputType inputType, OutputStream outputStream) throws InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        if(inputType == null){
            throw new InvalidMarshallerInputException("Marshaller input cant be null");
        }
        if(outputStream == null){
            throw new InvalidMarshallerOutputArgumentException("Marshaller output argument cant be null");
        }
    }

    @Override
    public String getEncoding() {
        return MARSHALLER_ENCODING;
    }

}
