package to.klay;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Klayton Killough
 * Date: 6/28/2019
 */
public class Main {

    public static int PORT = 3141;
    public static Logger log = LogManager.getLogger();
    public static String cacheURL = "http://google.com";
    private static ChromeDriver driver;
    private static boolean triggered = false;

    public static void main(String... args) throws Exception {

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.TRACE);
//        loggerConfig.setLevel(Level.FATAL);
//        loggerConfig.setLevel(Level.OFF);
        ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.


        CompletableFuture<Boolean> seleniumStopped = new CompletableFuture<>();
        SeleniumThread seleniumThread = new SeleniumThread(seleniumStopped);
        seleniumThread.start();

        PORT = args.length > 0 ? Integer.parseInt(args[0]) : PORT;
        NoticeThread noticeThread = new NoticeThread(seleniumThread, PORT);
        noticeThread.start();
        log.info("Program Started, all threads listening");


        try {
            seleniumStopped.get(); //wait until selenium is stopped to kill the program
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
