package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class GameEngine {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;

    private Ball ball;

    public GameEngine() {
        initialize();
        startGameLoop();
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(WIDTH/2,HEIGHT/2, 10, 2);

        StackPane root = new StackPane(canvas);
        scene = new Scene(root, WIDTH, HEIGHT);

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
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ball.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.RED);
        ball.render(gc);
    }

    public Scene getScene() {
        return scene;
    }
}
