/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.Position;
import org.olap4j.metadata.Member;

/**
 *
 * @author tarik
 */
public class ResultHandler {
    private static String response;
    public static void handle(CellSet result, HttpExchange exchange) throws IOException {
        response = "";
        
        writeTheAxis(result);
        writeTheRows(result);
   
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
    private static void writeTheAxis(CellSet result){
        // Print the axes
        for (int i = 0; i < result.getAxes().size(); i++) {
            CellSetAxis axis = result.getAxes().get(i);
            response = response + "Axis #" + i + ":\n";
            //System.out.println("Axis #" + i + ":");
            for (Position position : axis.getPositions()) {
                for(Member member: position.getMembers()){
                    response = response + member.getUniqueName() + "\t";
                    //System.out.print(member.getUniqueName());
                }
                response = response + "\n";
                //System.out.println();
            }
        }
    }
    private static void writeTheRows(CellSet result){
        // Print the rows
        for (int i = 0; i < result.getAxes().get(1).getPositions().size(); i++) {
            response = response + "Row #" + i + ": ";
            //System.out.print("Row #" + i + ": ");
            for (int j = 0; j < result.getAxes().get(0).getPositions().size(); j++) {
                Position[] cellPos = new Position[2];
                cellPos[0] = result.getAxes().get(0).getPositions().get(j);
                cellPos[1] = result.getAxes().get(1).getPositions().get(i);
                response = response + result.getCell(cellPos).getFormattedValue() + "\t";
                //System.out.print(result.getCell(cellPos).getFormattedValue() + "\t");
            }
            response = response + "\n";
            //System.out.println();
        }
    }
}

