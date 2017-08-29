package pl.krix.generator.impl.service.builder;

import pl.krix.generator.api.service.builder.DeclarationFactory;
import pl.krix.generator.api.service.marshaller.XmlMarshaller;
import pl.krix.generator.domain.xml.CrsBodyType;
import pl.krix.generator.domain.xml.Deklaracja;
import pl.krix.generator.domain.xml.ObjectFactory;
import pl.krix.generator.exception.JsonFileNotFoundException;
import pl.krix.generator.impl.service.marshaller.XmlMarshallerImpl;
import pl.krix.generator.util.ObjectChecksumUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by krix on 15.08.2017.
 */
public class DeclarationFactoryImpl implements DeclarationFactory {

    ObjectFactory objectFactory = new ObjectFactory();

    private XmlMarshaller xmlMarshaller;

    private InputStream metaDataInputStream ;

    private static final String DEFAULT_METADATA_INPUT_PATH = "metadata.xml";

    public DeclarationFactoryImpl(XmlMarshaller xmlMarshaller, InputStream inputStream) {
        this.xmlMarshaller = xmlMarshaller;
        this.metaDataInputStream = inputStream;
    }

    @SuppressWarnings("unchecked")
    public DeclarationFactoryImpl() {
        this(new XmlMarshallerImpl(Deklaracja.class), null);
    }

    @Override
    public Deklaracja generateDeclaration(List<CrsBodyType> crsBodyTypeList){
        initializeMetadataInputStream();
        Deklaracja declaration = (Deklaracja) xmlMarshaller.unmarshallFromXml(metaDataInputStream);
        declaration.getCRS().addAll(crsBodyTypeList);
        if(requiredMetadataIsSet(declaration)){
            generateIdsForHeaderAndCRSRaports(declaration);
        }
        return declaration;
    }

    private void initializeMetadataInputStream() {
        if(metaDataInputStream == null){
            try {
                this.metaDataInputStream = new FileInputStream(new File(DEFAULT_METADATA_INPUT_PATH));
            } catch (FileNotFoundException e) {
                throw new JsonFileNotFoundException("Metadata json file not found");
            }
        }
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
