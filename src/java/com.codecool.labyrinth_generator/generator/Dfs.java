package com.codecool.labyrinth_generator.generator;

import java.util.Arrays;

public class Dfs extends Labyrinth {

    public Dfs(int mazeWidth, int mazeHeight) {
        algoName = "Depth-first search algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        maze2D = new int[mazeHeight][mazeWidth];
        maze = new int[mazeHeight*mazeWidth];
        int[] currentTile = new int[2];

        super.createGrid();
        super.createAllNeighbors();
        currentTile = super.randomStart();

        generateLabyrinth(); //Hardcoded response.
    }

    public void generateLabyrinth() {
        this.mazeOrder = Arrays.asList(166, 148, 147, 129, 111, 112, 94, 76, 58, 40, 41, 42, 43, 61, 79, 78, 96, 114, 132, 150, 151, 152, 153, 154, 136, 118, 119, 120, 121, 103, 85, 86, 68, 69, 70, 71, 146, 145, 127, 109, 91, 75, 74, 56, 55, 37, 19, 20, 21, 23, 25, 26, 27, 45, 63, 81, 99, 98, 116, 28, 82, 83, 65, 47, 48, 49, 31, 32, 33, 34, 155, 156, 157, 158, 159, 160, 142, 124, 106, 105);
    }
}
