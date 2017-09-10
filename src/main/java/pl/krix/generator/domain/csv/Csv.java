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
    private String accountNumber;

    @CsvField(pos = 3)
    private String accountHolderName;

    @CsvField(pos = 4)
    private String accountHolderLastName;

    @CsvField(pos = 5)
    private String accountHolderBirthCountry;

    @CsvField(pos = 6)
    private String accountHolderLegalAddressType;

    @CsvField(pos = 7)
    private String accountHolderCountryCode;

    @CsvField(pos = 8)
    private String accountHolderAddressFree;

    @CsvField(pos = 9)
    private String accountHolderResCountryCode;

    @CsvField(pos = 10)
    private String controllingPersonResCountryCode;

    @CsvField(pos = 11)
    private String controllingPersonTinCountryCode;

    @CsvField(pos = 12)
    private String controllingPersonTinIssuedByCountryCode;

    @CsvField(pos = 13)
    private String controllingPersonName;

    @CsvField(pos = 14)
    private String controllingPersonLastName;

    @CsvField(pos = 15)
    private String controllingPersonLegalAddressType;

    @CsvField(pos = 16)
    private String controllingPersonCountryCode;

    @CsvField(pos = 17)
    private String controllingPersonAddressFree;

    @CsvField(pos = 18)
    private String accountBalance;

    @CsvField(pos = 19)
    private String accountBalanceCurrCode;











}
