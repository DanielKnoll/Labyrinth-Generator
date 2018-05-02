package com.codecool.labyrinth_generator.generator;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Dfs extends Labyrinth {
    Stack<Node> stack = new Stack<>();

    public Dfs(int mazeWidth, int mazeHeight) {
        algoName = "Depth-first search algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;
        maze2D = new int[mazeHeight][mazeWidth];
        maze = new int[mazeHeight*mazeWidth];

        super.createGrid();
        super.createAllNeighbors();

        generateLabyrinth(super.randomStart()); //Hardcoded response.
    }


    public void generateLabyrinth(int[] start) {
        Node currentTile = allTiles.get(start[0]).get(start[1]);
        List<Node> neighbors = currentTile.getNeighbors();
        Node nextTile;
        int[] nextPlace;  // Todo naming convention...

        if (isThereUnvisitedNeighbor(currentTile)) {
            do {  // TODO overhead
                nextTile = neighbors.get(rnd.nextInt(neighbors.size()));
            } while (nextTile.isVisited());

            nextTile.setWall(false);
            nextTile.setVisited(true);
            nextPlace = nextTile.getPlace();
            setMazeValues(nextPlace[0], nextPlace[1]);
            stack.push(nextTile);
            createCorridor(currentTile, nextTile);
            generateLabyrinth(nextPlace);
        } else {
            if(!stack.empty()) {
                generateLabyrinth(stack.pop().getPlace());
            }
        }
    }

    private boolean isThereUnvisitedNeighbor(Node tile) {
        for (Node neighbor: tile.getNeighbors()) {
            if(!neighbor.isVisited()) {
                return true;
            }  // TODO return a list of unvisited neighbor places and random from those.
        }
        return false;
    }

    private void createCorridor(Node currentTile, Node nextTile) {
        int[] currentPlace = currentTile.getPlace();
        int[] nextPlace = nextTile.getPlace();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int startLoop = 0;
        int endLoop = 4;

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

    /*public void generateLabyrinth() {
        this.mazeOrder = Arrays.asList(166, 148, 147, 129, 111, 112, 94, 76, 58, 40, 41, 42, 43, 61, 79, 78, 96, 114, 132, 150, 151, 152, 153, 154, 136, 118, 119, 120, 121, 103, 85, 86, 68, 69, 70, 71, 146, 145, 127, 109, 91, 75, 74, 56, 55, 37, 19, 20, 21, 23, 25, 26, 27, 45, 63, 81, 99, 98, 116, 28, 82, 83, 65, 47, 48, 49, 31, 32, 33, 34, 155, 156, 157, 158, 159, 160, 142, 124, 106, 105);
    }*/
}
