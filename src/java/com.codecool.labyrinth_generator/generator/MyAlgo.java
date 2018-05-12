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
        generateLabyrinth(start);
    }

    public void generateLabyrinth(Node start){
        Node currentTile = start;
        while(!stack.empty()) {
            List<int[]> unvisitedNeighbors = unvisitedNeighbors(currentTile);
            if (unvisitedNeighbors.size() > 0) {
                int[] nextCoordinate = unvisitedNeighbors.get(rnd.nextInt(unvisitedNeighbors.size()));
                Node nextTile = maze.get(nextCoordinate[0]).get(nextCoordinate[1]);

                setMazeTileCorridor(nextCoordinate[0], nextCoordinate[1]);
                setEndTile(nextCoordinate);
                stack.push(nextTile);
                createCorridor(currentTile, nextTile);
                currentTile = nextTile;
            } else {
                if(!stack.empty()) {
                    currentTile = stack.pop();
                }
            }
        }
    }

    private List<int[]> unvisitedNeighbors(Node tile) {
        List<Node> adjacentNeighbors = new ArrayList<>();
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int[] tileCoordinate;

        for (int[] dir: dirs) {
            tileCoordinate = tile.getCoordinate();
            if(super.isCoordinateInBound(tileCoordinate, dir)) {
                adjacentNeighbors.add(maze.get(tileCoordinate[0] + dir[0]).get(tileCoordinate[1] + dir[1]));
            }
        }

        List<int[]> unvisitedNeighbors = new ArrayList<>();
        for (Node neighbor: adjacentNeighbors) {
            if(!neighbor.isUnbreakable() && !super.isEdge(neighbor)) {
                unvisitedNeighbors.add(neighbor.getCoordinate());
            }
        }
        return unvisitedNeighbors;
    }

    private void createCorridor(Node currentTile, Node nextTile) {
        int[] currentCoordinate = currentTile.getCoordinate();
        int[] nextCoordinate = nextTile.getCoordinate();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int startLoop;
        int endLoop;

        if(currentCoordinate[0] == nextCoordinate[0]) {
            startLoop = 0;
            endLoop = 2;
        } else {
            startLoop = 2;
            endLoop = 4;
        }

        for (int i = startLoop; i < endLoop; i++) {
            maze.get(currentCoordinate[0] + dirs[i][0]).get(currentCoordinate[1] + dirs[i][1]).setUnbreakable();
        }
    }

    private void setEndTile(int[] curentTile) {  // TODO WET
        int[] startCoordinate = start.getCoordinate();

        if(mazeOrder.size() > 1 && !isEndTileFound) {
            if (startCoordinate[0] == 0 && curentTile[0] + 1 == mazeHeight - 1) {
                setMazeTileCorridor(curentTile[0] + 1, curentTile[1]);
                isEndTileFound = true;
            } else if (startCoordinate[0] == mazeHeight && curentTile[0] - 1 == 0) {
                setMazeTileCorridor(curentTile[0] - 1, curentTile[1]);
                isEndTileFound = true;
            } else if (startCoordinate[1] == 0 && curentTile[1] + 1 == mazeWidth - 1) {
                setMazeTileCorridor(curentTile[0], curentTile[1] + 1);
                isEndTileFound = true;
            } else if (startCoordinate[1] == mazeWidth && curentTile[1] - 1 == 0) {
                setMazeTileCorridor(curentTile[0], curentTile[1] - 1);
                isEndTileFound = true;
            }
        }
    }

    void setMazeTileCorridor(int x, int y) {
        Node tile = maze.get(x).get(y);
        tile.removeWall();
        tile.setUnbreakable();
        mazeOrder.add(x * mazeWidth + y);
    }
}
