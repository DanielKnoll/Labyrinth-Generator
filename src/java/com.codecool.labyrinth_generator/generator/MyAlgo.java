package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyAlgo extends Labyrinth {
    private Stack<Node> stack = new Stack<>();
    private boolean isEndTileFound = false;

    public MyAlgo(int mazeWidth, int mazeHeight) {
        algoName = "My algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        maze2D = new int[mazeHeight][mazeWidth];
        maze = new int[mazeHeight*mazeWidth];

        super.createGrid();
        //super.createAllNeighbors();

        generateLabyrinth(super.randomStart()); //Hardcoded response.
    }


    public void generateLabyrinth(int[] start) {
        Node currentTile = allTiles.get(start[0]).get(start[1]);
        Node nextTile;
        int[] nextPlace;  // Todo naming convention...
        List<int[]> unvisitedNeighbors = unvisitedNeighbors(currentTile);

        if (unvisitedNeighbors.size() > 0) {
            nextPlace = unvisitedNeighbors.get(rnd.nextInt(unvisitedNeighbors.size()));
            nextTile = allTiles.get(nextPlace[0]).get(nextPlace[1]);

            super.setMazeTileCorridor(nextPlace[0], nextPlace[1]);
            setEndTile(nextPlace[0], nextPlace[1]);
            stack.push(nextTile);
            createCorridor(currentTile, nextTile);
            generateLabyrinth(nextPlace);
        } else {
            if(!stack.empty()) {
                generateLabyrinth(stack.pop().getPlace());
            }
        }
    }

    private List<int[]> unvisitedNeighbors(Node tile) {
        List<Node> allNeighbors = new ArrayList<>();
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int[] tilePlace;

        for (int[] dir: dirs) {
            tilePlace = tile.getPlace();
            // IndexOutOfBind protection
            if(tilePlace[0] + dir[0] >= 0 && tilePlace[0] + dir[0] < mazeHeight &&
                    tilePlace[1] + dir[1] >= 0 && tilePlace[1] + dir[1] < mazeWidth) {

                allNeighbors.add(allTiles.get(tilePlace[0] + dir[0]).get(tilePlace[1] + dir[1]));
            }
        }

        List<int[]> unvisitedNeighbors = new ArrayList<>();
        for (Node neighbor: allNeighbors) {
            if(!neighbor.isVisited()) {
                unvisitedNeighbors.add(neighbor.getPlace());
            }
        }
        return unvisitedNeighbors;
    }

    private void createCorridor(Node currentTile, Node nextTile) {
        int[] currentPlace = currentTile.getPlace();
        int[] nextPlace = nextTile.getPlace();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int startLoop;
        int endLoop;

        if(currentPlace[0] == nextPlace[0]) {
            startLoop = 0;
            endLoop = 2;
        } else {
            startLoop = 2;
            endLoop = 4;
        }

        for (int i = startLoop; i < endLoop; i++) {
            allTiles.get(currentPlace[0] + dirs[i][0]).get(currentPlace[1] + dirs[i][1]).setVisited(true);
        }
    }

    private void setEndTile(int x, int y) {  // TODO WET
        int[] curentTile = new int[]{x, y};
        int[] startTile = mazeOrder2D.get(0);

        if(mazeOrder.size() > 1 && !isEndTileFound) {
            if (startTile[0] == 0 && curentTile[0] + 1 == mazeHeight - 1) {
                super.setMazeTileCorridor(curentTile[0] + 1, curentTile[1]);
                isEndTileFound = true;
            } else if (startTile[0] == mazeHeight && curentTile[0] - 1 == 0) {
                super.setMazeTileCorridor(curentTile[0] - 1, curentTile[1]);
                isEndTileFound = true;
            } else if (startTile[1] == 0 && curentTile[1] + 1 == mazeWidth - 1) {
                super.setMazeTileCorridor(curentTile[0], curentTile[1] + 1);
                isEndTileFound = true;
            } else if (startTile[1] == mazeWidth && curentTile[1] - 1 == 0) {
                super.setMazeTileCorridor(curentTile[0], curentTile[1] - 1);
                isEndTileFound = true;
            }
        }
    }
}
