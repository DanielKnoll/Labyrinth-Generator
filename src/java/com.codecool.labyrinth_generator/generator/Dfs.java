package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Dfs extends Labyrinth {
    private Stack<Node> stack = new Stack<>();
    private boolean isEndTileFound = false;

    public Dfs(int mazeWidth, int mazeHeight) {
        algoName = "Depth-first search algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        maze2D = new int[mazeHeight][mazeWidth];
        maze = new int[mazeHeight*mazeWidth];

        super.createGrid();
        super.createAllNeighbors();
        int[]startTile = super.randomStart();
    }


    public void generateLabyrinth(int[] start) {

    }

}
