package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ModeController extends MenuController{
    @FXML
    private Button Story;
    @FXML
    private Button Infinite;
    //protected Mode mode;

    public void onStory() {
        try {
            GameEngine game = new GameEngine();
            Stage stage = (Stage) Story.getScene().getWindow();
            stage.setScene(game.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onInfinite() {
        try {
            GameEngine game = new GameEngine();
            Stage stage = (Stage) Infinite.getScene().getWindow();
            stage.setScene(game.getScene());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
