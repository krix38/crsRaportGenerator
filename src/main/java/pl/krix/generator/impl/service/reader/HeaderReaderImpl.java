package pl.krix.generator.impl.service.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.krix.generator.api.service.reader.HeaderReader;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.HeaderReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by krix on 12.08.2017.
 */
public class HeaderReaderImpl implements HeaderReader {

    private ObjectMapper mapper = new ObjectMapper();

    public HeaderReaderImpl() {
        setDateFormatForMapper();
    }

    private void setDateFormatForMapper() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY");
        mapper.setDateFormat(dateFormat);
    }

    @Override
    public TNaglowek readHeder(InputStream inputStream) throws HeaderReaderException {
        checkinput(inputStream);
        return readInputStreamAndGetHeader(inputStream);
    }

    private TNaglowek readInputStreamAndGetHeader(InputStream inputStream) throws HeaderReaderException {
        try {
            return mapper.readValue(inputStream, TNaglowek.class);
        } catch (IOException exception) {
            throw new HeaderReaderException("Exception occured during input reading", exception);
        }
    }

    private void checkinput(InputStream inputStream) throws HeaderReaderException {
        if(inputStream == null){
            throw new HeaderReaderException("Invalid input: input stream cant be null");
        }
    }
}
