package pl.krix.generator.impl.service.factory;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.factory.DeclarationFactory;
import pl.krix.generator.domain.xml.*;
import pl.krix.generator.impl.service.mock.XmlMarshallerMock;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 17.08.2017.
 */
public class DeclarationFactoryTest {

    private ObjectFactory objectFactory = new ObjectFactory();

    private DeclarationFactory declarationFactory;

    private List<CorrectableAccountReportType> accountReportList = new ArrayList<>();

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
        setCrsBodyTypeList();
        this.declarationFactory = new DeclarationFactoryImpl(new XmlMarshallerMock(), new ByteArrayInputStream(correctXml.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void checkIfCorrectIdsAreGenerated(){
        Deklaracja declaration = declarationFactory.generateDeclaration(this.accountReportList);

        assertEquals("1", declaration.getCRS().get(0).getReportingFI().getDocSpec().getCorrDocRefId());
        assertEquals("CRS-1 (1)", declaration.getNaglowek().getKodFormularza().getKodSystemowy());
        assertEquals("20174FC4A0669E1", declaration.getNaglowek().getIdWiadomosci());
        assertEquals("20179317284428146164", declaration.getCRS().get(0).getReportingFI().getDocSpec().getDocRefId());

    }


    public void setCrsBodyTypeList(){
        for(int i = 0 ; i < 10; i++){
            CorrectableAccountReportType accountreport = objectFactory.createCorrectableAccountReportType();
            DocSpecType docSpec = objectFactory.createDocSpecType();
            accountreport.setDocSpec(docSpec);
            accountreport.getDocSpec().setCorrDocRefId(String.valueOf(i));
            accountReportList.add(accountreport);
        }
    }

}
