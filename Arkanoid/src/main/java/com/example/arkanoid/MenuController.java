package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button Start;
    @FXML
    private Button Mode;
    @FXML
    private Button Guide;
    @FXML
    private Button Exit;
    @FXML
    private Text text;
    @FXML
    private Button Leaderboard;

    private Stage stage;

    @FXML
    private void onStart() {
        try {
            Stage stage = (Stage) Start.getScene().getWindow();
            GameEngine game = new GameEngine(stage);

            game.inputUsername();
            stage.setScene(game.getScene());
            stage.centerOnScreen();
            stage.show();

            GameStateController controller = game.getTroller();
            controller.setState(GameState.RUNNING);

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
    public void onLeaderboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("leaderboard.fxml"));
        Parent root = loader.load();

        LeaderboardController controller = loader.getController();

        Stage leaderboardStage = (Stage) Leaderboard.getScene().getWindow();
        leaderboardStage.setTitle("Leaderboard");
        leaderboardStage.setScene(new Scene(root));

        controller.setStage(leaderboardStage);
        controller.setMode("Classic");

        leaderboardStage.show();
    }

    @FXML
    private void onGuide() {
        try {
            Stage stage = (Stage) Guide.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hdsd.fxml"));
            Scene hdsdScene = new Scene(loader.load());
            stage.setScene(hdsdScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onExit(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
