package com.codecool.labyrinth_generator.generator;

public abstract class Labyrinth {

    String algoName;
    int[] maze;
    int[][] maze2D;
    int mazeWidth;
    int mazeHeight;
    int[] mazeOrder;
    int[][] getMazeOrder2D;

    public String getAlgoName() {
        return algoName;
    }

    public int[] getMazeOrder() {
        return mazeOrder;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public abstract void generateLabyrinth();
}
