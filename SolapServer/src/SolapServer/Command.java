/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package SolapServer;

/**
 *
 * @author tarik
 */
public interface Command {
    public void execute(Request request, ResultHandler resultHandler);
}

