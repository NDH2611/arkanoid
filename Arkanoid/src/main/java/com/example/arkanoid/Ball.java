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

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
        shape.setCenterX(x+width/2);
        shape.setCenterY(y+height/2);
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }
}
