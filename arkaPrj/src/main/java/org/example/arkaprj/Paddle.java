package org.example.arkaprj;

import javafx.scene.shape.Rectangle;

public class Paddle extends MoveObject {
    private Rectangle paddle;
    private double screenWidth;

    public Paddle(Rectangle paddle, int speed, double screenWidth) {
        super(paddle.getLayoutX(), paddle.getLayoutY(),
                (int) paddle.getWidth(), (int) paddle.getHeight(), speed);

        this.paddle = paddle;
        this.screenWidth = screenWidth;
        this.shape = paddle;
    }

    public void moveLeft() {
        setDirection(-1, 0);
    }

    public void moveRight() {
        setDirection(1, 0);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void update() {
        basicMove();

        if (x < 0) {
            x = 0;
        }
        if (x + width > screenWidth) {
            x = screenWidth - width;
        }

        paddle.setLayoutX(x);
        paddle.setLayoutY(y);
    }

    @Override
    public void render() {
        paddle.setLayoutX(x);
        paddle.setLayoutY(y);
    }

    public Rectangle getNode() {
        return paddle;
    }
}
