package com.example.arkanoid;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private static final int WIDTH_BRICK = 80;
    private static final int HEIGHT_BRICK = 40;
    private static final int LEVEL_COLUMN = 9;
    private static final int LEVEL_ROW = 5;
    private static final int BRICK_SPACE = 5;
    private static final int DISTANCE_Y = 10;
    private static final int DISTANCE_X = (GameEngine.WIDTH - WIDTH_BRICK * LEVEL_COLUMN
            - BRICK_SPACE * (LEVEL_COLUMN - 1)) / 2;
    private static final int BRICK_TYPE = 5;

    private ArrayList<Brick> bricks =  new ArrayList<>();

    Level() {
        for(int i = 0; i < LEVEL_ROW; i++) {
            for(int j = 0; j < LEVEL_COLUMN; j++) {
                int y = i * (HEIGHT_BRICK + BRICK_SPACE) + DISTANCE_Y;
                int x = j * (WIDTH_BRICK + BRICK_SPACE) + DISTANCE_X;
                Brick brick = new Brick(x, y, WIDTH_BRICK, HEIGHT_BRICK);
                int type = (int) (Math.random() * BRICK_TYPE) + 1;
                if (type == 1) {
                    brick.setType(Brick.TYPE.GREEN);
                } else if (type == 2) {
                    brick.setType(Brick.TYPE.ORANGE);
                } else if (type == 3) {
                    brick.setType(Brick.TYPE.YELLOW);
                } else if (type == 4) {
                    brick.setType(Brick.TYPE.PURPLE);
                } else {
                    brick.setType(Brick.TYPE.PINK);
                }
                brick.brickStatus();
                bricks.add(brick);
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Brick brick : bricks) {
            brick.render(gc);
        }
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }
}
