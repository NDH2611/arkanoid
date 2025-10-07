package org.example.arkaprj;

import javafx.scene.shape.Shape;

public class CheckCollision {

    public boolean isCollision(Shape shape1, Shape shape2) {
        return Shape.intersect(shape1, shape2).getBoundsInLocal().getWidth() > 0;
    }

    public void onCollision(GameObject obj1, GameObject obj2) {
        if (obj1 instanceof Ball && (obj2 instanceof Paddle || obj2 instanceof Brick)) {

            Ball ball = (Ball) obj1;

            if (isCollision(ball.getShape(), obj2.getShape())) {
                ball.setDy(-ball.getDy());
                if (obj2 instanceof Brick) {
                    Brick brick = (Brick) obj2;
                    brick.destroy();
                }
            }
        }
    }

}
