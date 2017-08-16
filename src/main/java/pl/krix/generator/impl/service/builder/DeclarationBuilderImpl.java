package pl.krix.generator.impl.service.builder;

import pl.krix.generator.api.service.builder.DeclarationBuilder;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.DeclarationHeaderUniqueIdGenerationException;
import pl.krix.generator.util.ObjectChecksumUtil;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationBuilderImpl implements DeclarationBuilder {

    ObjectFactory objectFactory = new ObjectFactory();

    private Deklaracja deklaracja = objectFactory.createDeklaracja();

    @Override
    public DeclarationBuilder header(TNaglowek header){
        this.deklaracja.setNaglowek(header);
        return this;
    }

    @Override
    public DeclarationBuilder crsBodyList(List<CrsBodyType> crsBodyTypeList){
        this.deklaracja.getCRS().addAll(crsBodyTypeList);
        return this;
    }

    @Override
    public Deklaracja build(){
        if(headerDataIsSet()){
            generateIdsForHeaderAndCRSRaports();
        }
        return this.deklaracja;
    }

    private void generateIdsForHeaderAndCRSRaports() {
        this.deklaracja.getNaglowek().setIdWiadomosci(generateHeaderId(this.deklaracja));
        for(CrsBodyType crs : this.deklaracja.getCRS()){
            crs.getReportingFI().getDocSpec().setDocRefId(generateDocRefId(crs));
        }
    }

    private String generateDocRefId(CrsBodyType crs) {
        String id = headerYearAsString() + ObjectChecksumUtil.objectToChecksumSHA1(crs);
        return limitString(id, 20);
    }

    private Boolean headerDataIsSet() {
        return this.deklaracja.getNaglowek() != null && this.deklaracja.getNaglowek().getRok() != null;
    }

    private String generateHeaderId(Deklaracja deklaracja) {
        String id = String.format("%s%s", headerYearAsString(), ObjectChecksumUtil.objectToHexSHA1(deklaracja));
        return limitString(id, 15);
    }

    private String limitString(String string, Integer limit){
        return string.length() > limit ? string.substring(0, limit) : string;

    }

    private String headerYearAsString() {
        return getYearStringFromDate(deklaracja.getNaglowek().getRok());
    }


    private String getYearStringFromDate(Date rok) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deklaracja.getNaglowek().getRok());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
