package pl.krix.generator.exception;

import java.io.IOException;

/**
 * Created by krix on 12.08.2017.
 */
public class JsonReaderException extends RaportGenerationException {
    public JsonReaderException(String message) {
        super(message);
    }

    public JsonReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}