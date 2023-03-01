package SolapServer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.olap4j.CellSet;
import org.olap4j.OlapConnection;
import org.olap4j.OlapStatement;
import org.olap4j.OlapWrapper;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class GeoMondrianConnection {
    private static final Logger LOGGER = Logger.getLogger(GeoMondrianConnection.class.getName());

    private static GeoMondrianConnection instance = null;
    private static Connection connection = null;

    GeoMondrianConnection() throws SQLException, IOException, ClassNotFoundException {
        Properties props = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Class.forName("mondrian.olap4j.MondrianOlap4jDriver");
        
        String catalogFilePath = props.getProperty("catalogfile");
        String jdbcUrl = props.getProperty("jdbcurl");
        String user = props.getProperty("jdbcuser");
        String password = props.getProperty("jdbcpassword");
        
        String connectUrl = "jdbc:mondrian:"
                + "Jdbc="+ jdbcUrl +"?user="+user+"&password="+ password + ";"
                + "JdbcDrivers=org.postgresql.Driver;"
                + "Catalog=file:" + catalogFilePath + ";";
                
        connection = DriverManager.getConnection(connectUrl);
        System.out.println(connectUrl);
    }

    public static GeoMondrianConnection getInstance() throws SQLException, IOException, ClassNotFoundException {
        if (instance == null) {
            instance = new GeoMondrianConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public static CellSet executeMDXQuery(String query) throws SQLException, IOException, ClassNotFoundException {
        LOGGER.info("HERE WE ARE IN THE public static CellSet executeMDXQuery(String query) METHOD OF GeoMondrianConnection CLASS");
        if (instance == null) {
            instance = new GeoMondrianConnection();
        }
        LOGGER.info("we have the instance");
        OlapWrapper wrapper = (OlapWrapper) connection;
        LOGGER.info("we wrapped the connection");
        OlapConnection olapConnection = wrapper.unwrap(OlapConnection.class);
        LOGGER.info("we did this OlapConnection olapConnection = wrapper.unwrap(OlapConnection.class)");
        OlapStatement statement = olapConnection.createStatement();
        LOGGER.info("we created a statement");
        CellSet cellSet = statement.executeOlapQuery(query);
        LOGGER.info("HERE WE FINISHED EXECUTING THE QUERY FROM THE GeoMondrianConnection CLASS");
        
        return cellSet;
    }
}
