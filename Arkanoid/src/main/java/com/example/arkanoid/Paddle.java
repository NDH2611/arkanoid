package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends MovableObject {
    private static final double SPEED = 5.0;

    private boolean moveLeft = false;
    private boolean moveRight = false;
    private Rectangle rect;

    public Paddle(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
        rect = new Rectangle(x, y, width, height);
    }

    public void handleInput(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = true;
                    break;
                case RIGHT:
                    moveRight = true;
                    break;
                default:
                    break;
            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case LEFT:
                    moveLeft = false;
                    break;
                case RIGHT:
                    moveRight = false;
                default:
                    break;
            }
        }

        if (moveLeft && !moveRight) {
            dx = -SPEED;
        } else if (moveRight && !moveLeft) {
            dx = SPEED;
        } else {
            dx = 0;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }

    public void update() {
        x += dx;
        rect.setX(x);
        if (CheckCollision.checkEdge(rect)) {
            x -= dx;
            rect.setX(x);
            dx = 0;
        }
        y += dy;
        rect.setY(y);
        if (CheckCollision.checkEdge(rect)) {
            y -= dy;
            rect.setY(y);
            dy = 0;
        }
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
}
