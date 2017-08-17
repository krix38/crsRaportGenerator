package pl.krix.generator.impl.service.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.krix.generator.api.service.reader.JsonReader;
import pl.krix.generator.domain.xml.TNaglowek;
import pl.krix.generator.exception.JsonReaderException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by krix on 12.08.2017.
 */
public class JsonReaderServiceImpl<T> implements JsonReader {

    private Class<T> typeParameter;


    private ObjectMapper mapper = new ObjectMapper();

    public JsonReaderServiceImpl(Class<T> typeParameter) {
        this.typeParameter = typeParameter;
        setDateFormatForMapper();
    }

    private void setDateFormatForMapper() {
        DateFormat dateFormat = new SimpleDateFormat("YYYY");
        mapper.setDateFormat(dateFormat);
    }

    @Override
    public T read(InputStream inputStream) throws JsonReaderException {
        checkinput(inputStream);
        return readInputStreamAndGetHeader(inputStream);
    }

    private T readInputStreamAndGetHeader(InputStream inputStream) throws JsonReaderException {
        try {
            return mapper.readValue(inputStream, typeParameter);
        } catch (IOException exception) {
            throw new JsonReaderException("Exception occured during input reading", exception);
        }
    }

    private void checkinput(InputStream inputStream) throws JsonReaderException {
        if(inputStream == null){
            throw new JsonReaderException("Invalid input: input stream cant be null");
        }
    }
}
