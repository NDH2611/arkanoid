package com.example.arkanoid;

import com.example.arkanoid.GameConfig;
import com.example.arkanoid.Level;
import com.example.arkanoid.Ball;
import com.example.arkanoid.Paddle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

import java.util.List;

public class GameRenderer {
    private GraphicsContext gc;
    private Font uiFont;

    public GameRenderer(GraphicsContext gc, Font uiFont) {
        this.gc = gc;
        this.uiFont = uiFont;
    }

    public void render(List<Ball> balls, List<Paddle> paddles, List<Level> levels, List<PowerUp> powerUps) {
        clearScreen();
        renderLevel(levels);
        renderBall(balls);
        renderPaddle(paddles);
        renderPowerUps(powerUps);
    }

    private void clearScreen() {
        gc.setFill(GameConfig.BACKGROUND_COLOR);
        gc.fillRect(0, 0, GameConfig.CANVAS_WIDTH, GameConfig.CANVAS_HEIGHT);
    }

    private void renderBall(List<Ball> balls) {
        if (balls.isEmpty()) {
            return;
        }
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).render(gc);
        }
    }

    private void renderPaddle(List<Paddle> paddles) {
        if (paddles.isEmpty()) {
            return;
        }
        for (int i = 0; i < paddles.size(); i++) {
            paddles.get(i).render(gc);
        }
    }

    private void renderLevel(List<Level> levels) {
        if (levels.isEmpty()) {
            return;
        }
        levels.get(0).render(gc);
    }

    private void renderPowerUps(List<PowerUp> powerUps) {
        if (powerUps.isEmpty()) {
            return;
        }
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).render(gc);
        }
    }

    private void renderUI() {
        Font originalFont = gc.getFont();

        gc.setFont(uiFont);
        gc.setFill(GameConfig.UI_TEXT_COLOR);

    }
}
