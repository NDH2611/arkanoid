package org.example.arkaprj;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
        ball=new Ball(circle, 4);
        playerPaddle=new Paddle(paddle, 20, background.getPrefWidth());
    }

    public void startBallMovement() {
        timeline = new Timeline(new KeyFrame(Duration.millis(20), e-> {
            moveBall();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void moveBall() {
        ball.update();
    }

    private void resetBall() {
        double centerX=background.getPrefWidth()/2;
        double centerY=background.getPrefHeight()/2;
        ball.getShape().getCenterX();
        ball.getShape().getCenterY();
    }

    public void movePaddle(int direction) {
        if(playerPaddle!=null) {
            if(direction<0) {
                playerPaddle.moveLeft();
            } else {
                playerPaddle.moveRight();
            }
            playerPaddle.update();
        }
    }
}