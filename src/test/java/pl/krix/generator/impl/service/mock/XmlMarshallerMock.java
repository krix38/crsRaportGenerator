package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallerException;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by krix on 15.08.2017.
 */
public class XmlMarshallerMock implements XmlMarshaller {

    ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public <InputType> void marshallToXml(InputType inputObject, OutputStream outputStream) throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {

    }

    @Override
    public <InputType> InputType unmarshallFromXml(InputStream inputStream) {

        Deklaracja declaration = objectFactory.createDeklaracja();
        TNaglowek.KodFormularza formCode = objectFactory.createTNaglowekKodFormularza();
        try {
            declaration.setNaglowek(objectFactory.createTNaglowek());
            declaration.getNaglowek().setRok((new SimpleDateFormat("YYYY")).parse("2017"));
            declaration.getNaglowek().setKodFormularza(formCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (InputType) declaration;
    }

    @Override
    public String getEncoding() {
        return "UTF-8";
    }
}
