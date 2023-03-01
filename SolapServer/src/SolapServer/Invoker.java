/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import com.sun.net.httpserver.HttpExchange;
import java.util.logging.Logger;

/**
 *
 * @author tarik
 */
public class Invoker {
    private static final Logger LOGGER = Logger.getLogger(Invoker.class.getName());

    public void execute(Command command, Request request, Response response, HttpExchange exchange) {
        LOGGER.info("i am here just before executing invoker.execute(new MDXQueryCommand(), request, response);");

        command.execute(request, response, exchange);
    }
}

