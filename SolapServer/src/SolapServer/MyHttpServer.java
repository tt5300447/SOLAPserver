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
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8181), 0);
        Router router = new Router();

        server.createContext("/", new RequestHandler(router));
        server.setExecutor(null);
        server.start();

        System.out.println("Server is listening on port 8181");
    }

    static class RequestHandler implements HttpHandler {
        private final Router router;

        RequestHandler(Router router) {
            this.router = router;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            Request request = new Request(method, path, parseRequestParameters(exchange));

            Response response = new Response();

            exchange.getResponseHeaders().putAll(response.getHeaders());
            exchange.sendResponseHeaders(response.getStatus(), response.getBody().length());

            OutputStream os = exchange.getResponseBody();
            
            os.write(response.getBody().getBytes());
            os.close();
        }

        private Map<String, String> parseRequestParameters(HttpExchange exchange) {
            Map<String, String> parameters = new HashMap<>();

            String query = exchange.getRequestURI().getQuery();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length > 1) {
                        parameters.put(pair[0], pair[1]);
                    }
                }
            }

            return parameters;
        }
    }
}
