package pl.krix.generator.impl.service.builder;

import pl.krix.generator.api.service.builder.DeclarationFactory;
import pl.krix.generator.api.service.reader.JsonReader;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.exception.JsonFileNotFoundException;
import pl.krix.generator.impl.service.reader.JsonReaderServiceImpl;
import pl.krix.generator.util.ObjectChecksumUtil;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationFactoryImpl implements DeclarationFactory {

    ObjectFactory objectFactory = new ObjectFactory();

    private JsonReader jsonReader;

    private InputStream metaDataInputStream ;

    private static final String DEFAULT_METADATA_INPUT_PATH = "metadata.json";

    public DeclarationFactoryImpl(JsonReader jsonReader, InputStream inputStream) {
        this.jsonReader = jsonReader;
        this.metaDataInputStream = inputStream;
    }

    @SuppressWarnings("unchecked")
    public DeclarationFactoryImpl() {
        this(new JsonReaderServiceImpl(Deklaracja.class), null);
        try {
            this.metaDataInputStream = new FileInputStream(new File(DEFAULT_METADATA_INPUT_PATH));
        } catch (FileNotFoundException e) {
            throw new JsonFileNotFoundException("Metadata json file not found");
        }
    }

    @Override
    public Deklaracja generateDeclaration(List<CrsBodyType> crsBodyTypeList){
        Deklaracja declaration = (Deklaracja) jsonReader.read(metaDataInputStream);
        declaration.getCRS().addAll(crsBodyTypeList);
        if(requiredMetadataIsSet(declaration)){
            generateIdsForHeaderAndCRSRaports(declaration);
        }
        return declaration;
    }

    private void generateIdsForHeaderAndCRSRaports(Deklaracja declaration) {
        declaration.getNaglowek().setIdWiadomosci(generateHeaderId(declaration));
        for(CrsBodyType crs : declaration.getCRS()){
            crs.getReportingFI().getDocSpec().setDocRefId(generateDocRefId(crs, declaration));
        }
    }

    private String generateDocRefId(CrsBodyType crs, Deklaracja declaration) {
        String id = headerYearAsString(declaration) + ObjectChecksumUtil.objectToChecksumSHA1(crs);
        return limitString(id, 20);
    }

    private Boolean requiredMetadataIsSet(Deklaracja declaration) {
        return declaration.getNaglowek() != null && declaration.getNaglowek().getRok() != null;
    }

    private String generateHeaderId(Deklaracja declaration) {
        String id = String.format("%s%s", headerYearAsString(declaration), ObjectChecksumUtil.objectToHexSHA1(declaration));
        return limitString(id, 15);
    }

    private String limitString(String string, Integer limit){
        return string.length() > limit ? string.substring(0, limit) : string;

    }

    private String headerYearAsString(Deklaracja declaration) {
        return getYearStringFromDate(declaration.getNaglowek().getRok(), declaration);
    }


    private String getYearStringFromDate(Date rok, Deklaracja declaration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(declaration.getNaglowek().getRok());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }
}
