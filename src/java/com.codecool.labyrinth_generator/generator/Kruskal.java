package com.codecool.labyrinth_generator.generator;

public class Kruskal extends Labyrinth{

    public Kruskal(int mazeWidth, int mazeHeight) {
        setAlgoName("Kruskal's algorithm");
        setMazeWidth(mazeWidth);
        setMazeHeight(mazeHeight);
        generateLabyrinth(mazeWidth, mazeHeight);
    }

    public void generateLabyrinth(int mazeWidth, int mazeHeight) {
        int[] mazeOrder = {96, 77, 58, 39, 20, 40, 60, 80, 100, 81, 62, 43, 24, 27, 28, 29, 49, 68, 87, 105, 104, 103, 83, 64, 45, 32, 33, 34, 35, 36, 53, 72, 91, 110, 134, 153, 173, 174, 175, 157, 138, 193, 212, 144, 143, 142, 141, 140, 159, 178, 197, 216, 217, 218, 219, 220, 179, 180, 146, 147, 148, 149, 150, 167, 186, 205, 224};
        setMazeOrder(mazeOrder);
    }
}
