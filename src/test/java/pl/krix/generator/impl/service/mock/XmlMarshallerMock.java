package pl.krix.generator.impl.service.mock;

import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.*;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallerException;
import pl.krix.generator.impl.service.marshaller.XmlMarshallerImpl;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by krix on 15.08.2017.
 */
public class XmlMarshallerMock implements XmlMarshaller {

    private ObjectFactory objectFactory = new ObjectFactory();
    private Deklaracja deklaracja = objectFactory.createDeklaracja();

    @Override
    public <InputType> void marshallToXml(InputType inputObject, OutputStream outputStream) throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {

    }

    @Override
    public <InputType> InputType unmarshallFromXml(InputStream inputStream) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
        Date year = null;
        try {
            year = dateFormat.parse("2017");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.deklaracja = objectFactory.createDeklaracja();
        this.deklaracja.setNaglowek(objectFactory.createTNaglowek());
        this.deklaracja.getNaglowek().setIdWiadomosci("2017711F88C0EA3");
        this.deklaracja.getNaglowek().setRok(year);
        this.deklaracja.getNaglowek().setKodFormularza(objectFactory.createTNaglowekKodFormularza());
        this.deklaracja.getNaglowek().getKodFormularza().setValue(TKodFormularza.CRS_1);
        this.deklaracja.getNaglowek().getKodFormularza().setWersjaSchemy("1-0E");
        this.deklaracja.getNaglowek().getKodFormularza().setKodSystemowy("CRS-1 (1)");
        this.deklaracja.getNaglowek().setWariantFormularza((byte)0x01);
        this.deklaracja.setVersion("2.0");
        this.deklaracja.setPodmiot1(objectFactory.createDeklaracjaPodmiot1());
        this.deklaracja.getPodmiot1().setNazwaPodmiotu("bank");
        this.deklaracja.getPodmiot1().setNIP("4942118399");
        this.deklaracja.getCRS().add(new CrsBodyType());
        this.deklaracja.getCRS().get(0).setReportingFI(objectFactory.createCorrectableFIPartyType());
        this.deklaracja.getCRS().get(0).getReportingFI().setDocSpec(objectFactory.createDocSpecFIType());
        this.deklaracja.getCRS().get(0).getReportingFI().getDocSpec().setDocTypeIndic(OECDDocTypeIndicFIEnumType.OECD_0);
        this.deklaracja.getCRS().get(0).getReportingFI().getDocSpec().setCorrDocRefId("1");
        this.deklaracja.getCRS().get(0).getReportingFI().getDocSpec().setDocRefId("1");
        this.deklaracja.getCRS().get(0).getReportingFI().setResCountryCode("PL");
        this.deklaracja.getCRS().get(0).getReportingFI().setIN("4942118399");
        this.deklaracja.getCRS().get(0).getReportingFI().setName(objectFactory.createNameOrganisationType());
        this.deklaracja.getCRS().get(0).getReportingFI().getName().setValue("bank");
        this.deklaracja.getCRS().get(0).getReportingFI().setAddress(objectFactory.createAddressType());
        this.deklaracja.getCRS().get(0).getReportingFI().getAddress().setLegalAddressType(OECDLegalAddressTypeEnumType.OECD_301);
        this.deklaracja.getCRS().get(0).getReportingFI().getAddress().getContent().add(objectFactory.createAddressTypeCountryCode("PL"));
        this.deklaracja.getCRS().get(0).getReportingFI().getAddress().getContent().add(objectFactory.createAddressTypeAddressFree("a"));
        this.deklaracja.getCRS().get(0).setReportingGroup(objectFactory.createCrsBodyTypeReportingGroup());

        return (InputType) deklaracja;
    }

    @Override
    public String getEncoding() {
        return "UTF-8";
    }
}
