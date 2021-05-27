package com.ansar.application.controller;

import com.ansar.application.model.database.config.ConnectionFactory;
import com.ansar.application.model.entity.ConnectionProperties;
import com.ansar.application.model.entity.DatabaseProperties;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainController implements Initializable {


    // Processing
    @FXML private TextField textInput;
    @FXML private Label anbarLabel;


    // Connection setting
    @FXML private PasswordField password;
    @FXML private TextField address;
    @FXML private TextField port;
    @FXML private TextField databaseName;
    @FXML private TextField userName;

    public void showAnbarDialog(MouseEvent mouseEvent) {
        TextInputDialog dialog = new TextInputDialog();
        DatabaseProperties properties = new DatabaseProperties();

        dialog.setTitle("Input");
        dialog.setHeaderText("Please enter storeroom number");

        Optional<String> anbarId = dialog.showAndWait();

        anbarId.ifPresent(s ->
                {
                    if (!s.equals("")) {
                        properties.setAnbarName(s);
                        anbarLabel.setText(s);

                        DatabaseProperties.serializeToXml(properties);
                    }
                }
        );
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER)
            textInput.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // connection page
        ConnectionProperties properties = ConnectionProperties.deserializeFromXml();

        address.setText(properties.getAddress());
        port.setText(properties.getPort());
        databaseName.setText(properties.getDatabaseName());
        userName.setText(properties.getUserName());
        password.setText(properties.getPassword());

        // processing page
        textInput.requestFocus();
        DatabaseProperties databaseProperties = DatabaseProperties.deserializeFromXml();
        anbarLabel.setText(databaseProperties.getAnbarName());
    }

    public void doConnection(ActionEvent actionEvent) {
        String address = this.address.getText();
        String port = this.port.getText();
        String databaseName = this.databaseName.getText();
        String userName = this.userName.getText();
        String password = this.password.getText();

        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().address(address).port(port).
                databaseName(databaseName).userName(userName).password(password).build();

        ConnectionProperties.serializeToXml(connectionProperties);


    }
}
