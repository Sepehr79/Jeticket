package com.ansar.application.model.database.api;

import com.ansar.application.model.database.config.ConnectionFactory;
import com.ansar.application.model.entity.properties.ConnectionProperties;
import com.ansar.application.model.entity.beans.*;
import com.ansar.application.model.entity.properties.DatabaseProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseApi {

    // Logger for debugging and testing
    private static final Logger logger = Logger.getLogger(DatabaseApi.class.getName());

    private Connection connection;

    private PreparedStatement idSelector;

    public DatabaseApi() {


    }

    public void openConnection(ConnectionProperties connectionProperties, DatabaseProperties databaseProperties) throws SQLException {
        connection = ConnectionFactory.openConnection(connectionProperties);
        logger.info("Connection opened!");

        // Create Query with specific values
        idSelector = connection.prepareStatement("select ID1, K_Code_B, A_Code, Name1, Price_Consumer, Price_Forosh,Barcode, K_Qty1 from(\n" +
                "select kalaid.K_Code as ID1 ,A_Code, Name1, Barcode, Price_Consumer, price_forosh from kalaid join Anbar on KalaId.K_Code = Anbar.K_Code\n" +
                ") P\n" +
                "join\n" +
                "(select k_Code, '0' as K_Code_B, 1 as K_Qty1 from KalaId left join (select K_Code as ID ,'0' as k_code_B, 1 as K_Qty1 from TblBasket_Kala) P on K_Code = P.ID\n" +
                "union all\n" +
                "select K_Code as ID2,  K_Code_B, K_Qty1 from TblBasket_Kala) T\n" +
                "on P.ID1 = T.K_Code where (ID1 = ? or K_Code_B = ? or Barcode = ?) and A_Code = ?;");

        idSelector.setString(4, databaseProperties.getAnbarName().trim());
    }

    public void closeConnection() throws SQLException {
        idSelector.close();
        connection.close();
        logger.info("Connection closed");
    }

    public Product getProductById(String id) throws SQLException {

        idSelector.setString(1, id.trim());
        idSelector.setString(2, id.trim());
        idSelector.setString(3, id.trim());


        // Read data from executed query
        ResultSet resultSet = idSelector.executeQuery();

        if (resultSet.next()){
            String name = resultSet.getString("name1");
            String highPrice = resultSet.getString("price_forosh");
            String lowPrice = resultSet.getString("Price_Consumer");
            String count = resultSet.getString("K_Qty1");

            if (lowPrice == null) {
                lowPrice = "0";
            }

            return new Product(name, lowPrice, highPrice, count);
        }
        // Return null if no result found
        return null;

    }


}
