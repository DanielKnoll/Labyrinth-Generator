package com.codecool.labyrinth_generator.generator;

import net.minidev.json.JSONArray;

public abstract class Labyrinth {

    private String algoName;
    private int[] maze;
    private int[][] maze2D;
    private int mazeWidth;
    private int mazeHeight;
    private int[] mazeOrder;
    private int[][] getMazeOrder2D;

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public int[] getMazeOrder() {
        return mazeOrder;
    }

    public void setMazeOrder(int[] mazeOrder) {
        this.mazeOrder = mazeOrder;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public void setMazeWidth(int mazeWidth) {
        this.mazeWidth = mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public void setMazeHeight(int mazeHeight) {
        this.mazeHeight = mazeHeight;
    }

    public abstract void generateLabyrinth(int mazeWidth, int mazeHeight);



}
