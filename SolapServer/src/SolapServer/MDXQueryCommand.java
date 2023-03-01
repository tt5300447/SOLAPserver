/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.olap4j.CellSet;

/**
 *
 * @author tarik
 */
public class MDXQueryCommand implements Command {
    @Override
    public void execute(Request request, Response response, HttpExchange exchange) {
        // extract query parameters from request
        String query = request.getQueryParam("mdx");

        try {
            // execute query using GeoMondrianConnection
            CellSet result = GeoMondrianConnection.executeMDXQuery(query);
            ResultHandler.handle(result,exchange);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(MDXQueryCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

