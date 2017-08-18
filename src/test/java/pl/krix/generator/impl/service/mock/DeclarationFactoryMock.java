package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.builder.DeclarationFactory;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.TNaglowek;

import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationFactoryMock implements DeclarationFactory {

    @Override
    public Deklaracja generateDeclaration(List<CrsBodyType> crsBodyTypeList) {
        return new Deklaracja();
    }
}
