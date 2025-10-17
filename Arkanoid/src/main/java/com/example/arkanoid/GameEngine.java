package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;


public class GameEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double BALL_SPEED = 3.0;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Level>  levels =  new ArrayList<>();

    public GameEngine() {
        initialize();
        startGameLoop();
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(WIDTH / 2, HEIGHT / 2, 10, BALL_SPEED);
        paddle = new Paddle(WIDTH / 2, HEIGHT * 3 / 4, 75, 25, 0);
        //showBricks();
        createLevel();

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

    private void createLevel() {
        Level level = new Level();
        levels.add(level);
    }

    private void renderUpdateLevel() {
        for (Brick brick : levels.get(0).getBricks()) {
            if (!brick.isVisible()) {
                continue;
            }
            CheckCollision.CollisionSide side = CheckCollision.checkCollision(ball.getCircle(), brick.getRectangle());
            if(side != CheckCollision.CollisionSide.NONE) {
                if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
                    ball.setDx(-ball.getDx());
                } else if (side == CheckCollision.CollisionSide.TOP || side == CheckCollision.CollisionSide.BOTTOM) {
                    ball.setDy(-ball.getDy());
                }
                brick.setVisible(false);
                break;
            }

        }
    }

    public void update() {
        ball.update();

        CheckCollision.CollisionSide side = CheckCollision.checkCollision(ball.getCircle(), paddle.getRectangle());
        if (side == CheckCollision.CollisionSide.TOP) {
            CheckCollision.caculatedBallBounceAngle(ball, paddle);
        } else if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
            ball.setDx(-ball.getDx());
        }
        renderUpdateLevel();
        paddle.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ball.render(gc);
        paddle.render(gc);
        levels.get(0).render(gc);
    }

    public void handleKeyInput(KeyEvent event) {
        paddle.handleInput(event);
    }

    public Scene getScene() {
        return scene;
    }
}
