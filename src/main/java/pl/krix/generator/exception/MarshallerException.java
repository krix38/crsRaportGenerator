package pl.krix.generator.exception;

import javax.xml.bind.JAXBException;

/**
 * Created by krix on 07.08.2017.
 */
public class MarshallerException extends RaportGenerationException {
    public MarshallerException(String message, Throwable cause) {
        super(message, cause);
    }
}
