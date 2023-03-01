package SolapServer;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.OlapWrapper;
import java.sql.SQLException;
import mondrian.xmla.*;
import org.olap4j.CellSetAxis;
import org.olap4j.Position;
import org.olap4j.metadata.Member;
import org.olap4j.metadata.*;

public class requete {

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
        
        // Print the axes
        for (int i = 0; i < cellSet.getAxes().size(); i++) {
            CellSetAxis axis = cellSet.getAxes().get(i);
            System.out.println("Axis #" + i + ":");
            for (Position position : axis.getPositions()) {
                for(Member member: position.getMembers())
                    System.out.print(member.getUniqueName()+ "\t");
                System.out.println();
            }
        }

        // Print the rows
        for (int i = 0; i < cellSet.getAxes().get(1).getPositions().size(); i++) {
            System.out.print("Row #" + i + ": ");
            for (int j = 0; j < cellSet.getAxes().get(0).getPositions().size(); j++) {
                Position[] cellPos = new Position[2];
                cellPos[0] = cellSet.getAxes().get(0).getPositions().get(j);
                cellPos[1] = cellSet.getAxes().get(1).getPositions().get(i);
                System.out.print(cellSet.getCell(cellPos).getFormattedValue() + "\t");
            }
            System.out.println();
        }
        
        
        //PrintWriter writer = new PrintWriter(System.out);
        //writer.flush();
        connection.close();
    }
}
