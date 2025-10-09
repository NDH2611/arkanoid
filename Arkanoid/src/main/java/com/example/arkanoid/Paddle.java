package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {
    public static final double SPEED = 5.0;
    private Rectangle rect;

    public Paddle(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
        rect = new Rectangle(x, y, width, height);
    }

    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case LEFT:
                    dx = -SPEED;
                    break;
                case RIGHT:
                    dx = SPEED;
                    break;
                default:
                    break;
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case LEFT:
                    dx = 0;
                    break;
                case RIGHT:
                    dx = 0;
                default:
                    break;
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        rect.setLayoutX(x);
        rect.setLayoutY(y);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
}
