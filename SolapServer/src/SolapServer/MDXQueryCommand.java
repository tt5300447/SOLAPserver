/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import java.util.List;
import java.util.Map;

/**
 *
 * @author tarik
 */
public class MDXQueryCommand implements Command {
    @Override
    public void execute(Request request, ResultHandler resultHandler) {
        // extract query parameters from request
        String query = request.getQueryParam("query");

        // execute query using GeoMondrianConnection
        List<Map<String, Object>> result = GeoMondrianConnection.executeMDXQuery(query);

        // pass query result to result handler
        resultHandler.handle(result);
    }
}

