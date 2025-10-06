package org.example.arkaprj;

import javafx.scene.shape.Shape;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Shape shape;

    public GameObject(double x, double y, double width, double height, Shape shape) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.shape = shape;
    }

    public Shape getBound() {
        return shape;
    }

    public boolean intersects(GameObject other) {
        return shape.getBoundsInParent().intersects(other.getBound().getBoundsInParent());
    }

    public abstract void update();
    public abstract void render();
}
