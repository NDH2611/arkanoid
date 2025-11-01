package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DoubleBallPowerUp extends PowerUp{
    public DoubleBallPowerUp(double x, double y) {
        super(x,y,PowerUpType.DOUBLE_BALL,5000, false);
    }
    public Ball applyEffect(Ball originalBall){
        Ball newBall=new Ball(originalBall.getX(), originalBall.getY(),
                originalBall.radius, GameEngine.BALL_SPEED);
        newBall.setDx(-originalBall.getDx());
        newBall.setDy(originalBall.getDy());
        return newBall;
    }
    public void render(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillRect(x,y,width,height);
        gc.setFill(Color.WHITE);
        gc.fillText("D", x+6, y+14);
    }
}
