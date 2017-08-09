package pl.krix.generator.impl.service.deserializer;

import net.sf.jsefa.Deserializer;
import net.sf.jsefa.csv.CsvIOFactory;
import pl.krix.generator.api.service.deserializer.CsvDeserializerService;
import pl.krix.generator.domain.csv.Csv;
import pl.krix.generator.exception.InvalidDeserializerInputException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krix on 09.08.2017.
 */
public class CsvDeserializerServiceImpl implements CsvDeserializerService {

    private Deserializer deserializer = CsvIOFactory.createFactory(Csv.class).createDeserializer();

    @Override
    public Csv deserializeToCsv(String csvInput) throws InvalidDeserializerInputException {
        checkInput(csvInput);
        deserializer.open(new StringReader(csvInput));
        return deserializer.next();
    }

    @Override
    public List<Csv> deserializeToCsvList(String csvInput) throws InvalidDeserializerInputException {
        checkInput(csvInput);
        ArrayList<Csv> resultList = new ArrayList<>();
        deserializer.open(new StringReader((csvInput)));
        while (deserializer.hasNext()){
            resultList.add(deserializer.next());
        }
        return resultList;
    }

    private void checkInput(String csvInput) throws InvalidDeserializerInputException {
        if(csvInput == null){
            throw new InvalidDeserializerInputException("Deserializer input cant be null");
        }
    }


}
