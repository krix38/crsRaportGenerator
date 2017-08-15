package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallingException;

import java.io.OutputStream;

/**
 * Created by krix on 15.08.2017.
 */
public class XmlMarshallerMock implements XmlMarshaller {
    @Override
    public <InputType> void marshallToXml(InputType inputObject, OutputStream outputStream) throws MarshallingException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {

    }

    @Override
    public String getEncoding() {
        return "UTF-8";
    }
}
