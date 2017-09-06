package pl.krix.generator.domain.csv;

import lombok.Getter;
import lombok.Setter;
import net.sf.jsefa.csv.annotation.CsvDataType;
import net.sf.jsefa.csv.annotation.CsvField;

/**
 * Created by krix on 04.08.2017.
 */

@Getter
@Setter
@CsvDataType
public class Csv {

    @CsvField(pos = 1)
    private String docTypeIndic;

    @CsvField(pos = 2)
    private String accountNumberType;

    @CsvField(pos = 3)
    private String accountHolderName;

    @CsvField(pos = 4)
    private String accountHolderLastname;

    @CsvField(pos = 5)
    private String accountHolderCountry;

    @CsvField(pos = 6)
    private String accountHolderLegalAddressType;

}
