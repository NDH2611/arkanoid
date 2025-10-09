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
        if (CheckCollision.checkEdge(shape) || CheckCollision.checkCollision(shape, paddle.getRect())) {
            x -= dx;
            shape.setCenterX(x + width / 2);
            dx = -dx;
        }
        y += dy;
        shape.setCenterY(y + height / 2);
        if (CheckCollision.checkEdge(shape) || CheckCollision.checkCollision(shape, paddle.getRect())) {
            y -= dy;
            shape.setCenterY(y + height / 2);
            dy = -dy;
        }
//        if (dy > 0 && CheckCollision.checkCollision(shape, paddle.getRect())) {
//            y = paddle.getRect().getY()-height;
//            shape.setCenterY(y + height / 2);
//            dy = -dy;
//        }
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }
}
