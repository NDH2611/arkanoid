package com.example.arkanoid;

public class PowerUp extends GameObject{
    private static final double FALL_SPEED = 1.5;
    private double duration;
    private boolean active;

    public PowerUp(double x, double y, double width, double height, double duration) {
        super(x, y, width, height);
        this.duration = duration;
    }


}
