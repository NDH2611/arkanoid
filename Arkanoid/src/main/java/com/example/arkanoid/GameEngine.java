package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GameEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;
    public static final double BALL_SPEED = 1.5;
    public static final double PADDLE_WIDTH = 75;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;
    private long startTime = 0;
    private AnimationTimer gameLoop;
    private Stage stage;
    private GameStateController troller;
    private boolean pPressed = false;
    private Font renderFont;

    private Paddle paddle;
    private ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<PowerUp> activePowerUps = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<String> mapFiles = new ArrayList<>();

    private int lives = 3;
    private int totalScores = 0;
    private int currentLevel = 1;

    public GameEngine(Stage stage) {
        this.stage = stage;
        this.setTotalScores(0);
        this.setLives(3);
        loadMap("totalMap.txt");
        initialize();
        //startGameLoop();
        troller = new GameStateController(stage, this);
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        renderFont = loadFont("PressStart2P-Regular.ttf", 16);
        Ball initialBall = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, BALL_SPEED);
        balls.add(initialBall);
        paddle = new Paddle(WIDTH / 2.0, HEIGHT - 25, PADDLE_WIDTH, 15, 0);
        createLevel();

        StackPane root = new StackPane(canvas);
        scene = new Scene(root, WIDTH, HEIGHT);

        scene.setOnKeyPressed(event -> handleKeyInput(event));
        scene.setOnKeyReleased(event -> {
            handleKeyInput(event);
            if (event.getCode() == KeyCode.P) {
                pPressed = false;
            }
        });

    }

    public void startGameLoop() {
        if (gameLoop != null) {
            return;
        }
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
                renderGame();
            }
        };
        gameLoop.start();
        System.out.println("game started");
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
            System.out.println("game stopped.");
        }
    }

    public void restartGame() {
        stopGameLoop();
        balls.clear();
        powerUps.clear();
        levels.clear();
        this.setTotalScores(0);
        this.setLives(3);
        this.currentLevel = 1;
        initialize();
        startGameLoop();
    }

    private Font loadFont(String fontName, int fontSize) {
        try {
            InputStream is = this.getClass().getResourceAsStream("font/" + fontName);
            if (is == null) {
                System.err.println("Font not found");
                return Font.font("Arial", fontSize);
            }
            System.out.println("Font loaded" + fontName);
            return Font.loadFont(is, fontSize);
        } catch (Exception e) {
            System.err.println("Error loading font");
            e.printStackTrace();
            return Font.font("Arial", fontSize);
        }
    }

    private void loadMap(String fileName) {
        try {
            InputStream is = getClass().getResourceAsStream("map/" + fileName);
            if (is == null) {
                System.err.println("File not found");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    mapFiles.add(line);
                    System.out.println("Found map: " + line);
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error loading Map");
            e.printStackTrace();
        }
    }

    private void createLevel() {
        if (mapFiles.isEmpty()) {
            System.err.println("No map files found");
            return;
        }
        int randomIndex = (int) (Math.random() * mapFiles.size());
        String fileName = mapFiles.get(randomIndex);
        System.out.println("Loading random map: " + fileName);
        Level level = new Level(fileName);
        levels.clear();
        levels.add(level);
    }

    private void updateLevel() {
        for (Ball ball : balls) {
            for (Brick brick : levels.get(0).getBricks()) {
                if (!brick.isVisible()) {
                    continue;
                }
                CheckCollision.CollisionSide side = CheckCollision.checkCollision(ball.getCircle(), brick.getRectangle());
                if (side != CheckCollision.CollisionSide.NONE) {
                    if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
                        ball.setDx(-ball.getDx());
                    } else if (side == CheckCollision.CollisionSide.TOP || side == CheckCollision.CollisionSide.BOTTOM) {
                        ball.setDy(-ball.getDy());
                    }
                    brick.setStrength(brick.getStrength() - 1);
                    if (brick.getStrength() <= 0) {
                        brick.setVisible(false);
                        totalScores += brick.getScore();
                        System.out.println(totalScores);

                        System.out.println(brick.getType());
                        if (brick.getType() == Brick.TYPE.BLUE) {
                            PowerUp powerUp = new DoubleBallPowerUp(
                                    brick.getX() + brick.getWidth() / 2 - 10,
                                    brick.getY());
                            powerUps.add(powerUp);
                        } else if (brick.getType() == Brick.TYPE.PINK) {
                            PowerUp powerUp = new HealthPowerUp(brick.getX() + brick.getWidth() / 2 - 10,
                                    brick.getY());
                            powerUps.add(powerUp);
                        } else if (Math.random() < 0.25) {
                            if (Math.random() < 0.5) {
                                powerUps.add(new ExpandPaddlePowerUp(brick.getX() + 5, brick.getY() + 5));
                            } else {
                                powerUps.add(new ShrinkPaddlePowerUp(brick.getX() + 5, brick.getY() + 5));
                            }
                        }
                    }

                    break;
                }
            }
        }

    }

    private void updatePowerUp() {
        for (int i = powerUps.size() - 1; i >= 0; i--) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.update();
            if (powerUp.getY() > HEIGHT) {
                powerUps.remove(i);
                continue;
            }

            if (CheckCollision.checkCollision(powerUp.getRectangle(), paddle.getRectangle())) {
                if (powerUp instanceof ExpandPaddlePowerUp) {
                    System.out.println("ExpandPaddlePowerUp");
                    ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUp;
                    exp.applyEffect(paddle);
                    activePowerUps.add(exp);
                    exp.setActive(true);
                } else if (powerUp instanceof ShrinkPaddlePowerUp) {
                    System.out.println("ShrinkPaddlePowerUp");
                    ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                    shr.applyEffect(paddle);
                    activePowerUps.add(shr);
                    shr.setActive(true);
                } else if (powerUp instanceof DoubleBallPowerUp) {
                    System.out.println("DoubleBallPowerUp");
                    DoubleBallPowerUp dbl = (DoubleBallPowerUp) powerUp;
                    if (!balls.isEmpty()) {
                        Ball newBall = dbl.applyEffect(balls.get(0));
                        balls.add(newBall);
                    }
                } else if (powerUp instanceof HealthPowerUp) {
                    System.out.println("HealthPowerUp");
                    if (lives < 3) {
                        lives++;
                    }
                }
                powerUps.remove(i);
            }
        }

        for (int i = activePowerUps.size() - 1; i >= 0; i--) {
            if (!activePowerUps.get(i).isActive()) {
                activePowerUps.remove(i);
            }
        }
    }

    private void renderPowerUp() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (powerUps.get(i) instanceof ExpandPaddlePowerUp) {
                ExpandPaddlePowerUp exp = (ExpandPaddlePowerUp) powerUps.get(i);
                exp.render(gc);
            } else if (powerUps.get(i) instanceof ShrinkPaddlePowerUp) {
                ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUps.get(i);
                shr.render(gc);
            } else if (powerUps.get(i) instanceof DoubleBallPowerUp) {
                DoubleBallPowerUp dbl = (DoubleBallPowerUp) powerUps.get(i);
                dbl.render(gc);
            } else if (powerUps.get(i) instanceof HealthPowerUp) {
                HealthPowerUp health = (HealthPowerUp) powerUps.get(i);
                health.render(gc);
            }
        }
    }

    public void updateGame() {

        if (troller != null && troller.getState() != GameState.RUNNING) {
            return;
        }
        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball currentBall = balls.get(i);
            currentBall.update();
            CheckCollision.CollisionSide side = CheckCollision.checkCollision(currentBall.getCircle(), paddle.getRectangle());
            if (side == CheckCollision.CollisionSide.TOP) {
                CheckCollision.caculatedBallBounceAngle(balls.get(i), paddle);
            } else if (side == CheckCollision.CollisionSide.LEFT || side == CheckCollision.CollisionSide.RIGHT) {
                currentBall.setDx(-currentBall.getDx());
            }
            if (currentBall.getY() > HEIGHT) {
                balls.remove(i);

            }
            if (balls.isEmpty()) {
                loseLife();

            }
            paddle.update();
            updatePowerUp();
        }
        updateLevel();
        if (checkLevelComplete()) {
            handleLevelComplete();
        }
        paddle.update();
        updatePowerUp();
    }

    public void renderGame() {
        gc.setFill(Color.rgb(46, 26, 71));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Ball ball : balls) {
            ball.render(gc);
        }
        paddle.render(gc);
        levels.get(0).render(gc);
        renderPowerUp();
        renderUI();
    }

    private void loseLife() {
        lives--;
        System.out.println("Life loss. Lives remained: " + lives);
        if (lives > 0) {
            resetBallAndPaddle();
            troller.setState(GameState.READY);
        } else {
            System.out.println("Game over");
            troller.setState(GameState.GAME_OVER);
        }
    }

    private void resetBallAndPaddle() {
        balls.clear();
        Ball newBall = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, BALL_SPEED);
        newBall.setX(WIDTH / 2.0);
        newBall.setY(HEIGHT / 2.0);
        balls.add(newBall);
        paddle = new Paddle(WIDTH / 2.0, HEIGHT - 25, PADDLE_WIDTH, 15, 0);
    }

    private void renderUI() {
        Font originalFont = gc.getFont();

        gc.setFont(renderFont);
        gc.setFill(Color.rgb(242, 226, 210));

        String scoreText = "Scores: " + String.valueOf(totalScores);
        Text scoreTextNode = new Text(scoreText);
        scoreTextNode.setFont(renderFont);
        double textWidth=scoreTextNode.getLayoutBounds().getWidth();
        double textHeight=scoreTextNode.getLayoutBounds().getHeight();
        double horizontalCenter=WIDTH/2.0-textWidth/2.0;
        gc.fillText(scoreText, horizontalCenter  , Level.getDistanceY()/2.0 + textHeight/2.0);

        String livesText = "Lives: " + String.valueOf(totalScores);
        Text livesTextNode = new Text(livesText);
        livesTextNode.setFont(renderFont);
        double livesTextWidth = livesTextNode.getLayoutBounds().getWidth();
        gc.fillText(livesText, 10, Level.getDistanceY()/2.0 + textHeight/2.0);

        String levelText = "Levels: " + String.valueOf(totalScores);
        Text levelTextNode = new Text(levelText);
        levelTextNode.setFont(renderFont);
        double levelTextWidth = levelTextNode.getLayoutBounds().getWidth();
        gc.fillText(levelText, WIDTH-150, Level.getDistanceY()/2.0 + textHeight/2.0);

        drawSeparatorLine();
        gc.setFont(originalFont);
    }

    private void drawSeparatorLine() {
        double lineY = Level.getDistanceY();
        gc.setStroke(Color.rgb(242, 226, 210));
        gc.setLineWidth(3);
        gc.strokeLine(0, lineY, WIDTH, lineY);
    }

    private boolean checkLevelComplete() {
        if (levels.isEmpty()) {
            return false;
        }
        for (Brick brick : levels.get(0).getBricks()) {
            if (brick.isVisible()) {
                return false;
            }
        }
        return true;
    }

    private void handleLevelComplete() {
        System.out.println("Level Complete");
        currentLevel++;
        totalScores += 100;
        loadNextLevel();
    }

    private void loadNextLevel() {
        balls.clear();
        powerUps.clear();
        activePowerUps.clear();
        createLevel();
        resetBallAndPaddle();
        troller.setState(GameState.READY);
    }

    public void handleKeyInput(KeyEvent event) {
        if (troller == null) {
            return;
        }
        if (troller.getState() == GameState.RUNNING) {
            paddle.handleInput(event);
        }
        switch (event.getCode()) {
            case P:
                if (!pPressed) {
                    pPressed = true;
                    if (troller.getState() == GameState.RUNNING) {
                        troller.setState(GameState.PAUSE);
                    } else if (troller.getState() == GameState.PAUSE) {
                        troller.setState(GameState.RUNNING);
                    }
                }
                break;

            case R:
                if (troller.getState() == GameState.GAME_OVER) {
                    restartGame();
                    troller.setState(GameState.RUNNING);
                }
                break;
            case SPACE:
                if (troller.getState() == GameState.READY) {
                    if (!balls.isEmpty()) {
                        balls.get(0).setDx(BALL_SPEED);
                        balls.get(0).setDy(BALL_SPEED);
                    }
                    troller.setState(GameState.RUNNING);
                }
                break;
        }
    }

    public Scene getScene() {
        return scene;
    }

    public GameStateController getTroller() {
        return troller;
    }

    public int getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(int totalScores) {
        this.totalScores = totalScores;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public void setActivePowerUps(ArrayList<PowerUp> activePowerUps) {
        this.activePowerUps = activePowerUps;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Font getRenderFont() {
        return renderFont;
    }

    public void setRenderFont(Font renderFont) {
        this.renderFont = renderFont;
    }
}
