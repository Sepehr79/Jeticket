package com.ansar.application.model.entity;

import com.ansar.application.model.entity.properties.DatabaseProperties;
import org.junit.Test;

public class DatabasePropertiesTest {

    @Test
    public void testSerialize(){
        DatabaseProperties databaseProperties = new DatabaseProperties("110");

        DatabaseProperties.serializeToXml(databaseProperties);
    }

    @Test
    public void testUnmarshal(){
        DatabaseProperties properties = DatabaseProperties.deserializeFromXml();

        System.out.println(properties);
    }

}
