/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

/**
 *
 * @author tarik
 */
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.logging.Logger;

public class RequestHandler {
    private static final Logger LOGGER = Logger.getLogger(RequestHandler.class.getName());
    
    private final Invoker invoker;
    private Response response;
    private Request request;

    public RequestHandler(Request request, Response response) {
        this.invoker = new Invoker();
        this.response = response;
        this.request = request;
    }
    
    public void handle(HttpExchange exchange) throws IOException {
        LOGGER.info("Entered RequestHandler.handle() Method");
        String path = request.getUri().getPath();
        LOGGER.info("String path = " + path + " /*-*/ " + request.getQueryParam("mdx"));
        switch(path) {
            case "/query":
                LOGGER.info("i am here just before executing invoker.execute(new MDXQueryCommand(), request, response);");
                invoker.execute(new MDXQueryCommand(), request, response,exchange);
                break;
            // add more cases for other commands here
            default:
                //exchange.sendResponseHeaders(404, 0);
                //exchange.close();
                LOGGER.info("i am here just before executing hello world");
                String response_ = "This is the default http response";
                exchange.sendResponseHeaders(200, response_.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response_.getBytes());
                os.close();
        }
    }
}

