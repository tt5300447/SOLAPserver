/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import com.sun.net.httpserver.HttpExchange;

/**
 *
 * @author tarik
 */
public class Invoker {
    public void execute(Command command, Request request, Response response, HttpExchange exchange) {
        command.execute(request, response, exchange);
    }
}

