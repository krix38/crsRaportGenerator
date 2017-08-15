package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.builder.DeclarationBuilder;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.TNaglowek;

import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationBuilderMock implements DeclarationBuilder {
    @Override
    public DeclarationBuilder header(TNaglowek header) {
        return this;
    }

    @Override
    public DeclarationBuilder crsBodyList(List<CrsBodyType> crsBodyTypeList) {
        return this;
    }

    @Override
    public Deklaracja build() {
        return new Deklaracja();
    }
}
