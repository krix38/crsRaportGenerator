package pl.krix.generator.exception;

import javax.xml.bind.JAXBException;

/**
 * Created by krix on 15.08.2017.
 */
public class MarshallerCreationException extends RaportGenerationException {
    public MarshallerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
