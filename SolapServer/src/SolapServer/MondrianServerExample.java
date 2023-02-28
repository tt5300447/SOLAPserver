/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SolapServer;

import java.sql.Connection;
import java.sql.DriverManager;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.OlapWrapper;
import java.sql.SQLException;
import org.olap4j.Position;

public class MondrianServerExample {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("mondrian.olap4j.MondrianOlap4jDriver");

        String catalogFilePath = "simple_geofoodmart.xml";
        String connectUrl = "jdbc:mondrian:"
                + "Jdbc=jdbc:postgresql://localhost:5432/simple_geofoodmart?user=postgres&password=root;"
                + "JdbcDrivers=org.postgresql.Driver;"
                + "Catalog=file:" + catalogFilePath + ";";

        Connection connection = DriverManager.getConnection(connectUrl);
        OlapWrapper wrapper = (OlapWrapper) connection;
        OlapConnection olapConnection = wrapper.unwrap(OlapConnection.class);
        OlapStatement statement = olapConnection.createStatement();

        String qs = "SELECT\n"
                + "	{[Measures].[Unit Sales],[Measures].[Store Sales]} on columns,\n"
                + "	Filter(\n"
                + "        {[Store].[Store City].members},\n"
                + "                ST_Within([Store].CurrentMember.Properties(\"geom\"),\n"
                + "                [Store].[Store Country].[USA].Properties(\"geom\"))\n"
                + "	) on rows\n"
                + "FROM [Sales]\n"
                + "WHERE [Time].[1997]";

        CellSet cellSet = statement.executeOlapQuery(qs);

        int rowCount = cellSet.getAxes().get(1).getPositions().size();
        int colCount = cellSet.getAxes().get(0).getPositions().size();

        for (int row = 0; row < cellSet.getAxes().get(1).getPositionCount(); row++) {
            // loop through the columns
            for (int col = 0; col < cellSet.getAxes().get(0).getPositionCount(); col++) {
                // get the cell value for the current row and column
                Position[] pos = {cellSet.getAxes().get(0).getPositions().get(col),
                    cellSet.getAxes().get(1).getPositions().get(row)};
                System.out.print(cellSet.getCell(pos).getValue() + "\t");
            }
            System.out.println();
        }

        connection.close();
    }
}
