package com.example.arkanoid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

public class GameStateController {
    private GameState currentState = GameState.MENU;
    private GameEngine gameEngine;
    private Stage stage;
    private Parent pauseMenu;
    private Parent endMenu;
    @FXML
    private Button ReturnMenu;
    @FXML
    private Button PlayContinue;

    @FXML
    private void onReturnMenu() {
        showMenu();
    }

    @FXML
    private void onPlayContinue() {
        gameEngine.getRoot().getChildren().removeIf(node -> node instanceof Parent && node.lookup("#PlayContinue") != null);
        for (PowerUp powerUp : gameEngine.getActivePowerUps()) {
            if (powerUp instanceof ShrinkPaddlePowerUp) {
                ((ShrinkPaddlePowerUp) powerUp).resumeEffect();
            } else if (powerUp instanceof ExpandPaddlePowerUp) {
                ((ExpandPaddlePowerUp) powerUp).resumeEffect();
            }
        }
        gameEngine.startGameLoop();
    }

    public GameStateController() {
    }

    public GameStateController(Stage stage, GameEngine gameEngine) {
        this.stage = stage;
        this.gameEngine = gameEngine;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
        System.out.println("state changed");
        switch (newState) {
            case MENU:
                showMenu();
                break;
            case RUNNING:
                startGame();
                break;
            case PAUSE:
                pauseGame();
                break;
            case GAME_OVER:
                gameOverRestart();
                break;
            case READY:
                gameEngine.startGameLoop();
                break;

        }
    }

    public GameState getState() {
        return currentState;
    }

    private void showMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Scene menuScene = new Scene(loader.load());
            stage.setScene(menuScene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame() {
        stage.setScene(gameEngine.getScene());
        stage.show();
        gameEngine.getScene().getRoot().requestFocus();
        gameEngine.startGameLoop();
        for (PowerUp powerUp : gameEngine.getActivePowerUps()) {
            if (powerUp instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                shr.resumeEffect();

            } else if (powerUp instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUp;
                exp.resumeEffect();
            }
        }
    }

    private void pauseGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("state.fxml"));
            pauseMenu = loader.load();
            //StackPane.setAlignment(pauseMenu, Pos.CENTER);

            GameStateController controller = loader.getController();
            controller.setStage(stage);
            controller.setGameEngine(gameEngine);

            gameEngine.getRoot().getChildren().add(pauseMenu);
            pauseMenu.toFront();
            pauseMenu.requestFocus();
            pauseMenu.setPickOnBounds(true);

            gameEngine.stopGameLoop();

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (PowerUp powerUp : gameEngine.getActivePowerUps()) {
            if (powerUp instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                shr.pauseEffect();
            } else if (powerUp instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUp;
                exp.pauseEffect();
            }
        }
        //gameEngine.stopGameLoop();
    }

    private void gameOverRestart() {
        //gameEngine.stopGameLoop();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("endgame.fxml"));
            endMenu = loader.load();

            GameStateController controller = loader.getController();
            controller.setStage(stage);
            controller.setGameEngine(gameEngine);

            gameEngine.getRoot().getChildren().add(endMenu);
            pauseMenu.toFront();
            pauseMenu.requestFocus();
            pauseMenu.setPickOnBounds(true);

            gameEngine.stopGameLoop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }
}
