package com.ansar.application.model.database.config;

import com.ansar.application.model.entity.properties.ConnectionProperties;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionTest {
    @Test
    public void testConnection() {

        ConnectionProperties properties = ConnectionProperties.deserializeFromXml();

        try {
            Connection connection = ConnectionFactory.openConnection(properties);
            connection.close();
            System.out.println("Connection successfully opened and closed");
        } catch (SQLException exception) {
            System.out.println("Wrong connection properties");
            exception.printStackTrace();
        }
    }
}
