package org.example.arkaprj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Run extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(loader.load(), 420, 539);

        GameController gameController = loader.getController();

        gameController.startBallMovement();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    gameController.movePaddle(-1);
                    break;
                case RIGHT:
                    gameController.movePaddle(1);
                    break;
            }
        });

        stage.setTitle("Arkanoid JavaFX");
        stage.setScene(scene);
        stage.show();

        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
