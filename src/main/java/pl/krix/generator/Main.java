package pl.krix.generator;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.krix.generator.api.service.RaportGenerationService;
import pl.krix.generator.exception.BadUsageException;
import pl.krix.generator.exception.RaportGenerationException;
import pl.krix.generator.impl.service.RaportGenerationServiceImpl;


/**
 * Created by krix on 04.08.2017.
 */
public class Main {

    private static RaportGenerationService raportGenerationService;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args){
        try {
            checkUsage(args);
            raportGenerationService = new RaportGenerationServiceImpl();
            raportGenerationService.generate(args[0]);
        } catch (RaportGenerationException e) {
            logger.error(e.getMessage());
        }
    }

    private static void checkUsage(String[] args) {
        if(args.length != 1){
            printUsage();
            throw new BadUsageException("Invalid number of application parameters");
        }
    }

    private static void printUsage(){
        System.out.print("usage: java -jar crsRaport.jar [csv input file]\n\n");
    }
}
