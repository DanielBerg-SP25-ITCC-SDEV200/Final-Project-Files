package com.example.snakegame;

import javafx.animation.AnimationTimer;

public class SnakeGame {
    private Snake snake;
    private Food food;
    private int score;
    private boolean gameOver;
    private boolean paused;
    private AnimationTimer gameLoop;

    public SnakeGame() {
        this.snake = new Snake();
        this.food = new Food(this.snake);
        this.score = 0;
        this.gameOver = false;
        this.paused = false;
    }

    // Reset the game state
    public void restartGame() {
        // Reset the snake
        this.snake = new Snake();  // Resets the snake to the initial state

        // Reset the food
        this.food = new Food(this.snake);  // Generates new food

        // Reset score
        this.score = 0;

        // Reset game-over state
        this.gameOver = false;

        // Reset paused state
        this.paused = false;
    }

    // Start a new game
    public void startGame() {
        this.snake.reset();
        this.food.generateNewFood(this.snake);
        this.score = 0;
        this.gameOver = false;
        this.paused = false;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!paused && !gameOver) {
                    update();
                }
            }
        };
        gameLoop.start();
    }

    // Pause the game
    public void pauseGame() {
        this.paused = !this.paused;
    }

    // End the game
    public void endGame() {
        this.gameOver = true;
        gameLoop.stop();
    }

    // Update game logic: move snake, check collisions, generate food
    public void update() {
        this.snake.move();
        if (this.snake.checkCollision()) {
            endGame();
        }
        if (this.snake.eatsFood(food)) {
            this.score++;
            this.snake.grow();
            food.generateNewFood(this.snake);
        }
    }

    public int getScore() {
        return score;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
