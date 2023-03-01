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
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class Response {
    private HttpExchange exchange;
    private int status;
    private Map<String, String> headers;

    public Response(HttpExchange exchange) {
        this.exchange = exchange;
        this.status = 200; // default status is OK
        this.headers = new HashMap<String, String>();
    }

    // setters and getters for status and headers
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    // methods to manipulate response headers
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public void setContentType(String contentType) {
        headers.put("Content-Type", contentType);
    }

    // methods to write response body
    public void write(byte[] response) throws IOException {
        exchange.sendResponseHeaders(status, response.length);
        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    public void write(String response) throws IOException {
        write(response.getBytes());
    }

    public void writeHTML(String html) throws IOException {
        setContentType("text/html");
        write(html.getBytes());
    }
}
