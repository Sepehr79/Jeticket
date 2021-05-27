package com.ansar.application.model.database.config;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.Test;

public class ConnectionTest {
    @Test
    public void testConnection(){

        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();

        try {
            connectionFactory.openConnection();
        } catch (SQLServerException exception) {
            exception.printStackTrace();
        }

        connectionFactory.closeConnection();

    }
}
