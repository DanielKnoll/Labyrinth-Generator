package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Labyrinth {

    String algoName;
    int mazeWidth;
    int mazeHeight;
    List<Integer> mazeOrder = new ArrayList<>();  //TODO Node
    List<List<Node>> maze = new ArrayList<>();
    Random rnd = new Random();
    Node start;

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

    public abstract void generateLabyrinth(Node start);

    void createGrid() {
        maze.clear();
        for (int i = 0; i < mazeHeight; i++) {
            maze.add(new ArrayList<>());
            for (int j = 0; j < mazeWidth; j++) {
                Node tile = new Node(i, j);
                maze.get(i).add(tile);
            }
        }
        randomStart();
    }

    void createAllNeighbors() {
        Node tile;
        for (int i = 0; i < maze.size(); i++) {
            for (int j = 0; j < maze.get(i).size(); j++) {
                tile = maze.get(i).get(j);
                addNeighbors(tile);
            }
        }
    }

    private void addNeighbors(Node tile) {
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        Node neighbor;
        int[] tilePlace;

        for (int[] dir: dirs) {
            tilePlace = tile.getCoordinate();
            // IndexOutOfBind protection
            if(tilePlace[0] + dir[0] >= 0 && tilePlace[0] + dir[0] < mazeHeight &&
                    tilePlace[1] + dir[1] >= 0 && tilePlace[1] + dir[1] < mazeWidth) {

                neighbor = maze.get(tilePlace[0] + dir[0]).get(tilePlace[1] + dir[1]);
                tile.addNeighbor(neighbor);
            }
        }
    }

    private void randomStart() {
        int randomCol;
        int randomRow = rnd.nextInt(mazeHeight);

        if (randomRow == 0 || randomRow == mazeHeight - 1) {
            randomCol = rnd.nextInt(mazeWidth - 2) + 1;  //To avoid corners: between 1 and width-1
        } else {
            randomCol = (((int) (rnd.nextInt(1) + 0.5)) == 0) ? 0 : mazeWidth - 1;
        }
        start = maze.get(randomRow).get(randomCol);
        start.removeWall();
        int[] startTileCoord = start.getCoordinate(); // TODO temporary
        mazeOrder.add(startTileCoord[0] * mazeWidth + startTileCoord[1]); // TODO change mazeOrder to Node
    }

    void setMazeTileCorridor(int x, int y) {
        Node tile = maze.get(x).get(y);
        tile.removeWall();
        tile.setVisited(true);
        mazeOrder.add(x * mazeWidth + y);
    }
}
