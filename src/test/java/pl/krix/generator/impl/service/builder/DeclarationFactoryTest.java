package pl.krix.generator.impl.service.builder;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.builder.DeclarationFactory;
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

    private List<CrsBodyType> crsBodyTypeList = new ArrayList<>();

    private String correctXmlInput =
            "{" +
            "    \"naglowek\": {" +
            "                    \"idWiadomosci\": 1," +
            "                    \"rok\": \"2017\"," +
            "                    \"kodFormularza\": {" +
            "                                           \"value\": \"CRS_1\"," +
            "                                           \"kodSystemowy\": \"CRS-1 (1)\"," +
            "                                           \"wersjaSchemy\": \"1-0E\"" +
            "                                       }," +
            "                    \"wariantFormularza\": 1," +
            "                    \"idWiadomosciKorygowanej\": [\"A\", \"B\", \"C\"]" +
            "                  }" +
            "}";

    @Before
    public void setup() throws ParseException {
        setCrsBodyTypeList();
        this.declarationFactory = new DeclarationFactoryImpl(new XmlMarshallerMock(), new ByteArrayInputStream(correctXmlInput.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void checkIfCorrectIdsAreGenerated(){
        Deklaracja declaration = declarationFactory.generateDeclaration(this.crsBodyTypeList);

        assertEquals("2", declaration.getCRS().get(2).getReportingFI().getDocSpec().getCorrDocRefId());
        assertEquals("CRS-1 (1)", declaration.getNaglowek().getKodFormularza().getKodSystemowy());
        assertEquals("2017711F88C0EA3", declaration.getNaglowek().getIdWiadomosci());
        assertEquals("20173866688995361091", declaration.getCRS().get(0).getReportingFI().getDocSpec().getDocRefId());

    }


    public void setCrsBodyTypeList(){
        for(int i = 0 ; i < 10; i++){
            CrsBodyType crsBodyType = objectFactory.createCrsBodyType();
            CorrectableFIPartyType reportingFi = objectFactory.createCorrectableFIPartyType();
            DocSpecFIType docSpec = objectFactory.createDocSpecFIType();
            crsBodyType.setReportingFI(reportingFi);
            crsBodyType.getReportingFI().setDocSpec(docSpec);
            crsBodyType.getReportingFI().getDocSpec().setCorrDocRefId(String.valueOf(i));
            crsBodyTypeList.add(crsBodyType);
        }
    }

}
