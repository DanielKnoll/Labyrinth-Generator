package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
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
        stack.push(start);
        generateLabyrinth(start);
    }

    public void generateLabyrinth(Node start) {
        Node currentTile = start;
        while(!stack.empty()) {
            List<Node> nextTiles = checkNeighbors(currentTile);
            if (nextTiles.size() > 0) {
                Node next = nextTiles.get(rnd.nextInt(nextTiles.size()));
                next.removeWall();
                stack.push(next);
                int[] nextCoord = next.getCoordinate(); // TODO helper method for conversion (x * mazeWidth + y)
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
        int[] nodeCoordinate = node.getCoordinate();
        int[][] adjacentDirections = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};

        for (int[] direction : adjacentDirections) {
            if (isCoordinateInBound(nodeCoordinate, direction)) {
                Node neighbor = maze.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);
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
        int[] nodeCoordinate = node.getCoordinate();
        int[][] allDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        int corridorCounter = 0;

        for (int[] direction : allDirections) {
            Node neighbor = maze.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);
            if(!neighbor.isWall()) {corridorCounter++;}
            if (!neighbor.isWall() && !getLastStepAndNeighbors().contains(neighbor)) {
                return false;  // Todo bug
            }
        }
        if(corridorCounter > 2) return false;
        return true;
    }

    private List<Node> getLastStepAndNeighbors() {
        List<Node> neighbors = getAdjacentNeighbours(stack.peek());
        for (int i = 0; i < neighbors.size(); i++) {
            Node node = neighbors.get(i);
            if (node.isWall()) {
                neighbors.remove(node);
            }
        }
        neighbors.add(stack.peek());
        return neighbors;
    }

    /**
     * returns true if the node is on the edge
     */
    private boolean isEdge(Node node) {
        int[] nodeCoordinate = node.getCoordinate();
        if(nodeCoordinate[0] == 0 || nodeCoordinate[0] == mazeHeight - 1 ||
                nodeCoordinate[1] == 0 || nodeCoordinate[1] == mazeWidth - 1) {
            return true;
        }
        return false;
    }
}
