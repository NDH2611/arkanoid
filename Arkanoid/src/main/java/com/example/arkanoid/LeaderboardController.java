package com.example.arkanoid;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {
    @FXML
    private TableView<ScoreRecord> leaderboardTable;
    @FXML
    private TableColumn<ScoreRecord, Integer> rankColumn;
    @FXML
    private TableColumn<ScoreRecord, String> playerColumn;
    @FXML
    private TableColumn<ScoreRecord, Integer> levelColumn;
    @FXML
    private TableColumn<ScoreRecord, Integer> scoreColumn;
    @FXML
    private ComboBox<String> modeSelector;
    @FXML
    private Button backButton;

    private DatabaseManager dbManager;
    private Stage stage;
    private String currentMode = "Classic";

    public void initialize() {
        dbManager = DatabaseManager.getInstance();
        rankColumn.setCellValueFactory(cellData -> {
            int index = leaderboardTable.getItems().indexOf(cellData.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(index).asObject();
        });
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("levelNum"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        ObservableList<String> modes = FXCollections.observableArrayList("Classic");
        modeSelector.setItems(modes);
        modeSelector.setValue(currentMode);
        loadLeaderboard(currentMode);
        modeSelector.setOnAction(event -> {
            currentMode = modeSelector.getValue();
            loadLeaderboard(currentMode);
        });
    }

    private void loadLeaderboard(String mode) {
        List<ScoreRecord> scores = dbManager.getTopScore(mode, 10);
        ObservableList<ScoreRecord> scoreData = FXCollections.observableArrayList(scores);
        leaderboardTable.setItems(scoreData);
    }

    @FXML
    private void onBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Arkanoid");
        MenuController controller = (MenuController) loader.getController();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMode(String mode) {
        this.currentMode = mode;
        if (modeSelector != null) {
            modeSelector.setValue(mode);
            loadLeaderboard(mode);
        }
    }
}
