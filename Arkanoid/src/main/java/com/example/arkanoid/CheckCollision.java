package com.example.arkanoid;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class CheckCollision {
    enum CollisionSide {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public static boolean checkEdge(Circle circle) {
        if (circle.getCenterX() - circle.getRadius() <= 0 ||
                circle.getCenterX() + circle.getRadius() >= GameEngine.WIDTH) {
            return true;
        }
        if (circle.getCenterY() - circle.getRadius() <= 0 ||
                circle.getCenterY() + circle.getRadius() >= GameEngine.HEIGHT) {
            return true;
        }
        return false;
    }

    public static boolean checkEdge(Rectangle rectangle) {
        if (rectangle.getX() <= 0 || rectangle.getX() + rectangle.getWidth() >= GameEngine.WIDTH) {
            return true;
        }
        if (rectangle.getY() <= 0 || rectangle.getY() + rectangle.getHeight() >= GameEngine.HEIGHT) {
            return true;
        }
        return false;
    }

    public static boolean checkCollision(Rectangle rectangle1, Rectangle rectangle2) {
        if (rectangle1.getX() > rectangle2.getX() + rectangle2.getWidth()) {
            return false;
        }
        if (rectangle1.getX() + rectangle1.getWidth() < rectangle2.getX()) {
            return false;
        }
        if (rectangle1.getY() + rectangle1.getHeight() < rectangle2.getY()) {
            return false;
        }
        if (rectangle1.getY() > rectangle2.getY() + rectangle2.getHeight()) {
            return false;
        }
        return true;
    }

    public static CollisionSide checkCollision(Circle circle, Rectangle rectangle) {
        double posX;
        double posY;

        if (circle.getCenterX() < rectangle.getX()) {
            posX = rectangle.getX();
        } else if (circle.getCenterX() > rectangle.getX() + rectangle.getWidth()) {
            posX = rectangle.getX() + rectangle.getWidth();
        } else {
            posX = circle.getCenterX();
        }

        if (circle.getCenterY() < rectangle.getY()) {
            posY = rectangle.getY();
        } else if (circle.getCenterY() > rectangle.getY() + rectangle.getHeight()) {
            posY = rectangle.getY() + rectangle.getHeight();
        } else {
            posY = circle.getCenterY();
        }

        if ((circle.getCenterX() - posX) * (circle.getCenterX() - posX) +
                (circle.getCenterY() - posY) * (circle.getCenterY() - posY) >
                circle.getRadius() * circle.getRadius()) {
            return CollisionSide.NONE;
        }
        double overlapLeft = (circle.getCenterX() + circle.getRadius()) - rectangle.getX();
        double overlapRight = (rectangle.getX() + rectangle.getWidth()) - (circle.getCenterX() - circle.getRadius());
        double overlapTop = (circle.getCenterY() - circle.getRadius()) - rectangle.getY();
        double overlapBottom = (rectangle.getY() + rectangle.getHeight()) - (circle.getCenterY() - circle.getRadius());

        double minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop,overlapBottom));

        if(minOverlap == overlapLeft) {
            return CollisionSide.LEFT;
        }
        if(minOverlap == overlapRight) {
            return CollisionSide.RIGHT;
        }
        if(minOverlap == overlapTop) {
            return CollisionSide.TOP;
        }
        return CollisionSide.BOTTOM;
    }


}
