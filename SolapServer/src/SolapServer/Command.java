/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SolapServer;

import com.sun.net.httpserver.HttpExchange;

/**
 *
 * @author tarik
 */
public interface Command {
    public void execute(Request request, Response response, HttpExchange exchange);
}

