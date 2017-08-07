package pl.krix.generator.api.service.marshaller;

import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.exception.MarshallingException;

import java.io.OutputStream;

/**
 * Created by krix on 07.08.2017.
 */
public interface XmlMarshaller {
    void marshallToXml(Deklaracja deklaracja, OutputStream outputStream) throws MarshallingException;
    String getEncoding();
}
