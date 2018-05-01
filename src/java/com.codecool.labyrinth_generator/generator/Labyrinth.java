package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Labyrinth {

    String algoName;
    int[] maze;
    int[][] maze2D;
    int mazeWidth;
    int mazeHeight;
    List<Integer> mazeOrder = new ArrayList<>();
    List<int[]> mazeOrder2D = new ArrayList<>();
    List<List<Node>> allTiles = new ArrayList<>();
    Random rnd = new Random();

    public String getAlgoName() {
        return algoName;
    }

    public List<Integer> getMazeOrder() {
        return mazeOrder;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public abstract void generateLabyrinth();

    void createGrid() {
        for (int i = 0; i < mazeHeight; i++) {
            allTiles.add(new ArrayList<>());
            for (int j = 0; j < mazeWidth; j++) {
                Node tile = new Node(i, j);
                if(i == 0 || i == mazeHeight -1 || j == 0 || j == mazeWidth - 1) {
                    tile.setVisited(true);
                }
                allTiles.get(i).add(tile);
            }
        }
    }

    void createAllNeighbors() {
        Node tile;
        for (int i = 0; i < allTiles.size(); i++) {
            for (int j = 0; j < allTiles.get(i).size(); j++) {
                tile = allTiles.get(i).get(j);
                addNeighbors(tile);
            }
        }
    }

    private void addNeighbors(Node tile) {
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        Node neighbor;
        int[] tilePlace;

        for (int[] dir: dirs) {
            tilePlace = tile.getPlace();
            // IndexOutOfBind protection
            if(tilePlace[0] + dir[0] >= 0 && tilePlace[0] + dir[0] < mazeHeight &&
                    tilePlace[1] + dir[1] >= 0 && tilePlace[1] + dir[1] < mazeWidth) {

                neighbor = allTiles.get(tilePlace[0] + dir[0]).get(tilePlace[1] + dir[1]);
                tile.addNeighbor(neighbor);
            }
        }
    }

    void randomStart() {
        int randomCol;
        int randomRow = rnd.nextInt(mazeHeight);

        if(randomRow == 0 || randomRow == mazeHeight -1) {
            randomCol = rnd.nextInt(mazeWidth - 2) + 1;  //To avoid corners -> between 1 and width - 1
        } else {
            randomCol = (((int)(rnd.nextInt(1) + 0.5)) == 0) ? 0 : mazeWidth - 1;
        }

        Node tile = allTiles.get(randomRow).get(randomCol);
        tile.setWall(false);
    }
}
