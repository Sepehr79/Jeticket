package com.ansar.application.controller;

import com.ansar.application.model.database.api.DatabaseApi;
import com.ansar.application.model.database.config.ConnectionFactory;
import com.ansar.application.model.entity.beans.Product;
import com.ansar.application.model.entity.logic.Calculator;
import com.ansar.application.model.entity.properties.ConnectionProperties;
import com.ansar.application.model.entity.properties.DatabaseProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class MainController implements Initializable {

    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    // Processing
    @FXML private TextField textInput;

    @FXML private Text name;
    @FXML private Text storePrice;
    @FXML private Text highPrice;
    @FXML private Text discount;


    // Connection setting
    @FXML private PasswordField password;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private TextField databaseName;
    @FXML private TextField userName;

    private static ConnectionProperties connectionProperties;
    private static DatabaseProperties databaseProperties;

    // To format money fields
    private static final DecimalFormat formatter = new DecimalFormat("#,###");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load data
        connectionProperties = ConnectionProperties.deserializeFromXml();
        databaseProperties = DatabaseProperties.deserializeFromXml();

        // connection page
        address.setText(connectionProperties.getAddress());
        port.setText(connectionProperties.getPort());
        databaseName.setText(connectionProperties.getDatabaseName());
        userName.setText(connectionProperties.getUserName());
        password.setText(connectionProperties.getPassword());

        // processing page
        textInput.requestFocus();
    }

    public void showAnbarDialog(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Input");
        dialog.setHeaderText("Please enter storeroom number");
        dialog.getEditor().setText(databaseProperties.getAnbarName());

        Optional<String> anbarId = dialog.showAndWait();

        anbarId.ifPresent(s ->
                {
                    if (!s.equals("")) {
                        databaseProperties.setAnbarName(s);

                        DatabaseProperties.serializeToXml(databaseProperties);
                    }
                }
        );
    }

    public void keyPressed(KeyEvent keyEvent) {
        try {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                DatabaseApi api = new DatabaseApi();
                String id = textInput.getText();

                api.openConnection(connectionProperties, databaseProperties);
                Product product = api.getProductById(id);
                api.closeConnection();

                setCurrentProduct(product);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            showAlert("Problem with connecting!",
                    "Please check your connection setting",
                    Alert.AlertType.ERROR);

        }catch (NumberFormatException | ArithmeticException exception){
            // Catch Any expected exceptions
            // 0 / 0 Exception
            discount.setText("");
            exception.printStackTrace();
        } finally {
            // Finally, the input is reset
            textInput.setText("");
        }

    }

    public void setFocus(MouseEvent mouseEvent) {
        textInput.requestFocus();
    }

    public void doConnection(ActionEvent actionEvent) {
        String address = this.address.getText();
        String port = this.port.getText();
        String databaseName = this.databaseName.getText();
        String userName = this.userName.getText();
        String password = this.password.getText();

        connectionProperties = new ConnectionProperties.Builder().address(address).port(port).
                databaseName(databaseName).userName(userName).password(password).build();

        try {
            Connection connection = ConnectionFactory.openConnection(connectionProperties);

            connection.close();

            ConnectionProperties.serializeToXml(connectionProperties);

            logger.info("Connection successful !");

            showAlert("Connection successful !", null, Alert.AlertType.INFORMATION);

        } catch (SQLException | NumberFormatException exception) {
            exception.printStackTrace();

            logger.info("Connection unsuccessful !");

            showAlert("Cannot connect to the sql server", "Please check your connection settings",
                    Alert.AlertType.ERROR);

        }
    }

    // Private methods

    /**
     * Change the displayed product
     * @param product will display on view
     * Empty the appearance if the product is null
     */
    private void setCurrentProduct(Product product){
        if (product != null) {

            name.setText(product.getName());

            String storeStrPrice = formatter.format(product.getLowPrice());
            String highStrPrice = formatter.format(product.getHighPrice());

            storePrice.setText(storeStrPrice);
            highPrice.setText(highStrPrice);

            try {
                int discountNumber = Calculator.calculateDiscount(product.getLowPrice(), product.getHighPrice());
                if (discountNumber >= 10)
                    discount.setText(String.valueOf(discountNumber) + "%");
                else
                    discount.setText("");
            }catch (ArithmeticException | NumberFormatException | NullPointerException exception){
                logger.info("Exception while calculating discount!");
//                exception.printStackTrace();
            }

        } else {
            resetPage();
        }
    }

    /**
     * Reset the current page
     */
    private void resetPage(){
         name.setText("");
         storePrice.setText("");
         highPrice.setText("");
         discount.setText("");
     }

     private void showAlert(String headerText, String contentText, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
     }
}
