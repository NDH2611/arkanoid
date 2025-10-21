package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExpandPaddlePowerUp extends PowerUp {
    private double originalWidth;

    public ExpandPaddlePowerUp(double x, double y) {
        super(x, y, PowerUpType.EXPAND_PADDLE,  5000);
    }

    public void applyEffect(Paddle paddle) {
        originalWidth = paddle.getWidth();
        paddle.setWidth(originalWidth * 1.25);
        paddle.getRectangle().setWidth(paddle.getWidth());
    }

    public void removeEffect(Paddle paddle) {
        paddle.setWidth(originalWidth);
        paddle.getRectangle().setWidth(paddle.getWidth());
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, rectangle.getWidth(), rectangle.getHeight());
        gc.setFill(Color.RED);
        gc.fillText("E", x + 7, y + 14);
    }
}
