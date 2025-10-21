package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class MenuController {
    @FXML
    private Button Start;
    @FXML
    private Button Mode;
    @FXML
    private Button Hdsd;
    @FXML
    private Button Exit;
    @FXML
    private Text text;

    @FXML
    private void onStart() {
        try {
            GameEngine game = new GameEngine();
            Stage stage = (Stage) Start.getScene().getWindow();
            stage.setScene(game.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onMode() {
        try {
            Stage stage = (Stage) Start.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mode.fxml"));
            Scene modeScene = new Scene(loader.load());
            stage.setScene(modeScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onHdsd() {
        try {
            Stage stage = (Stage) Hdsd.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hdsd.fxml"));
            Scene hdsdScene = new Scene(loader.load());
            stage.setScene(hdsdScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onExit() {

    }
}
