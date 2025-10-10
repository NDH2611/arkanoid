package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovableObject {
    private Circle shape;

    public Ball(double x, double y, double radius, double speed) {
        super(x - radius, y - radius, radius * 2, radius * 2, speed);
        shape = new Circle(x, y, radius);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);
    }

    public void update(Paddle paddle) {
        x += dx;
        shape.setCenterX(x + width / 2);
        if (CheckCollision.checkEdge(shape)) {
            x -= dx;
            shape.setCenterX(x + width / 2);
            dx = -dx;
        }
        y += dy;
        shape.setCenterY(y + height / 2);
        if (CheckCollision.checkEdge(shape)) {
            y -= dy;
            shape.setCenterY(y + height / 2);
            dy = -dy;
        }
        CheckCollision.CollisionSide side = CheckCollision.checkCollision(shape, paddle.getRect());
        switch (side) {
            case LEFT:
                x = paddle.getX() - width;
                dx = -GameEngine.BALL_SPEED;
                break;
            case RIGHT:
                x = paddle.getX() + paddle.getWidth();
                dx = GameEngine.BALL_SPEED;
                break;
            case TOP:
                y = paddle.getY() - height;
                dy = -GameEngine.BALL_SPEED;
                break;
            case BOTTOM:
                y = paddle.getY() + paddle.getHeight();
                dy = GameEngine.BALL_SPEED;
                break;
            default:
                break;
        }
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }
}
