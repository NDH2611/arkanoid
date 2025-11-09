package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsernameLogController {
    @FXML
    private TextField usernameField;
    @FXML
    private Button continueButton;
    private boolean confirmed = false;
    private String username = "player";
    private Stage logStage;

    @FXML
    private void initialize() {
        usernameField.requestFocus();
        usernameField.setOnAction(event -> {
            handleConfirm();
        });
    }
    @FXML
    private void handleConfirm() {
        String input = usernameField.getText();
        if (input.isEmpty()) {
            this.username = "Player";
        } else {
            this.username = input;
        }
        confirmed = true;
        if (logStage != null) {
            logStage.close();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Stage getLogStage() {
        return logStage;
    }

    public void setLogStage(Stage logStage) {
        this.logStage = logStage;
    }
}
