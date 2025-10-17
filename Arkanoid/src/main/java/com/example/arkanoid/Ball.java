package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends MovableObject {
    protected double radius;
    protected Circle circle;

    public Ball(double x, double y, double radius, double speed) {
        super(x - radius, y - radius, radius * 2, radius * 2, speed);
        circle = new Circle(x, y, radius);
        this.radius = radius;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);
    }

    public void update() {
        setX(x += dx);
        setY(y += dy);
        CheckCollision.checkBallWallCollision(this);
    }

    @Override
    public void setX(double x) {
        this.x = x;
        circle.setCenterX(x + width / 2);
    }

    @Override
    public void setY(double y) {
        this.y = y;
        circle.setCenterY(y + height / 2);
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
