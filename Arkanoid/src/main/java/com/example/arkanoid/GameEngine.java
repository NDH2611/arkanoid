package com.example.arkanoid;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;


public class GameEngine {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final double BALL_SPEED = 3.0;
    public static final double PADDLE_WIDTH = 75;

    private Canvas canvas;
    private GraphicsContext gc;
    private Scene scene;
    private long startTime = 0;
    private AnimationTimer gameLoop;
    private Stage stage;
    private GameStateController troller;
    private boolean pPressed = false;
    //private Ball ball;
    private Paddle paddle;
    private ArrayList<Level> levels = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Ball> balls = new ArrayList<>();
    private ArrayList<String> mapFiles = new ArrayList<>();

    public GameEngine(Stage stage) {
        this.stage = stage;
        loadMap("totalMap.txt");
        initialize();
        //startGameLoop();
        troller = new GameStateController(stage, this);
    }

    public void initialize() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Ball initialBall = new Ball(WIDTH / 2.0, HEIGHT / 2.0, 10, BALL_SPEED);
        balls.add(initialBall);
        paddle = new Paddle(WIDTH / 2.0, HEIGHT - 25, PADDLE_WIDTH, 25, 0);
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
        //scene.setOnKeyReleased(event -> handleKeyInput(event));


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
        initialize();
        startGameLoop();
    }

    private void loadMap(String fileName){
        try{
            InputStream is = getClass().getResourceAsStream("map/"+fileName);
            if(is == null){
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
                        System.out.println(brick.getType());
                        if (brick.getType() == Brick.TYPE.PURPLE) {
                            System.out.println("PURPLE destroyed");
                            PowerUp powerUp = new DoubleBallPowerUp(
                                    brick.getX() + brick.getWidth() / 2 - 10,
                                    brick.getY());
                            powerUps.add(powerUp);
                        }
                        if (Math.random() < 0.33) {
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
//                    System.out.println(System.currentTimeMillis() - startTime);
//                    if(System.currentTimeMillis() - startTime > exp.getDuration()) {
//                        exp.removeEffect(paddle);
//                    }
                } else if (powerUp instanceof ShrinkPaddlePowerUp) {
                    System.out.println("ShrinkPaddlePowerUp");
                    ShrinkPaddlePowerUp shr = (ShrinkPaddlePowerUp) powerUp;
                    shr.applyEffect(paddle);
                } else if (powerUp instanceof DoubleBallPowerUp) {
                    System.out.println("DoubleBallPowerUp");
                    DoubleBallPowerUp dbl = (DoubleBallPowerUp) powerUp;
                    if (!balls.isEmpty()) {
                        Ball newBall = dbl.applyEffect(balls.get(0));
                        balls.add(newBall);
                    }
                }
                powerUps.remove(i);
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
                System.out.println("game over");

            }
            updateLevel();
            paddle.update();
            updatePowerUp();
        }
    }

    public void renderGame() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Ball ball : balls) {
            ball.render(gc);
        }
        paddle.render(gc);
        levels.get(0).render(gc);
        renderPowerUp();
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
                    } else if (troller.getState() == GameState.PAUSE){
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
        }
    }

    public Scene getScene() {
        return scene;
    }
}
