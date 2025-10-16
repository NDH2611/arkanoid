package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick extends GameObject {
    private TYPE type;
    private boolean visible;
    private Rectangle rectangle;
    //private final static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.x = x;
        this.y = y;
        this.visible = true;
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void render(GraphicsContext gc) {
        if (visible) {
            switch (type) {
                case GREEN:
                    gc.setFill(Color.GREEN);
                    break;
                case ORANGE:
                    gc.setFill(Color.ORANGE);
                    break;
                case YELLOW:
                    gc.setFill(Color.YELLOW);
                    break;
                case PURPLE:
                    gc.setFill(Color.PURPLE);
                    break;
                case PINK:
                    gc.setFill(Color.PINK);
                    break;
            }
            gc.fillRect(x, y, width, height);
        }
    }


    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    enum TYPE{
        GREEN,
        YELLOW,
        PURPLE,
        PINK,
        ORANGE
    }

    public TYPE getType() {
        return type;
    }
    public  void setType(TYPE type) {
        this.type = type;
    }
}
