package to.klay;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Klayton Killough
 * Date: 6/28/2019
 */
class NoticeThread extends Thread {
    public static final int MAXIMUM_POOL_SIZE = 20;
    public static final int KEEP_ALIVE_TIME = 5; //how long to keep extra threads alive (in minutes)
    public static final int MIN_POOL_SIZE = 1;
    public static final int SERVER_ERROR_LIMIT = 10;
    public static Logger log = LogManager.getLogger();
    Gson gson = new Gson();
    Server server;


    private ServerSocket serverSocket;
    private SeleniumThread seleniumThread;
    boolean triggered;
    ExecutorService executorService;

    NoticeThread(SeleniumThread seleniumThread, int port) {
        this.seleniumThread = seleniumThread;


        executorService = new ThreadPoolExecutor(
                MIN_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(MAXIMUM_POOL_SIZE));

        //start listening socket
        server = new Server(port);
        server.setHandler(new PostHandler());
//        serverSocket = new ServerSocket(port);
        log.info("Started listening on port " + port);


    }

    @Override
    public void run() {
        super.run();
        int serverSocketErrorCount = 0;
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("SERVER SOCKET ERROR", e);
            if (++serverSocketErrorCount == SERVER_ERROR_LIMIT) {
                log.fatal("Server Socket errored out too many times");
                System.exit(1);
            }
        }


    }

    private class PostHandler extends AbstractHandler {
        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            String content = "";
            try {
                if (request.getMethod().equalsIgnoreCase("POST")) {
                    content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    SNSJSon SNSJSon = gson.fromJson(content, SNSJSon.class);
                    String message = gson.fromJson(SNSJSon.getMessage(), PinpointJson.class).getMessageBody();

                    seleniumThread.clickLink(decipher(message));
                    triggered = true;
                    log.info(message);
                } else {
                    content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                    log.info(content);
                }


            } catch (Exception ex) {
                log.error("ERROR GETTING MESSAGE, content: " + content, ex);
            }

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            request.setHandled(true);
        }

        public String decipher(String s) {
            int start = s.indexOf("http");
            int end = s.indexOf(" ", start); //start search from start
            return s.substring(start, end).trim();
        }

    }
}
