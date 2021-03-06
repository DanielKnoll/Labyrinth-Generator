package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Dfs extends Labyrinth {
    private Stack<Node> stack = new Stack<>();
    boolean doubleStep = true;

    public Dfs(int mazeWidth, int mazeHeight) {
        algoName = "Depth-first search algorithm";
        this.mazeWidth = mazeWidth;
        this.mazeHeight = mazeHeight;

        super.createGrid();
        Node start = super.randomStart();
        stack.push(start);
        generateLabyrinth(start);
    }

    public void generateLabyrinth(Node start) {
        Node currentTile = start;
        while(!stack.empty()) {
            List<Node> nextTiles = checkNeighbors(currentTile);
            if (nextTiles.size() > 0) {
                Node next = getNext(nextTiles);
                next.removeWall();
                stack.push(next);
                mazeOrder.add(next);
                setEndTile(stack.get(0), next);
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
    private List<Node> checkNeighbors(Node current) {
        List<Node> result = new ArrayList<>();

        for (Node neighbour : getAdjacentNeighbours(current)) {
            if (neighbour.isWall() && // do not go back
                    !super.isEdge(neighbour) && // do not dig into edges
                    hasNoVisitedNearby(neighbour, current)) { // do not dig if there is a tunnel nearby
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
        int[][] adjacentDirections = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};  // Left, Top, Right, Bottom

        for (int[] direction : adjacentDirections) {
            if (super.isCoordinateInBound(nodeCoordinate, direction)) {
                neighbors.add(maze.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]));
            }
        }
        return neighbors;
    }


    /**
     * Checks if there are no corridor tiles nearby
     * except for the current searches head and or its neighbors
     */
    private boolean hasNoVisitedNearby(Node next, Node last) {
        int[] nextCoordinate = next.getCoordinate();
        int[][] allDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        List<Node> lastStepAndNeighbors = getAdjacentNeighbours(last);
        lastStepAndNeighbors.add(last);

        for (int[] direction : allDirections) {
            Node neighbor = maze.get(nextCoordinate[0] + direction[0]).get(nextCoordinate[1] + direction[1]);
            if (!neighbor.isWall() && !lastStepAndNeighbors.contains(neighbor)) {
                return false;
            }
        }
        return true;
    }

    private Node getNext(List<Node> nextTiles) {
        if(doubleStep) {
            doubleStep = false;
        } else {
            doubleStep = true;
            int[] lastStep = stack.peek().getCoordinate();
            int[] lastDirection = getLastDirection(lastStep);
            for (Node neighbor: nextTiles) {
                int[] coordinate = neighbor.getCoordinate();
                if(lastStep[0] + lastDirection[0] == coordinate[0] &&
                        lastStep[1] + lastDirection[1] == coordinate[1]){
                    return neighbor;
                }
            }
            /*if(isCoordinateInBound(lastStep, lastDirection)) {  // interesting result
                return maze.get(lastStep[0] + lastDirection[0]).get(lastStep[1] + lastDirection[1]);
            }*/
        }
        return nextTiles.get(rnd.nextInt(nextTiles.size()));
    }

    private int[] getLastDirection(int[] lastStep) {
        int[] beforeLastStep = mazeOrder.get(mazeOrder.size() - 2).getCoordinate();
        int[] direction = new int[2];
        direction[0] = lastStep[0] - beforeLastStep[0];
        direction[1] = lastStep[1] - beforeLastStep[1];
        return direction;
    }
}
