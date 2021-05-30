package com.ansar.application;

import com.ansar.application.view.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = ViewLoader.getMainPage();

        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){

    }

    @Override
    public void stop(){

    }
}
