package pl.krix.generator.impl.service.builder;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.api.service.builder.DeclarationBuilder;
import pl.krix.generator.domain.xml.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 17.08.2017.
 */
public class DeclarationBuilderTest {

    private ObjectFactory objectFactory = new ObjectFactory();

    private DeclarationBuilder declarationBuilder = new DeclarationBuilderImpl();

    private List<CrsBodyType> crsBodyTypeList = new ArrayList<>();

    private TNaglowek header = objectFactory.createTNaglowek();

    @Before
    public void setup() throws ParseException {
        setCrsBodyTypeList();
        setHeader();
    }

    @Test
    public void checkIfCorrectIdsAreGenerated(){
        Deklaracja declaration = declarationBuilder
                .crsBodyList(crsBodyTypeList)
                .header(header)
                .build();

        assertEquals("2", declaration.getCRS().get(2).getReportingFI().getDocSpec().getCorrDocRefId());
        assertEquals("1", declaration.getNaglowek().getKodFormularza().getKodSystemowy());
        assertEquals("2017E813A6FF12D", declaration.getNaglowek().getIdWiadomosci());
        assertEquals("20173866688995361091", declaration.getCRS().get(0).getReportingFI().getDocSpec().getDocRefId());

    }

    private void setHeader() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        header.setRok(simpleDateFormat.parse("2017"));
        TNaglowek.KodFormularza formCode = objectFactory.createTNaglowekKodFormularza();
        formCode.setKodSystemowy("1");
        formCode.setWersjaSchemy("1");
        formCode.setValue(TKodFormularza.CRS_1);
        header.setKodFormularza(formCode);
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
