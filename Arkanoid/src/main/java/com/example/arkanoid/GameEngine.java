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

    public static int height = 40;
    public static int width = 80;
    public static int space = 5;
    public static int columns = 9;
    public static int rows = 5;
    public static int distanceY = 10;
    public static int distanceX = (800 - width * columns - space * (columns - 1)) / 2;
    public static int bricksType = 5;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;

    private Ball ball;
    private Paddle paddle;

    private final static ArrayList<Brick> bricks = new ArrayList<>();

    public GameEngine() {
        initialize();
        startGameLoop();
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(WIDTH / 2, HEIGHT / 2, 10, BALL_SPEED);
        paddle = new Paddle(WIDTH / 2, HEIGHT * 3 / 4, 75, 25, 0);
        showBricks();

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

    private void renderUpdateBrick() {
        for (Brick brick : bricks) {
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
        renderUpdateBrick();
        paddle.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ball.render(gc);
        paddle.render(gc);
        for (Brick brick : bricks) {
            brick.render(gc);
        }
    }

    public static void newBricks() {
        bricks.clear();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int x = i * (width + space) + distanceX;
                int y = j * (height + space) + distanceY;
                int type = (int) (Math.random() * bricksType) + 1;
                Brick brick = new Brick(x, y, width, height);
                if (type == 1) {
                    brick.setType(Brick.TYPE.GREEN);
                } else if (type == 2) {
                    brick.setType(Brick.TYPE.ORANGE);
                } else if (type == 3) {
                    brick.setType(Brick.TYPE.YELLOW);
                } else if (type == 4) {
                    brick.setType(Brick.TYPE.PURPLE);
                } else {
                    brick.setType(Brick.TYPE.PINK);
                }
                bricks.add(brick);
            }
        }
    }

    public void showBricks() {
        newBricks();
    }

    public void handleKeyInput(KeyEvent event) {
        paddle.handleInput(event);
    }

    public Scene getScene() {
        return scene;
    }
}
