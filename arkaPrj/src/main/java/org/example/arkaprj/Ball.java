package org.example.arkaprj;

import javafx.scene.shape.Circle;

public class Ball extends MoveObject {
    private Circle shape;

    public Ball(Circle circle, int speed) {
        super(circle.getCenterX(), circle.getCenterY(), circle.getRadius() * 2, circle.getRadius() * 2, speed);
        this.shape = circle;
        this.dx = 1;
        this.dy = -1;
    }

    @Override
    public void basicMove() {
        x += dx * speed;
        y += dy * speed;
        shape.setCenterX(x);
        shape.setCenterY(y);
    }

    public void reverseX() {
        setDirection(-dx, dy);
    }

    public void reverseY() {
        setDirection(dx, -dy);
    }

    public Circle getShape() {
        return shape;
    }

    @Override
    public void update() {
        basicMove();
    }

    @Override
    public void render() {
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}
