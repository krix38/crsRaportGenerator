package pl.krix.generator.impl.service.marshaller;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.*;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
/**
 * Created by krix on 07.08.2017.
 */
public class MarshallerServiceTest {


    private ObjectFactory objectFactory = new ObjectFactory();
    private XmlMarshaller xmlMarshaller;
    private Deklaracja deklaracja;

    @Before
    public void setup() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");
        Date year = dateFormat.parse("2017");

        this.xmlMarshaller = new XmlMarshallerImpl(Deklaracja.class);
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
    }

    @Test
    public void marshallingTest() throws MarshallingException, UnsupportedEncodingException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMarshaller.marshallToXml(this.deklaracja, byteArrayOutputStream);
        String outputXml = byteArrayOutputStream.toString(xmlMarshaller.getEncoding());
        assertEquals("2017711F88C0EA3", findStringBetweenStrings(outputXml, "<IdWiadomosci>", "</IdWiadomosci>"));
        assertEquals("2017", findStringBetweenStrings(outputXml, "<Rok>", "</Rok>"));

    }

    @Test(expected = InvalidMarshallerInputException.class)
    public void marshallerNullInputTest() throws MarshallingException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMarshaller.marshallToXml(null, byteArrayOutputStream);
    }

    @Test(expected = InvalidMarshallerOutputArgumentException.class)
    public void marshallerNullOutputArgumentTest() throws MarshallingException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        xmlMarshaller.marshallToXml(this.deklaracja, null);
    }


    private String findStringBetweenStrings(String input, String first, String second){
        return input.substring(input.indexOf(first) + first.length(), input.indexOf(second));
    }
}
