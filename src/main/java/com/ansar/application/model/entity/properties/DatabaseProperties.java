package com.ansar.application.model.entity.properties;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class DatabaseProperties {
    private static final Logger logger = Logger.getLogger(DatabaseProperties.class.getName());

    private String anbarName;

    public DatabaseProperties(String databaseName) {
        this.anbarName = databaseName;
    }

    public DatabaseProperties() {
    }

    public String getAnbarName() {
        return anbarName;
    }

    public void setAnbarName(String databaseName) {
        this.anbarName = databaseName;
    }

    public static void serializeToXml(DatabaseProperties properties){
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("database.xml"))) {
            JAXB.marshal(properties, writer);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static DatabaseProperties deserializeFromXml(){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("database.xml"))){
            return JAXB.unmarshal(reader, DatabaseProperties.class);
        }catch (NoSuchFileException exception){
            logger.info("Database properties not found !");
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return new DatabaseProperties("");
    }

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "anbarName='" + anbarName + '\'' +
                '}';
    }
}
