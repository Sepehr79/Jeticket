package com.ansar.application.model.database.config;

import com.ansar.application.model.entity.ConnectionProperties;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectionFactory {

    // Logger for see results and debugging
    private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    private final static BasicDataSource basicDataSource = new BasicDataSource();

    // Connection instance
    private static Connection connection;

    /**
     * Constructor
     */
    private ConnectionFactory() {

    }



    /**
     * Open connection to the sql serer database
     * @return opened connection
     */
    public void openConnection() throws SQLServerException {
        ConnectionProperties connectionProperties = ConnectionProperties.deserializeFromXml();

        // Create suitable input string
        String connectionString =  "jdbc:sqlserver://" + connectionProperties.getAddress().trim() + ":"
                + connectionProperties.getPort().trim() + "; databaseName=" + connectionProperties.getDatabaseName().trim() + ";";

        logger.info("Connection string: " + connectionString);

        try {
            // Open connection
            connection = DriverManager.getConnection(connectionString, connectionProperties.getUserName().trim(), connectionProperties.getPassword().trim());
            logger.info("Connection opened!");
        } catch (SQLServerException exception){

            // When failed to open connection
            logger.info("Connection not successful !");
            throw exception;

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            connection().close();
            logger.info("Connection closed!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (NullPointerException exception){
            logger.info("Cant close opened connection !");
        }
    }

    public static ConnectionFactory getInstance(){
        return new ConnectionFactory();
    }

    /**
     * @return connection it may be open or close
     */
    public Connection connection(){
        return connection;
    }
}
