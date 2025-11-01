package com.example.arkanoid;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class GameStateController {
    private GameState currentState = GameState.MENU;
    private GameEngine gameEngine;
    private Stage stage;

    public GameStateController(Stage stage, GameEngine gameEngine) {
        this.stage = stage;
        this.gameEngine = gameEngine;
    }

    public void setState(GameState newState) {
        this.currentState = newState;
        System.out.println("state changed");
        switch (newState) {
            case MENU: showMenu(); break;
            case RUNNING: startGame(); break;
            case PAUSE: pauseGame(); break;
            case GAME_OVER: gameOverRestart(); break;

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
        for(PowerUp powerUp: gameEngine.getActivePowerUps()) {
            if(powerUp instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                shr.resumeEffect();

            } else if (powerUp instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp =  (ExpandPaddlePowerUp) powerUp;
                exp.resumeEffect();
            }
        }
    }

    private void pauseGame() {
        for(PowerUp powerUp: gameEngine.getActivePowerUps()) {
            if(powerUp instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                shr.pauseEffect();
            } else if (powerUp instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp =  (ExpandPaddlePowerUp) powerUp;
                exp.pauseEffect();
            }
        }
        gameEngine.stopGameLoop();
    }

    private void gameOverRestart() {
        gameEngine.stopGameLoop();
        System.out.println("game over");
    }
}
