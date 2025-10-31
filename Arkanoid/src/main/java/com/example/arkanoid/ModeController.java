package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModeController extends MenuController{
    @FXML
    private Button Story;
    @FXML
    private Button Infinite;

    public void onStory() {
        try {
            Stage stage = (Stage) Story.getScene().getWindow();
            GameEngine game = new GameEngine(stage);
            stage.setScene(game.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onInfinite() {
        try {
            Stage stage = (Stage) Infinite.getScene().getWindow();
            GameEngine game = new GameEngine(stage);
            stage.setScene(game.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
