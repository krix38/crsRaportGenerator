package pl.krix.generator;

import org.junit.Before;
import org.junit.Test;
import pl.krix.generator.impl.service.mock.RaportGenerationServiceMock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.SQLSyntaxErrorException;

import static org.junit.Assert.assertEquals;

/**
 * Created by krix on 16.08.2017.
 */
public class MainClassTest {

    private final ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
    private final ByteArrayOutputStream standardOutput = new ByteArrayOutputStream();


    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        mockRaportGenerationService();
        System.setErr(new PrintStream(errorOutput));
        System.setOut(new PrintStream(standardOutput));
    }

    @Test
    public void testMainClassCorrectArguments() {
        String[] args = {"test.csv"};
        Main.main(args);
        assertEquals("", standardOutput.toString());
    }

    @Test
    public void testMainClassNoArguments() {
        String[] args = {};
        Main.main(args);
        assertEquals("usage: java -jar crsRaport.jar [csv input file]\n\n", standardOutput.toString());
    }

    private void mockRaportGenerationService() throws NoSuchFieldException, IllegalAccessException {
        Field serviceField = Main.class.getDeclaredField("raportGenerationService");
        serviceField.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(serviceField, serviceField.getModifiers() & ~Modifier.FINAL);
        serviceField.set(null, new RaportGenerationServiceMock());
    }
}
