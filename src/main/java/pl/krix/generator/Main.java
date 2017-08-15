package pl.krix.generator;

import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.exception.RaportGenerationException;
import pl.krix.generator.impl.service.RaportGenerationServiceImpl;

/**
 * Created by krix on 04.08.2017.
 */
public class Main {

    private static RaportGenerationService raportGenerationService;

    //TODO: print usage if wrong args
    public static void main(String[] args){
        try {
            raportGenerationService = new RaportGenerationServiceImpl();
            raportGenerationService.generate(args[0]);
        } catch (RaportGenerationException e) {
            e.printStackTrace();
        }
    }
}
