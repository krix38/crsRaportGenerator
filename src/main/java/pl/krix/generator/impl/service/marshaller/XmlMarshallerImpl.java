package pl.krix.generator.impl.service.marshaller;

import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.exception.MarshallingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.OutputStream;

/**
 * Created by krix on 07.08.2017.
 */
public class XmlMarshallerImpl implements XmlMarshaller {

    private static final String MARSHALLER_ENCODING = "UTF-8";

    private Marshaller marshaller;

    public XmlMarshallerImpl(JAXBContext jaxbContext) throws JAXBException {
        this.marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, MARSHALLER_ENCODING);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    @Override
    public void marshallToXml(Deklaracja deklaracja, OutputStream outputStream) throws MarshallingException {
        try {
            marshaller.marshal(deklaracja, outputStream);
        } catch (JAXBException exception) {
            throw new MarshallingException("output marshalling error", exception);
        }
    }

    @Override
    public String getEncoding() {
        return MARSHALLER_ENCODING;
    }

}
