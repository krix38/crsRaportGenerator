package pl.krix.generator.impl.service.marshaller;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.exception.MarshallingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
/**
 * Created by krix on 07.08.2017.
 */
public class MarshallerServiceTest {

    private ObjectFactory objectFactory = new ObjectFactory();
    private XmlMarshaller xmlMarshaller;
    private Deklaracja deklaracja;

    @Before
    public void setup() throws JAXBException {
        this.xmlMarshaller = new XmlMarshallerImpl(JAXBContext.newInstance(Deklaracja.class));
        this.deklaracja = objectFactory.createDeklaracja();
        this.deklaracja.setNaglowek(objectFactory.createTNaglowek());
        this.deklaracja.getNaglowek().setIdWiadomosci("1");
    }

    @Test
    public void marshallingTest() throws MarshallingException, UnsupportedEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMarshaller.marshallToXml(this.deklaracja, byteArrayOutputStream);
        String outputXml = byteArrayOutputStream.toString(xmlMarshaller.getEncoding());
        assertEquals("1", findStringBetweenStrings(outputXml, "<IdWiadomosci>", "</IdWiadomosci"));
    }

    private String findStringBetweenStrings(String input, String first, String second){
        return input.substring(input.indexOf(first) + first.length(), input.indexOf(second));
    }
}
