/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

/**
 *
 * @author tarik
 */


import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyHttpServer {
    private static final Logger LOGGER = Logger.getLogger(MyHttpServer.class.getName());
    
    public static void main(String[] args) throws Exception {
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.ALL);
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        LOGGER.info("Server started on port 8000");
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            LOGGER.info("request received");
            Request request = new Request(t);
            LOGGER.info("Request created");
            Response response = new Response(t);
            LOGGER.info("Response created");
            RequestHandler handler = new RequestHandler(request, response);
            LOGGER.info("RequestHandler ");
            handler.handle(t);
        }
    }
}
