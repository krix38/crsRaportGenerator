package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.HeaderReaderException;

import java.io.InputStream;

/**
 * Created by krix on 15.08.2017.
 */
public class HeadeReaderMock implements HeaderReader {

    ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public TNaglowek readHeder(InputStream inputStream) throws HeaderReaderException {
        TNaglowek header = objectFactory.createTNaglowek();
        header.setIdWiadomosci("1");
        return header;
    }
}
