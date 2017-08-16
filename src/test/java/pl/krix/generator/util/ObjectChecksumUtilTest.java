package pl.krix.generator.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by krix on 16.08.2017.
 */
public class ObjectChecksumUtilTest {

    private String test = "test";

    @Test
    public void hexSHA1GenerationTest(){
        String result = ObjectChecksumUtil.objectToHexSHA1(test);
        assertEquals("9C4BE61C36C32C9F64DCEF46713820D3B33A63AB", result);
    }

    @Test
    public void checksumSHA1GenerationTest(){
        String result = ObjectChecksumUtil.objectToChecksumSHA1(test);
        assertEquals("892295161564871055873307067851675065023851029419", result);
    }
}
