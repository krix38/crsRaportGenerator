package pl.krix.generator.impl.service.builder;

import pl.krix.generator.api.service.builder.DeclarationBuilder;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.DeclarationHeaderUniqueIdGenerationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
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
        if(this.deklaracja.getNaglowek() != null){
            this.deklaracja.getNaglowek().setIdWiadomosci(generateRandomId(this.deklaracja));
        }
        return this.deklaracja;
    }

    private String generateRandomId(Deklaracja deklaracja) {
        String yearString = getYearStringFromDate(deklaracja.getNaglowek().getRok());
        String id = String.format("%s%s", yearString, objectToSHA1(deklaracja));
        return id.length() > 15 ? id.substring(0, 15) : id;
    }

    private String objectToSHA1(Object object){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            BigInteger bigIntegerHash = new BigInteger(1, MessageDigest.getInstance("SHA1").digest(byteArrayOutputStream.toByteArray()));
            return String.valueOf(bigIntegerHash);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new DeclarationHeaderUniqueIdGenerationException("Failed to generate unique id for declaration header", e);
        }
    }



    private String getYearStringFromDate(Date rok) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deklaracja.getNaglowek().getRok());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
