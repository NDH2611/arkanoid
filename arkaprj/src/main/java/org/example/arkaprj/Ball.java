package org.example.arkaprj;

import javafx.scene.shape.Circle;

public class Ball extends MoveObject {
    private Circle shape;

    private double xVelocity;
    private double yVelocity;

    public Circle getShape() {
        return shape;
    }

    public Ball(Circle circle, int speed) {
        super(circle.getCenterX(), circle.getCenterY(),
                circle.getRadius()*2, circle.getRadius()*2, speed);
        this.shape=circle;
        this.xVelocity=3.0;
        this.yVelocity=4.0;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public void move() {
        shape.setLayoutX(shape.getLayoutX() + xVelocity);
        shape.setLayoutY(shape.getLayoutY() + yVelocity);
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render() {
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}