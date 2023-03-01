/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tarik
 */
public class Router {
    private Map<String, Command> commands;

    public Router() {
        commands = new HashMap<>();
        commands.put("/query", new MDXQueryCommand());
        // more commands here
    }

    public Command route(Request request) {
        String url = request.getUrl();
        String method = request.getMethod();

        // determine appropriate command based on url and method
        // return null if no matching command found
        return commands.get(url + ":" + method);
    }
}

