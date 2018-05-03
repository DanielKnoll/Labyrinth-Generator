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

        super.createGrid();
        Node startTile = super.randomStart();
        startTile.removeWall();
        stack.push(startTile);
        int[] startTileCoord = startTile.getPlace(); // TODO temporary
        mazeOrder.add(startTileCoord[0] * mazeWidth + startTileCoord[1]); // TODO change mazeOrder to Node
        generateLabyrinth(startTile);
    }

    public void generateLabyrinth(Node start) {
        Node currentTile = start;
        while(!stack.empty()) {
            List<Node> nextTiles = checkNeighbors(currentTile);
            if (nextTiles.size() > 0) {
                int num = rnd.nextInt(nextTiles.size());
                Node next = nextTiles.get(num);  // TODO one line
                next.removeWall();
                stack.push(next);
                int[] nextCoord = next.getPlace(); // TODO helper method for conversion (x * mazeWidth + y)
                mazeOrder.add(nextCoord[0] * mazeWidth + nextCoord[1]);
                currentTile = next;
            } else if (!stack.empty()) {
                currentTile = stack.pop();
            }
        }
    }

    /**
     * Checks 4 adjacent neighbor. 1 is only OK, if:
     * - It is a wall
     * - Not maze edge
     * - That neighbor has 8 neighbors and all of them are walls OR
     *      the neighbor is one of the top two element in the stack
     */
    private List<Node> checkNeighbors(Node node) {
        List<Node> result = new ArrayList<>();

        for (Node neighbour : getAdjacentNeighbours(node)) {
            if (neighbour.isWall() && // do not go back
                    !isEdge(neighbour) && // do not dig into edges
                    hasNoVisitedNearby(neighbour)) { // do not dig if there is a tunnel nearby
                result.add(neighbour);
            }
        }
        return result;
    }

    /**
     * Returns north, east, south, west neighbours if they exist
     */
    private List<Node> getAdjacentNeighbours(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[] nodeCoordinate = node.getPlace();
        int[][] adjacentDirections = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        for (int[] direction : adjacentDirections) {
            if (isCoordinateInBound(nodeCoordinate, direction)) {
                Node neighbor = allTiles.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean isCoordinateInBound(int[] nodeCoordinate, int[] direction) {
        return nodeCoordinate[0] + direction[0] >= 0 && nodeCoordinate[0] + direction[0] < mazeHeight &&
                nodeCoordinate[1] + direction[1] >= 0 && nodeCoordinate[1] + direction[1] < mazeWidth;
    }

    /**
     * Checks if there are no corridor tiles nearby
     * except for the current searches head (top 2 elements of the Stack)
     */
    private boolean hasNoVisitedNearby(Node node) {
        int[] nodeCoordinate = node.getPlace();
        int[][] allDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        for (int[] direction : allDirections) {
            Node neighbor = allTiles.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);
            if (!neighbor.isWall() && !(stack.search(neighbor) == 1 || stack.search(neighbor) == 2)) {
                return false;  // Todo bug
            }
        }
        return true;
    }

    /**
     * returns true if the node is on the edge
     */
    private boolean isEdge(Node node) {
        int[] nodeCoordinate = node.getPlace();
        if(nodeCoordinate[0] == 0 || nodeCoordinate[0] == mazeHeight - 1 ||
                nodeCoordinate[1] == 0 || nodeCoordinate[1] == mazeWidth - 1) {
            return true;
        }
        return false;
    }
}
