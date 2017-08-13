package pl.krix.generator.exception;

import java.io.IOException;

/**
 * Created by krix on 12.08.2017.
 */
public class HeaderReaderException extends RaportGenerationException {
    public HeaderReaderException(String message) {
        super(message);
    }

    public HeaderReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}