package com.ansar.application.model.database.api;


import com.ansar.application.model.entity.properties.ConnectionProperties;
import com.ansar.application.model.entity.properties.DatabaseProperties;
import org.junit.Test;

import java.sql.SQLException;

public class DatabaseApiTest {

    @Test
    public void testGetProduct() throws SQLException {
        ConnectionProperties connectionProperties = ConnectionProperties.deserializeFromXml();
        DatabaseProperties databaseProperties = DatabaseProperties.deserializeFromXml();

        DatabaseApi api = new DatabaseApi(connectionProperties, databaseProperties);
    }

}
