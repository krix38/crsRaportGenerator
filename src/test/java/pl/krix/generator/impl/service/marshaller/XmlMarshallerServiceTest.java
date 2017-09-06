package pl.krix.generator.impl.service.marshaller;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.*;
import pl.krix.generator.exception.InvalidMarshallerInputException;
import pl.krix.generator.exception.InvalidMarshallerOutputArgumentException;
import pl.krix.generator.exception.MarshallerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
/**
 * Created by krix on 07.08.2017.
 */
public class XmlMarshallerServiceTest {


    private ObjectFactory objectFactory = new ObjectFactory();
    private XmlMarshaller xmlMarshaller;
    private Deklaracja deklaracja;

    private String correctXml = "" +
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
            "<Deklaracja xmlns=\"http://crd.gov.pl/wzor/2017/07/12/4274/\" version=\"2.0\">" +
            "    <Naglowek>" +
            "        <KodFormularza kodSystemowy=\"CRS-1 (1)\" wersjaSchemy=\"1-0E\">CRS-1</KodFormularza>" +
            "        <WariantFormularza>1</WariantFormularza>" +
            "        <Rok>2017</Rok>" +
            "        <IdWiadomosci>2017711F88C0EA3</IdWiadomosci>" +
            "    </Naglowek>" +
            "    <Podmiot1>" +
            "        <NazwaPodmiotu>bank</NazwaPodmiotu>" +
            "        <NIP>4942118399</NIP>" +
            "    </Podmiot1>" +
            "    <CRS>" +
            "        <ReportingFI>" +
            "            <ResCountryCode>PL</ResCountryCode>" +
            "            <IN>4942118399</IN>" +
            "            <Name>bank</Name>" +
            "            <Address legalAddressType=\"OECD301\">" +
            "                <CountryCode>PL</CountryCode>" +
            "                <AddressFree>a</AddressFree>" +
            "            </Address>" +
            "            <DocSpec>" +
            "                <DocTypeIndic>OECD0</DocTypeIndic>" +
            "                <DocRefId>1</DocRefId>" +
            "                <CorrDocRefId>1</CorrDocRefId>" +
            "            </DocSpec>" +
            "        </ReportingFI>" +
            "        <ReportingGroup/>" +
            "    </CRS>" +
            "</Deklaracja>";

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

        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().add(objectFactory.createCorrectableAccountReportType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).setDocSpec(objectFactory.createDocSpecType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getDocSpec().setDocTypeIndic(OECDDocTypeIndicEnumType.OECD_1);
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getDocSpec().setDocRefId("123");
        CRSAccountNumberType accountNumberType = objectFactory.createCRSAccountNumberType();
        accountNumberType.setValue("1234");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).setAccountNumber(accountNumberType);
        AccountHolderType accountHolderType = objectFactory.createAccountHolderType();
        PersonPartyType personPartyType = objectFactory.createPersonPartyType();
        NamePersonType namePersonType = objectFactory.createNamePersonType();
        namePersonType.setFirstName("JAN");
        namePersonType.setLastName("KOWALSKI");
        personPartyType.setName(namePersonType);
        PersonPartyType.BirthInfo birthInfo = objectFactory.createPersonPartyTypeBirthInfo();
        PersonPartyType.BirthInfo.CountryInfo countryInfo = objectFactory.createPersonPartyTypeBirthInfoCountryInfo();
        countryInfo.setCountryCode("PL");
        birthInfo.setCountryInfo(countryInfo);
        personPartyType.getAddress().add(objectFactory.createAddressType());
        personPartyType.getAddress().get(0).setLegalAddressType(OECDLegalAddressTypeEnumType.OECD_301);
        personPartyType.getAddress().get(0).getContent().add(objectFactory.createAddressTypeCountryCode("PL"));
        personPartyType.getAddress().get(0).getContent().add(objectFactory.createAddressTypeAddressFree("a"));
        personPartyType.setBirthInfo(birthInfo);
        personPartyType.getResCountryCode().add("PL");
        accountHolderType.setIndividual(personPartyType);
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).setAccountHolder(accountHolderType);
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().add(objectFactory.createControllingPersonType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).setIndividual(objectFactory.createPersonPartyType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getResCountryCode().add("PL");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getTIN().add(objectFactory.createTINCRSType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getTIN().get(0).setValue("PL");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getTIN().get(0).setIssuedBy("PL");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().setName(objectFactory.createNamePersonType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getName().setFirstName("JAN");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getName().setLastName("KOWALSKI");
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getAddress().add(objectFactory.createAddressType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getAddress().get(0).setLegalAddressType(OECDLegalAddressTypeEnumType.OECD_301);
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getAddress().get(0).getContent().add(objectFactory.createAddressTypeCountryCode("PL"));
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getControllingPerson().get(0).getIndividual().getAddress().get(0).getContent().add(objectFactory.createAddressTypeAddressFree("a"));
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).setAccountBalance(objectFactory.createMonAmntType());
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getAccountBalance().setValue(new BigDecimal(123123123));
        this.deklaracja.getCRS().get(0).getReportingGroup().getAccountReport().get(0).getAccountBalance().setCurrCode(CurrCodeType.ALL);

    }

    @Test
    public void marshallingTest() throws MarshallerException, UnsupportedEncodingException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMarshaller.marshallToXml(this.deklaracja, byteArrayOutputStream);
        String outputXml = byteArrayOutputStream.toString(xmlMarshaller.getEncoding());
        assertEquals("2017711F88C0EA3", findStringBetweenStrings(outputXml, "<IdWiadomosci>", "</IdWiadomosci>"));
        assertEquals("2017", findStringBetweenStrings(outputXml, "<Rok>", "</Rok>"));

    }

    @Test
    public void unmarshallingTest() {
        InputStream inputStream = new ByteArrayInputStream(correctXml.getBytes(StandardCharsets.UTF_8));
        Deklaracja deklaracja = xmlMarshaller.unmarshallFromXml(inputStream);
        assertEquals("2017711F88C0EA3", deklaracja.getNaglowek().getIdWiadomosci());
        assertEquals("2017", (new SimpleDateFormat("YYYY")).format(deklaracja.getNaglowek().getRok()));
    }

    @Test(expected = InvalidMarshallerInputException.class)
    public void marshallerNullInputTest() throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMarshaller.marshallToXml(null, byteArrayOutputStream);
    }

    @Test(expected = InvalidMarshallerOutputArgumentException.class)
    public void marshallerNullOutputArgumentTest() throws MarshallerException, InvalidMarshallerInputException, InvalidMarshallerOutputArgumentException {
        xmlMarshaller.marshallToXml(this.deklaracja, null);
    }


    private String findStringBetweenStrings(String input, String first, String second){
        return input.substring(input.indexOf(first) + first.length(), input.indexOf(second));
    }
}
