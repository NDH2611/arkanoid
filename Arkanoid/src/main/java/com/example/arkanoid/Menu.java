package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Menu {
    enum GameMenu {
        MENU,
        START,
        MODE,
        HDSD,
        EXIT
    }

    @FXML
    private Button Start;

    @FXML
    private Button Mode;

    @FXML
    private Button Hdsd;

    @FXML
    private Button Exit;

}
