/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

/**
 *
 * @author tarik
 */
public class Invoker {
    public void execute(Command command, Request request, ResultHandler resultHandler) {
        command.execute(request, resultHandler);
    }
}

