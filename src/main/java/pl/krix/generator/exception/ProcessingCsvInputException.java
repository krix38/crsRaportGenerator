package pl.krix.generator.exception;

import java.io.IOException;

/**
 * Created by krix on 15.08.2017.
 */
public class ProcessingCsvInputException extends RaportGenerationException {

    public ProcessingCsvInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
