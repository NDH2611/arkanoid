package com.example.arkanoid;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Run extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        GameEngine game =  new GameEngine();

        stage.setTitle("Hello!");
        stage.setScene(game.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
