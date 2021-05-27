package com.ansar.application.model.entity;

import javax.xml.bind.JAXB;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ConnectionProperties {

    private static final Logger logger = Logger.getLogger(ConnectionProperties.class.getName());

    private String address;
    private String port;
    private String databaseName;
    private String userName;
    private String password;

    public static class Builder{

        private String address;
        private String port;
        private String databaseName;
        private String userName;
        private String password;

        public Builder(){

        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Builder port(String port){
            this.port = port;
            return this;
        }
        public Builder databaseName(String databaseName){
            this.databaseName = databaseName;
            return this;
        }
        public Builder userName(String userName){
            this.userName = userName;
            return this;
        }
        public Builder password(String password){
            this.password = password;
            return this;
        }

        public String getAddress() {
            return address;
        }

        public String getPort() {
            return port;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }

        public ConnectionProperties build(){
            return new ConnectionProperties(this);
        }
    }


    private ConnectionProperties(Builder builder) {
        this.address = builder.getAddress();
        this.port = builder.getPort();
        this.databaseName = builder.getDatabaseName();
        this.userName = builder.getUserName();
        this.password = builder.getPassword();
    }

    public ConnectionProperties() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void serializeToXml(ConnectionProperties properties){

        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("connection.xml"))) {
            JAXB.marshal(properties, writer);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static ConnectionProperties deserializeFromXml(){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("connection.xml"))){
            return JAXB.unmarshal(reader, ConnectionProperties.class);
        }catch (NoSuchFileException exception){
          logger.info("Connection file not found");
        } catch (IOException exception){
            exception.printStackTrace();
        }
        return new ConnectionProperties.Builder().address("").port("").databaseName("").userName("").password("").
                build();
    }


    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "address='" + address + '\'' +
                ", port='" + port + '\'' +
                ", databaseName='" + databaseName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
