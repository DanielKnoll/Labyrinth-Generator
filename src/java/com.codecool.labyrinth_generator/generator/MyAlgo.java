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

        super.createGrid();
        stack.push(start);
        createEdge();
        generateLabyrinth(start); //Hardcoded response.
        System.out.println();
    }

    public void generateLabyrinth(Node start){
        Node currentTile = start;
        Node nextTile;
        int[] nextCoordinate;  // Todo naming convention...
        List<int[]> unvisitedNeighbors = unvisitedNeighbors(currentTile);

        if (unvisitedNeighbors.size() > 0) {
            nextCoordinate = unvisitedNeighbors.get(rnd.nextInt(unvisitedNeighbors.size()));
            nextTile = maze.get(nextCoordinate[0]).get(nextCoordinate[1]);

            super.setMazeTileCorridor(nextCoordinate[0], nextCoordinate[1]);
            setEndTile(nextCoordinate);
            stack.push(nextTile);
            createCorridor(currentTile, nextTile);
            generateLabyrinth(nextTile);
        } else {
            if(!stack.empty()) {
                generateLabyrinth(stack.pop());
            }
        }
    }

    private List<int[]> unvisitedNeighbors(Node tile) {
        List<Node> allNeighbors = new ArrayList<>();
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int[] tilePlace;

        for (int[] dir: dirs) {
            tilePlace = tile.getCoordinate();
            // IndexOutOfBind protection
            if(tilePlace[0] + dir[0] >= 0 && tilePlace[0] + dir[0] < mazeHeight &&
                    tilePlace[1] + dir[1] >= 0 && tilePlace[1] + dir[1] < mazeWidth) {

                allNeighbors.add(maze.get(tilePlace[0] + dir[0]).get(tilePlace[1] + dir[1]));
            }
        }

        List<int[]> unvisitedNeighbors = new ArrayList<>();
        for (Node neighbor: allNeighbors) {
            if(!neighbor.isVisited()) {
                unvisitedNeighbors.add(neighbor.getCoordinate());
            }
        }
        return unvisitedNeighbors;
    }

    private void createCorridor(Node currentTile, Node nextTile) {
        int[] currentPlace = currentTile.getCoordinate();
        int[] nextPlace = nextTile.getCoordinate();
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
            maze.get(currentPlace[0] + dirs[i][0]).get(currentPlace[1] + dirs[i][1]).setVisited(true);
        }
    }

    private void setEndTile(int[] curentTile) {  // TODO WET
        int[] startTile = start.getCoordinate();

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

    private void createEdge() {
        for (int i = 0; i < mazeHeight; i++) {
            maze.add(new ArrayList<>());
            for (int j = 0; j < mazeWidth; j++) {
                Node tile = maze.get(i).get(j);
                if(i == 0 || i == mazeHeight -1 || j == 0 || j == mazeWidth - 1) {
                    tile.setVisited(true);
                }
            }
        }
    }
}
