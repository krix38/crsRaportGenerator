package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.factory.DeclarationFactory;
import pl.krix.generator.domain.xml.CorrectableAccountReportType;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;

import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationFactoryMock implements DeclarationFactory {


    @Override
    public Deklaracja generateDeclaration(List<CorrectableAccountReportType> accountReportList) {
        return new Deklaracja();
    }
}
