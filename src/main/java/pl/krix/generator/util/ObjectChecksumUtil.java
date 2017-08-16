package pl.krix.generator.util;

import pl.krix.generator.exception.DeclarationHeaderUniqueIdGenerationException;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by krix on 16.08.2017.
 */
public class ObjectChecksumUtil {

    public static String objectToHexSHA1(Object object){
        return DatatypeConverter.printHexBinary(calculateDigestSHA1(object));
    }

    public static String objectToChecksumSHA1(Object object){
        return String.valueOf(new BigInteger(1, calculateDigestSHA1(object)));
    }

    private static byte[] calculateDigestSHA1(Object object){
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return MessageDigest.getInstance("SHA1").digest(byteArrayOutputStream.toByteArray());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new DeclarationHeaderUniqueIdGenerationException("Failed to generate unique id for declaration header", e);
        }
    }

}
