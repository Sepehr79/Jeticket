package com.ansar.application.model.database.api;

import com.ansar.application.model.database.config.ConnectionFactory;
import com.ansar.application.model.entity.properties.ConnectionProperties;
import com.ansar.application.model.entity.properties.DatabaseProperties;
import com.ansar.application.model.entity.beans.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseApi {

    private static final Logger logger = Logger.getLogger(DatabaseApi.class.getName());

    private final Connection connection;

    private final PreparedStatement idSelector;

    public DatabaseApi(ConnectionProperties properties) throws SQLException {

        connection = ConnectionFactory.openConnection(properties);

        logger.info("Connection opened!");

        idSelector = connection.prepareStatement("select ID1, K_Code_B, A_Code, Name1, Price_Consumer, Price_Forosh, K_Qty1 from(\n" +
                "select kalaid.K_Code as ID1 ,A_Code, Name1, Price_Consumer, price_forosh from kalaid join Anbar on KalaId.K_Code = Anbar.K_Code\n" +
                ") P\n" +
                "join\n" +
                "(select k_Code, '0' as K_Code_B, 1 as K_Qty1 from KalaId left join (select K_Code as ID ,'0' as k_code_B, 1 as K_Qty1 from TblBasket_Kala) P on K_Code = P.ID\n" +
                "union all\n" +
                "select K_Code as ID2,  K_Code_B, K_Qty1 from TblBasket_Kala) T \n" +
                "on P.ID1 = T.K_Code where (ID1 = ? or K_Code_B = ?) and A_Code = ?;");

    }

    public Product getProductById(String id, String anbarCode) throws SQLException {

        DatabaseProperties properties = DatabaseProperties.deserializeFromXml();

        idSelector.setString(1, id);
        idSelector.setString(2, id);
        idSelector.setString(3, anbarCode);

        ResultSet resultSet = idSelector.executeQuery();

        if (resultSet.next()){
            String name = resultSet.getString("name1");
            String highPrice = resultSet.getString("price_forosh");
            String lowPrice = resultSet.getString("Price_Consumer");
            String count = resultSet.getString("K_Qty1");

            if (lowPrice == null){
                lowPrice = "0";
            }

            return new Product(name, lowPrice, highPrice, count);
        }

        connection.close();

        logger.info("Connection closed");

        return null;
    }


}
