package com.ansar.application.model.entity;

import com.ansar.application.model.entity.properties.ConnectionProperties;
import org.junit.Test;

public class ConnectionPropertiesTest {

    @Test
    public void testMarshal(){
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().
                address("localhost").port("1433").databaseName("1399").userName("sepehr").password("s134s134").build();

        ConnectionProperties.serializeToXml(connectionProperties);
    }

    @Test
    public void testUnMarshal(){
        ConnectionProperties connectionProperties = ConnectionProperties.deserializeFromXml();

        System.out.println(connectionProperties);
    }

}
