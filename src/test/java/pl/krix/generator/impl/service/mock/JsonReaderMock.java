package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.reader.JsonReader;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TKodFormularza;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.JsonReaderException;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by krix on 15.08.2017.
 */
public class JsonReaderMock implements JsonReader {

    ObjectFactory objectFactory = new ObjectFactory();


    @Override
    public Object read(InputStream inputStream) throws JsonReaderException {
        Deklaracja declaration = objectFactory.createDeklaracja();
        TNaglowek.KodFormularza formCode = objectFactory.createTNaglowekKodFormularza();
        try {
            declaration.setNaglowek(objectFactory.createTNaglowek());
            declaration.getNaglowek().setRok((new SimpleDateFormat("YYYY")).parse("2017"));
            declaration.getNaglowek().setKodFormularza(formCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return declaration;
    }
}
