package com.example.arkanoid;

public class CheckCollision {

    public static void checkEdge(Ball ball, Paddle paddle) {
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= 800) {
            ball.setDx(-ball.getDx());
        }
        if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= 600) {
            ball.setDy(-ball.getDy());
        }
        if (paddle.getX() < 0) {
            paddle.setX(0);
        } else if (paddle.getX() + paddle.getWidth() > 800) {
            paddle.setX(800 - paddle.getWidth());
        }

    }
    public static boolean rectRect(GameObject a, GameObject b) {
        return a.getX() < b.getX() + b.getWidth() &&
                a.getX() + a.getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getHeight() &&
                a.getY() + a.getHeight() > b.getY();
    }

    public static boolean circleRect(Ball circle, GameObject rect) {
        double centerX = circle.getX() + circle.getWidth() / 2.0;
        double centerY = circle.getY() + circle.getHeight() / 2.0;
        double radius = circle.getWidth() / 2.0;

        double closestX = clamp(centerX, rect.getX(), rect.getX() + rect.getWidth());
        double closestY = clamp(centerY, rect.getY(), rect.getY() + rect.getHeight());

        double dx = centerX - closestX;
        double dy = centerY - closestY;

        return dx * dx + dy * dy < radius * radius;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static void onCollision(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Ball) {
            Ball ball = (Ball) obj1;

            if (obj2 instanceof Paddle) {
                Paddle paddle = (Paddle) obj2;
                if (circleRect(ball, paddle)) {
                    reflectBallEdge(ball, paddle);
                    System.out.println("Ball hit Paddle!");
                }
            }
            /*
            else if (obj2 instanceof Brick) {
                Brick brick = (Brick) obj2;
                if (circleRect(ball, brick)) {
                    reflectBallEdge(ball, brick);
                    brick.destroy();
                    System.out.println("Ball hit Brick!");
                }
            }
            */
        }
    }

    private static void reflectBallEdge(Ball ball, GameObject rect) {
        double nextX = ball.getX() + ball.getDx();
        double nextY = ball.getY() + ball.getDy();
        double ballRadius = ball.getWidth() / 2.0;

        double rectLeft = rect.getX();
        double rectRight = rect.getX() + rect.getWidth();
        double rectTop = rect.getY();
        double rectBottom = rect.getY() + rect.getHeight();

        double centerX = nextX + ballRadius;
        double centerY = nextY + ballRadius;


        double rectCenterX = rect.getX() + rect.getWidth() / 2.0;
        double rectCenterY = rect.getY() + rect.getHeight() / 2.0;

        double dx = centerX - rectCenterX;
        double dy = centerY - rectCenterY;

        double absDX = Math.abs(dx);
        double absDY = Math.abs(dy);

        double overlapX = (rect.getWidth() / 2.0 + ballRadius) - absDX;
        double overlapY = (rect.getHeight() / 2.0 + ballRadius) - absDY;

        if (overlapX < overlapY) {
            if (dx > 0) {
                ball.setX(rectRight + 0.01);
            } else {
                ball.setX(rectLeft - ball.getWidth() - 0.01);
            }
            ball.setDx(-ball.getDx());
        } else {
            if (dy > 0) {
                ball.setY(rectBottom + 0.01);
            } else {
                ball.setY(rectTop - ball.getHeight() - 0.01);
            }
            ball.setDy(-ball.getDy());
        }
    }
}
