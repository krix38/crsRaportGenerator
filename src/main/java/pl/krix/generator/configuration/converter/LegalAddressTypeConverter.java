package pl.krix.generator.configuration.converter;

import org.dozer.DozerConverter;
import pl.krix.generator.domain.xml.OECDLegalAddressTypeEnumType;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.PersonPartyType;

/**
 * Created by krix on 10.09.2017.
 */
public class LegalAddressTypeConverter extends DozerConverter<String, PersonPartyType> {

    ObjectFactory objectFactory = new ObjectFactory();

    public LegalAddressTypeConverter() {
        super(String.class, PersonPartyType.class);
    }

    @Override
    public PersonPartyType convertTo(String s, PersonPartyType personPartyType) {
        personPartyType.getAddress().add(objectFactory.createAddressType());
        personPartyType.getAddress().get(0).setLegalAddressType(OECDLegalAddressTypeEnumType.valueOf(s));
        return personPartyType;
    }

    @Override
    public String convertFrom(PersonPartyType personPartyType, String s) {
        return personPartyType.getAddress().get(0).getLegalAddressType().value();
    }
}
