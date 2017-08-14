package pl.krix.generator;

import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.impl.service.RaportGenerationServiceImpl;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

/**
 * Created by krix on 04.08.2017.
 */
public class Main {

    private static RaportGenerationService raportGenerationService;


    //TODO: handle jaxbexception or all exceptions on lower level
    public static void main(String[] args){
        try {
            raportGenerationService = new RaportGenerationServiceImpl();
            raportGenerationService.generate(args[0]);
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
