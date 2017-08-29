package pl.krix.generator.api.service.marshaller;

import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallerException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by krix on 07.08.2017.
 */
public interface XmlMarshaller {
    <InputType> void marshallToXml(InputType inputObject, OutputStream outputStream) throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException;

    @SuppressWarnings("unchecked")
    <InputType> InputType unmarshallFromXml(InputStream inputStream);

    String getEncoding();
}
