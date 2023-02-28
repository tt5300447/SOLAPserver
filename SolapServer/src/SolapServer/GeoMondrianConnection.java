package SolapServer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import mondrian.olap.Connection;
import mondrian.olap.DriverManager;

public class GeoMondrianConnection {
    private static GeoMondrianConnection instance = null;
    private Connection connection = null;

    GeoMondrianConnection() throws SQLException, IOException {
        Properties props = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String catalog = props.getProperty("catalogfile");
        String jdbcUrl = props.getProperty("jdbcurl");
        String user = props.getProperty("jdbcuser");
        String password = props.getProperty("jdbcpassword");

        String connectString = "Provider=mondrian;"
                + "Jdbc=" + jdbcUrl +"?user=" + user + "&password=" + password +";"
                + "Catalog=" + catalog + ";"
                + "JdbcDrivers=org.postgresql.Driver";

        System.out.println(connectString);
        connection = DriverManager.getConnection(connectString,null);
    }

    public static GeoMondrianConnection getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new GeoMondrianConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static List<Map<String, Object>> executeMDXQuery(String query) {
        //GeoMondrianConnection.getInstance();
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
