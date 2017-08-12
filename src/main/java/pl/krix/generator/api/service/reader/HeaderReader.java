package pl.krix.generator.api.service.reader;

import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.HeaderReaderException;

import java.io.InputStream;

/**
 * Created by krix on 12.08.2017.
 */
public interface HeaderReader {
    TNaglowek readHeder(InputStream inputStream) throws HeaderReaderException;
}
