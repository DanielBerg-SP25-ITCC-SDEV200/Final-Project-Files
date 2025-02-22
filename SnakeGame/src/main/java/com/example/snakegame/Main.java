package com.example.snakegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private SnakeGame game;
    private Canvas canvas;
    private VBox root;
    private VBox controlButtons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        game = new SnakeGame();
        canvas = new Canvas(400, 400);

        // Create the control buttons (Start, Pause, Reset)
        controlButtons = createControlButtons();

        // Create the layout and add canvas and control buttons
        root = new VBox();
        root.getChildren().add(canvas);
        root.getChildren().add(controlButtons);

        Scene scene = new Scene(root, 400, 450);

        // Ensure the root container captures key input by focusing on it
        scene.setOnKeyPressed(event -> handleKeyInput(event.getCode()));

        // Focus the root layout so it can receive keyboard input immediately
        root.setFocusTraversable(true);  // Focus the VBox root layout

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the game loop
        game.startGame();
        startGameLoop();
    }

    // Handle user input (arrow keys or WASD keys)
    private void handleKeyInput(KeyCode keyCode) {
        if (keyCode == KeyCode.UP) {
            game.getSnake().changeDirection(Direction.UP);
        } else if (keyCode == KeyCode.DOWN) {
            game.getSnake().changeDirection(Direction.DOWN);
        } else if (keyCode == KeyCode.LEFT) {
            game.getSnake().changeDirection(Direction.LEFT);
        } else if (keyCode == KeyCode.RIGHT) {
            game.getSnake().changeDirection(Direction.RIGHT);
        }
    }

    // Start the game loop
    private void startGameLoop() {
        javafx.animation.AnimationTimer gameLoop = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                {
                    game.update();
                    draw();
                }
            }
        };
        gameLoop.start();
    }

    // Draw the game on the canvas
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Setting the Snake color to Green
        gc.setFill(javafx.scene.paint.Color.GREEN);

        // Draw snake
        for (Segment segment : game.getSnake().getBody()) {
            gc.fillRect(segment.x * 20, segment.y * 20, 20, 20);
        }

        // Draw food
        gc.setFill(javafx.scene.paint.Color.RED);
        gc.fillRect(game.getFood().getX() * 20, game.getFood().getY() * 20, 20, 20);

        // Draw score
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("Score: " + game.getScore(), 10, 10);
    }

    // Create control buttons (Start, Pause, Reset)
    private VBox createControlButtons() {
        Button startButton = new Button("Start Game");
        startButton.setOnAction(event -> startGame());

        Button pauseButton = new Button("Pause Game");
        pauseButton.setOnAction(event -> game.pauseGame());

        Button resetButton = new Button("Restart Game");
        resetButton.setOnAction(event -> restartGame());

        VBox buttons = new VBox(10, startButton, pauseButton, resetButton);
        return buttons;
    }

    // Start the game by hiding the buttons
    private void startGame() {
        // Remove the control buttons from the layout
        root.getChildren().remove(controlButtons);

        // Focus the root layout again to ensure key events are captured
        root.requestFocus();

        // Start the game logic
        game.startGame();
    }

    // Restart the game by showing the buttons again (optional)
    private void restartGame() {
        // Remove the game canvas
        root.getChildren().clear();

        // Add control buttons back
        root.getChildren().add(controlButtons);

        // Optionally, reset the game state and start a new game
        game.restartGame();
    }
}

