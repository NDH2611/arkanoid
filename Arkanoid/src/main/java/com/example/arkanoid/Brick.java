package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Brick extends GameObject {
    static int height = 20;
    static int width = 60;
    static int space = 5;
    static int columns = 12;
    static int rows = 5;
    static int distanceY = 10;
    static int distanceX = (GameEngine.WIDTH-width*columns-space*(columns-1))/2;
    static int bricksType=5;

    private TYPE type;
    private int x;
    private int y;
    private boolean visible;
    private final static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick(int x, int y, int width, int height) {
        super(x,y,width,height);
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public static void newBricks() {
        bricks.clear();
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                int x = i * (width + space) + distanceX;
                int y = j * (height + space) + distanceY;
                int type =(int)(Math.random()*bricksType)+1;
                Brick brick = new Brick(x, y, width, height);
                if(type==1) {
                    brick.type=TYPE.GREEN;
                } else if (type==2) {
                    brick.type=TYPE.ORANGE;
                }  else if (type==3) {
                    brick.type=TYPE.YELLOW;
                } else if(type==4) {
                    brick.type=TYPE.PURPLE;
                } else {
                    brick.type=TYPE.PINK;
                }
                bricks.add(brick);
            }
        }
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

    public static ArrayList<Brick> getBricks() {
        return bricks;
    }

    public boolean getVisible() {
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
