package to.klay;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class SmsSenderTest {
    public static Logger log = LogManager.getLogger();

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID;
    public static final String AUTH_TOKEN;
    public static final String CALLER_NUMBER;
    public static final String CALLING_NUMBER;
    public static final String DEFAULT_VALUE = "X";
    public static final String TWILLO_PROPERTIES = "twillo.properties";


    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(SmsSenderTest.class.getResourceAsStream(TWILLO_PROPERTIES));
        } catch (IOException e) {
            log.error("Couldnt get twillo properties (missing file?)", e);
        }
        ACCOUNT_SID = properties.getProperty("ACCOUTN_SID", DEFAULT_VALUE);
        AUTH_TOKEN = properties.getProperty("AUTH_TOKEN", DEFAULT_VALUE);
        CALLER_NUMBER = properties.getProperty("CALLER_NUMBER", DEFAULT_VALUE);
        CALLING_NUMBER = properties.getProperty("CALLING_NUMBER", DEFAULT_VALUE);
    }


    public static void main(String... args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String s = "Test message";
        Message message = Message
                .creator(new PhoneNumber(CALLER_NUMBER), // to
                        new PhoneNumber(CALLING_NUMBER), // from
                        s)
                .create();
        log.info("MESSAGE SENT");
        log.info("Twillo message id : " + message.getSid());
    }
}
