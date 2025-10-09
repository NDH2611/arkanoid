package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;


public class GameEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;

    private Ball ball;
    private Paddle paddle;

    public GameEngine() {
        initialize();
        startGameLoop();
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(WIDTH/2,HEIGHT/2, 10, 5);
        paddle = new Paddle(WIDTH/2, HEIGHT*3/4, 75,25,0);

        StackPane root = new StackPane(canvas);
        scene = new Scene(root, WIDTH, HEIGHT);

        scene.setOnKeyPressed(event -> handleKeyInput(event));
        scene.setOnKeyReleased(event -> handleKeyInput(event));

    }

    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
            }
        };
        gameLoop.start();
    }

    public void update() {
        ball.update(paddle);
        paddle.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ball.render(gc);
        paddle.render(gc);
    }

    public void handleKeyInput(KeyEvent event) {
        paddle.handleInput(event);
    }

    public Scene getScene() {
        return scene;
    }
}
