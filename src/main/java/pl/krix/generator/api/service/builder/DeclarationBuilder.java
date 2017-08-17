package pl.krix.generator.api.service.builder;

import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.TNaglowek;

import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public interface DeclarationBuilder {
    DeclarationBuilder header(TNaglowek header);

    DeclarationBuilder crsBodyList(List<CrsBodyType> crsBodyTypeList);

    Deklaracja build();

    DeclarationBuilder sendingEntity(Deklaracja.Podmiot1 entity);
}
