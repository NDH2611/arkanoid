package com.example.arkanoid;

import javafx.animation.PauseTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ExpandPaddlePowerUp extends PowerUp {

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, PowerUpType.EXPAND_PADDLE,  5000);
    }

    public void applyEffect(Paddle paddle) {
        paddle.setWidth(GameEngine.PADDLE_WIDTH * 1.25);
        paddle.getRectangle().setWidth(paddle.getWidth());
        PauseTransition timer =  new PauseTransition(Duration.millis(duration));
        timer.setOnFinished(event -> {
            System.out.println("Shrink Paddle PowerUp Finished");
            paddle.setWidth(GameEngine.PADDLE_WIDTH);
            paddle.getRectangle().setWidth(paddle.getWidth());
        });
        timer.play();
    }

    public void removeEffect(Paddle paddle) {
        paddle.setWidth(GameEngine.PADDLE_WIDTH);
        paddle.getRectangle().setWidth(paddle.getWidth());
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, rectangle.getWidth(), rectangle.getHeight());
        gc.setFill(Color.RED);
        gc.fillText("E", x + 7, y + 14);
    }
}
