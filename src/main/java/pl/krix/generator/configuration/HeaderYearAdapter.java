package pl.krix.generator.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by krix on 15.08.2017.
 */
public class HeaderYearAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY");


    @Override
    public Date unmarshal(String v) throws Exception {
        synchronized (dateFormat){
            return dateFormat.parse(v);
        }
    }

    @Override
    public String marshal(Date v) throws Exception {
        synchronized (dateFormat){
            return dateFormat.format(v);
        }    }
}
