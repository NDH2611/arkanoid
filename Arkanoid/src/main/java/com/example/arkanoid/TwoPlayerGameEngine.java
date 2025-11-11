package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TwoPlayerGameEngine {
    private Stage stage;
    private Scene scene;
    private HBox container;

    private AnimationTimer gameLoop;
    private long startTime;

    private PlayerArea player1Area;
    private PlayerArea player2Area;

    public TwoPlayerGameEngine(Stage stage) {
        this.stage = stage;

        player1Area = new PlayerArea();
        player2Area = new PlayerArea();

        container = new HBox();

        Rectangle separator = new Rectangle(3, GameConfig.HEIGHT);
        separator.setFill(GameConfig.SEPARATOR_COLOR);

        container.getChildren().addAll(
                player1Area.getCanvas(),
                separator,
                player2Area.getCanvas());

        scene = new Scene(container, GameConfig.WIDTH * 2 + 3, GameConfig.HEIGHT);

        scene.setOnKeyPressed(event -> handleKeyInput(event));
        scene.setOnKeyReleased(event -> handleKeyInput(event));
    }

    public void startGameLoop() {
        if (gameLoop != null) {
            return;
        }
        startTime=System.nanoTime();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime=(now-startTime)/1000000000.0;
                startTime = now;
                updateGame(deltaTime);
                renderGame();
            }
        };
        gameLoop.start();
        System.out.println("game started");
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
            startTime = 0;
            System.out.println("game stopped.");
        }
    }

    private void updateGame(double deltaTime) {
        player1Area.update(deltaTime);
        player2Area.update(deltaTime);
    }

    public void handleKeyInput(KeyEvent event) {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.D) {
            player1Area.getPaddles().get(0).handleInput(event, true);
        }

        // Chỉ gửi sự kiện LEFT/RIGHT cho Player 2
        if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT) {
            player2Area.getPaddles().get(0).handleInput(event, false);
        }
    }
    private void renderGame() {
        player1Area.render();
        player2Area.render();
    }

    public Scene getScene() {
        return scene;
    }
}