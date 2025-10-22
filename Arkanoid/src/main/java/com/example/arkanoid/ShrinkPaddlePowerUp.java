package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShrinkPaddlePowerUp extends PowerUp {
    private double originalWidth;

    public ShrinkPaddlePowerUp(double x, double y) {
        super(x, y, PowerUpType.SHRINK_PADDLE ,5000);
    }

    public void applyEffect(Paddle paddle) {
        originalWidth = paddle.getWidth();
        paddle.setWidth(originalWidth*0.75);
        paddle.getRectangle().setWidth(paddle.getWidth());
    }

    public void removeEffect(Paddle paddle) {
        paddle.setWidth(originalWidth);
        paddle.getRectangle().setWidth(paddle.getWidth());
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(x, y, rectangle.getWidth(), rectangle.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillText("S", x + 7, y + 14);
    }

}
