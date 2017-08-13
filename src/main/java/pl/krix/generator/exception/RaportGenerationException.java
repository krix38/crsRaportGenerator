package pl.krix.generator.exception;

/**
 * Created by krix on 13.08.2017.
 */
public class RaportGenerationException extends Exception {
    public RaportGenerationException(String message) {
        super(message);
    }

    public RaportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
