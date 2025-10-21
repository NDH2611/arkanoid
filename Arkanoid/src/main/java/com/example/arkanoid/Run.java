package com.example.arkanoid;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class Run extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(loader.load());
        stage.setTitle("Hello!");
        stage.setScene(menuScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
