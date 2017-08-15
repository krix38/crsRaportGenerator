package pl.krix.generator.impl.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.impl.service.mock.CsvDeserializerMock;
import pl.krix.generator.impl.service.mock.CsvToXmlMapperMock;
import pl.krix.generator.impl.service.mock.HeadeReaderMock;
import pl.krix.generator.impl.service.mock.XmlMarshallerMock;

import java.io.File;
import java.io.IOException;

/**
 * Created by krix on 15.08.2017.
 */
public class RaportGenerationServiceTest {

    private RaportGenerationService raportGenerationService;

    private static final String TEST_CSV_INPUT_FILE_NAME = "test.csv";

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testCorrectRaportGenerationServiceCall() throws IOException {
        File tempFile = temporaryFolder.newFile(TEST_CSV_INPUT_FILE_NAME);
        raportGenerationService = correctRaportGenerationServiceWithMockedDeps();
        raportGenerationService.generate(tempFile.getPath());
    }

    private RaportGenerationService correctRaportGenerationServiceWithMockedDeps() {
        return new RaportGenerationServiceImpl(
                new CsvDeserializerMock(),
                new CsvToXmlMapperMock(),
                new XmlMarshallerMock(),
                new HeadeReaderMock());
    }
}
