package pl.krix.generator.api.service.factory;

import pl.krix.generator.domain.xml.CorrectableAccountReportType;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;

import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public interface DeclarationFactory {
    Deklaracja generateDeclaration(List<CorrectableAccountReportType> accountReportList);
}
