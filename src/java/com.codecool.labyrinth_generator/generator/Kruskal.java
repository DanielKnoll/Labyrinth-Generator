package com.codecool.labyrinth_generator.generator;

import java.util.Arrays;

public class Kruskal extends Labyrinth{

    public Kruskal(int mazeWidth, int mazeHeight) {
        algoName = "Kruskal's algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        generateLabyrinth(new Node(0,0));
    }

    public void generateLabyrinth(Node start) {
    }
}
