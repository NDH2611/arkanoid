package org.example.arkaprj;

public abstract class MoveObject extends GameObject {
    protected int dx;
    protected int dy;
    protected double speed;

    public MoveObject(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, null);
        this.speed = speed;
    }

    public void basicMove() {
        this.x += dx * speed;
        this.y += dy * speed;
    }

    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void stop() {
        dx = dy = 0;
    }
}
