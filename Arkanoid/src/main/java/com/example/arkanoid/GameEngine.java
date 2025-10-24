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
    private long startTime = 0;

    private Ball ball;
    private Paddle paddle;
    private ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    public GameEngine() {
        initialize();
        startGameLoop();
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        ball = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, BALL_SPEED);
        paddle = new Paddle(WIDTH / 2.0, HEIGHT * 3 / 4.0, 75, 25, 0);
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
                updateGame();
                renderGame();
            }
        };
        gameLoop.start();
    }

    private void createLevel() {
        Level level = new Level();
        levels.add(level);
    }

    private void updateLevel() {
        for (Brick brick : levels.get(0).getBricks()) {
            if (!brick.isVisible()) {
                continue;
            }
            CheckCollision.CollisionSide side = CheckCollision.checkCollision(ball.getCircle(), brick.getRectangle());
            if (side != CheckCollision.CollisionSide.NONE) {
                if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
                    ball.setDx(-ball.getDx());
                } else if (side == CheckCollision.CollisionSide.TOP || side == CheckCollision.CollisionSide.BOTTOM) {
                    ball.setDy(-ball.getDy());
                }
                if(brick.isBreakable()) {
                    brick.setStrength(brick.getStrength()-1);
                    if(brick.getStrength()<=0) {
                        brick.setVisible(false);
                        if (Math.random() < 0.999) {
                            if (Math.random() < 0.5) {
                                powerUps.add(new ExpandPaddlePowerUp(brick.getX() + 5, brick.getY() + 5));
                            } else {
                                powerUps.add(new ShrinkPaddlePowerUp(brick.getX() + 5, brick.getY() + 5));
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private void updatePowerUp() {
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).update();
            if (powerUps.get(i).getY() + powerUps.get(i).getHeight() > HEIGHT) {
                powerUps.remove(i);
            }

            if (CheckCollision.checkCollision(powerUps.get(i).getRectangle(), paddle.getRectangle())) {
                if (powerUps.get(i) instanceof ExpandPaddlePowerUp) {
                    ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUps.get(i);
                    exp.applyEffect(paddle);
//                    System.out.println(System.currentTimeMillis() - startTime);
//                    if(System.currentTimeMillis() - startTime > exp.getDuration()) {
//                        exp.removeEffect(paddle);
//                    }
                } else if (powerUps.get(i) instanceof ShrinkPaddlePowerUp) {
                    ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUps.get(i);
                    shr.applyEffect(paddle);
//                    System.out.println(System.currentTimeMillis() - startTime);
//                    if(System.currentTimeMillis() - startTime > shr.getDuration()) {
//                        shr.removeEffect(paddle);
//                    }
                }
                powerUps.remove(i);
            }
        }
    }

    private void renderPowerUp() {
        for (int i = 0; i < powerUps.size(); i++) {
            if(powerUps.get(i) instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUps.get(i);
                exp.render(gc);
            } else if(powerUps.get(i) instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUps.get(i);
                shr.render(gc);
            }
        }
    }

    public void updateGame() {
        ball.update();

        CheckCollision.CollisionSide side = CheckCollision.checkCollision(ball.getCircle(), paddle.getRectangle());
        if (side == CheckCollision.CollisionSide.TOP) {
            CheckCollision.caculatedBallBounceAngle(ball, paddle);
        } else if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
            ball.setDx(-ball.getDx());
        }
        updateLevel();
        paddle.update();
        updatePowerUp();
    }

    public void renderGame() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ball.render(gc);
        paddle.render(gc);
        levels.get(0).render(gc);
        renderPowerUp();
    }

    public void handleKeyInput(KeyEvent event) {
        paddle.handleInput(event);
    }

    public Scene getScene() {
        return scene;
    }
}
