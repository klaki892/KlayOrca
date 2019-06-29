package to.klay;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

/**
 * @author Klayton Killough
 * Date: 6/28/2019
 */
public class SeleniumThread extends Thread {
    public static WebDriver driver; //driver that shows the website
    private final ChromeOptions chromeOptions;
    public static Logger log = LogManager.getLogger();
    private CompletableFuture<Boolean> triggered = new CompletableFuture<>();
    private CompletableFuture<Boolean> seleniumStopped;

    public SeleniumThread(CompletableFuture<Boolean> seleniumStopped) {
        super();
        this.seleniumStopped = seleniumStopped;
        //setup the driver for us to use.
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        chromeOptions = new ChromeOptions()
                .merge(capabilities);
    }

    @Override
    public void run() {

        //start selenium
        driver = new ChromeDriver(chromeOptions);
//        driver = new FirefoxDriver();
        driver.get(Main.cacheURL);
        log.info("Started Selenium");

        while (true) { //runs until the program itself is killed
            if (driverDied()) { //driver stopped for some reason, bring it back up.
                if (triggered.isDone()) //we meant to kill the program
                    break;
                //restart if it's not time for the server to die.
                log.warn("SELENIUM DRIVER RESTARTING.");
                driver = new ChromeDriver(chromeOptions);
                driver.get(Main.cacheURL);
            }
        } //end of while

        log.warn("Ending program");
        seleniumStopped.complete(true);
    }

    private boolean driverDied() {
        try {
            driver.getWindowHandles();
            return false;
        } catch (WebDriverException wde) {
            log.error("SELENIUM DIED", wde);
            return true;
        }
    }

    public void clickLink(String link) {
        driver.get(link);
        triggered.complete(true);
        log.info("SELENIUM LINK ACTIVATED: " + link);
        Executors.newSingleThreadExecutor().execute(SmsSender::send);
    }


}
