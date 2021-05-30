package com.ansar.application.controller;

import com.ansar.application.model.database.api.DatabaseApi;
import com.ansar.application.model.database.config.ConnectionFactory;
import com.ansar.application.model.entity.beans.Product;
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

                DatabaseApi api = new DatabaseApi(connectionProperties);

                String id = textInput.getText();

                Product product = api.getProductById(id, databaseProperties.getAnbarName().trim());

                if (product != null) {
                    name.setText(product.getName());

                    String storeStrPrice = formatter.format(product.getLowPrice());
                    String highStrPrice = formatter.format(product.getHighPrice());

                    storePrice.setText(storeStrPrice);
                    highPrice.setText(highStrPrice);

                    int discountNumber = product.getDiscount();
                    if (discountNumber > 10)
                        discount.setText(String.valueOf(discountNumber));
                    else
                        discount.setText("");
                } else {

                    name.setText("");
                    storePrice.setText("");
                    highPrice.setText("");
                    discount.setText("");
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Problem with connecting!");
            alert.setContentText("Please check your connection setting");
            alert.showAndWait();
        }catch (NumberFormatException | ArithmeticException exception){
            exception.printStackTrace();
        } finally {
            textInput.setText("");
        }

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

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Connection successful !");
            alert.showAndWait();

        } catch (SQLException | NumberFormatException exception) {
            exception.printStackTrace();

            logger.info("Connection unsuccessful !");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Connection unsuccessful !");
            alert.showAndWait();
        }
    }
}
