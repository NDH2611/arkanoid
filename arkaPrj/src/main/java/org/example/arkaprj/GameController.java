package org.example.arkaprj;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class GameController {
    @FXML
    private Rectangle paddle;
    @FXML
    private Pane background;
    @FXML
    private Circle circle;

    private Ball ball;
    private Paddle playerPaddle;
    private Timeline timeline;

    public void initialize() {
        System.out.println("GameController initialized");

        ball = new Ball(circle, 4);

        playerPaddle = new Paddle(paddle, 20, background.getPrefWidth());
    }

    public void startBallMovement() {
        System.out.println("Ball movement started");

        timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
            moveBall();
            checkCollisions();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void moveBall() {
        ball.update();
    }

    public void checkCollisions() {
        double radius = ball.getShape().getRadius();
        double ballX = ball.getShape().getCenterX();
        double ballY = ball.getShape().getCenterY();

        if (ballX - radius <= 0 || ballX + radius >= background.getPrefWidth()) {
            ball.reverseX();
        }

        if (ballY - radius <= 0) {
            ball.reverseY();
        }

        if (ballY + radius >= background.getPrefHeight()) {
            stopGame();
            resetBall();
        }

        checkPaddleCollision();
    }

    private void resetBall() {
        double centerX = background.getPrefWidth() / 2;
        double centerY = background.getPrefHeight() / 2;
        ball.getShape().setCenterX(centerX);
        ball.getShape().setCenterY(centerY);
    }

    public void stopGame() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public void checkPaddleCollision() {
        double radius = ball.getShape().getRadius();
        double ballX = ball.getShape().getCenterX();
        double ballY = ball.getShape().getCenterY();

        double paddleX = paddle.getLayoutX();
        double paddleY = paddle.getLayoutY();
        double paddleWidth = paddle.getWidth();
        double paddleHeight = paddle.getHeight();

        if (ballY + radius >= paddleY &&
                ballY + radius <= paddleY + paddleHeight &&
                ballX >= paddleX &&
                ballX <= paddleX + paddleWidth) {

            ball.reverseY();
            ball.getShape().setCenterY(paddleY - radius);
        }
    }

    public void movePaddle(int direction) {
        if (playerPaddle != null) {
            if (direction < 0) {
                playerPaddle.moveLeft();
            } else {
                playerPaddle.moveRight();
            }
            playerPaddle.update();
        }
    }

    public void stopPaddle() {
        if (playerPaddle != null) playerPaddle.stop();
    }
}
