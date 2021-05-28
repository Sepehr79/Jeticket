package com.ansar.application.model.database.api;


import com.ansar.application.model.entity.properties.ConnectionProperties;
import org.junit.Test;

import java.sql.SQLException;

public class DatabaseApiTest {

    @Test
    public void testGetProduct() throws SQLException {
        ConnectionProperties connectionProperties = ConnectionProperties.deserializeFromXml();

        DatabaseApi api = new DatabaseApi(connectionProperties);

    }

}
