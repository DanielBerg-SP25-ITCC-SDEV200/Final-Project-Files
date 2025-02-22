package com.example.snakegame;

import java.util.LinkedList;

public class Snake {
    private LinkedList<Segment> body;
    private Direction direction;

    public Snake() {
        this.body = new LinkedList<>();
        this.direction = Direction.RIGHT;
        this.body.add(new Segment(5, 5));  // initial position of snake
    }

    public void move() {
        Segment head = body.getFirst();
        Segment newHead = new Segment(head.x, head.y);

        switch (direction) {
            case UP: newHead.y--; break;
            case DOWN: newHead.y++; break;
            case LEFT: newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        body.addFirst(newHead);  // add new head
        body.removeLast();  // remove tail
    }

    public void grow() {
        // Add a new segment at the tail to grow the snake
        Segment tail = body.getLast();
        body.addLast(new Segment(tail.x, tail.y));
    }

    public boolean checkCollision() {
        Segment head = body.getFirst();
        // Check if snake hits the wall or itself
        return head.x < 0 || head.x >= 20 || head.y < 0 || head.y >= 20 || body.contains(head);
    }

    public boolean eatsFood(Food food) {
        Segment head = body.getFirst();
        return head.x == food.getX() && head.y == food.getY();
    }

    public void reset() {
        body.clear();
        body.add(new Segment(5, 5));
        direction = Direction.RIGHT;
    }

    public void changeDirection(Direction newDirection) {
        // Prevent snake from turning 180 degrees
        if (this.direction == Direction.UP && newDirection != Direction.DOWN) {
            this.direction = newDirection;
        }
        if (this.direction == Direction.DOWN && newDirection != Direction.UP) {
            this.direction = newDirection;
        }
        if (this.direction == Direction.LEFT && newDirection != Direction.RIGHT) {
            this.direction = newDirection;
        }
        if (this.direction == Direction.RIGHT && newDirection != Direction.LEFT) {
            this.direction = newDirection;
        }
    }

    public LinkedList<Segment> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }
}

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

class Segment {
    int x, y;

    public Segment(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

