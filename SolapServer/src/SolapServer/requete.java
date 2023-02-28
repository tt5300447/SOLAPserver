package SolapServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import mondrian.olap.Connection;
import mondrian.olap.Query;
import mondrian.olap.Result;

public class requete {

    public void requete1() throws SQLException, IOException {

        Connection connection = GeoMondrianConnection.getInstance().getConnection();
        Query query = connection.parseQuery("SELECT\n"
                + "	{[Measures].[Unit Sales],[Measures].[Store Sales]} on columns,\n"
                + "	Filter(\n"
                + "        {[Store].[Store City].members},\n"
                + "                ST_Within([Store].CurrentMember.Properties(\"geom\"),\n"
                + "                [Store].[Store Country].[USA].Properties(\"geom\"))\n"
                + "	) on rows\n"
                + "FROM [Sales]\n"
                + "WHERE [Time].[1997]");

        Result result = connection.execute(query);
        result.print(new PrintWriter(System.out, true));
        connection.close();
    }

    public static void main(String[] args) throws SQLException, IOException {
        new requete().requete1();
        //MyHttpServer server = new MyHttpServer();
        //server.start();
    }
}
