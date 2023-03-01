/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

/**
 *
 * @author tarik
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Request {
    private String method;
    private URI uri;
    private Map<String, String> queryParameters;
    private Map<String, String> headers;
    private String body;

    public Request(HttpExchange exchange) throws IOException {
        method = exchange.getRequestMethod();
        uri = exchange.getRequestURI();
        parseQueryParameters(uri.getRawQuery());
        parseHeaders(exchange.getRequestHeaders());
        parseBody(exchange.getRequestBody());
    }

    private void parseQueryParameters(String query) throws UnsupportedEncodingException {
        if (query == null) {
            queryParameters = new HashMap<>();
            return;
        }

        queryParameters = new HashMap<>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 1) {
                queryParameters.put(keyValue[0], "");
            } else {
                queryParameters.put(keyValue[0], URLDecoder.decode(keyValue[1], Charset.forName("UTF-8").displayName()));
            }
        }
    
    }

    private void parseHeaders(Headers headers) {
        this.headers = new HashMap<>();
        for (String key : headers.keySet()) {
            this.headers.put(key, headers.getFirst(key));
        }
    }

    private void parseBody(InputStream requestBody) throws IOException {
        if (requestBody == null) {
            body = "";
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        body = sb.toString();
    }

    public String getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    String getUrl() {
        return "hello";
    }

    String getQueryParam(String param) {
        return this.queryParameters.get(param);
    }
}




