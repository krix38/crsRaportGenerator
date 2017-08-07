package pl.krix.generator.api.service.marshaller;

import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;

import java.io.OutputStream;

/**
 * Created by krix on 07.08.2017.
 */
public interface XmlMarshaller {
    void marshallToXml(Deklaracja deklaracja, OutputStream outputStream);

    void marshallToXml(CrsBodyType crsBodyType, OutputStream outputStream);
}
