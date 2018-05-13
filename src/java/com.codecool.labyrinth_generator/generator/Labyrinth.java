package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Labyrinth {

    String algoName;
    int mazeWidth;
    int mazeHeight;
    List<Node> mazeOrder = new ArrayList<>();
    List<List<Node>> maze = new ArrayList<>();
    Random rnd = new Random();
    Node end = new Node(0,0); // Fail safe
    private boolean isEndTileFound = false;

    public String getAlgoName() {
        return algoName;
    }

    public List<Node> getMazeOrder() {
        return mazeOrder;
    }

    public int getMazeWidth() {
        return mazeWidth;
    }

    public int getMazeHeight() {
        return mazeHeight;
    }

    public List<List<Node>> getMaze() {
        return maze;
    }

    public Node getEnd() {
        return end;
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
    }

    Node randomStart() {
        int randomCol;
        int randomRow = rnd.nextInt(mazeHeight);

        if (randomRow == 0 || randomRow == mazeHeight - 1) {
            randomCol = rnd.nextInt(mazeWidth - 2) + 1;  //To avoid corners: between 1 and width-1
        } else {
            randomCol = (((int) (rnd.nextInt(1) + 0.5)) == 0) ? 0 : mazeWidth - 1;
        }
        Node start = maze.get(randomRow).get(randomCol);
        start.removeWall();
        mazeOrder.add(start);
        return start;
    }

    boolean isCoordinateInBound(int[] nodeCoordinate, int[] direction) {
        return nodeCoordinate[0] + direction[0] >= 0 && nodeCoordinate[0] + direction[0] < mazeHeight &&
                nodeCoordinate[1] + direction[1] >= 0 && nodeCoordinate[1] + direction[1] < mazeWidth;
    }

    /**
     * returns true if the node is on the edge
     */
    boolean isEdge(Node node) {
        int[] nodeCoordinate = node.getCoordinate();
        return nodeCoordinate[0] == 0 || nodeCoordinate[0] == mazeHeight - 1 ||
                nodeCoordinate[1] == 0 || nodeCoordinate[1] == mazeWidth - 1;
    }

    void setEndTile(Node start, Node curentTile) {  // TODO WET
        int[] startCoordinate = start.getCoordinate();
        int[] curentCoordintate = curentTile.getCoordinate();

        if(mazeOrder.size() > 1 && !isEndTileFound) {
            if (startCoordinate[0] == 0 && curentCoordintate[0] + 1 == mazeHeight - 1) {
                setEndToCorridor(maze.get(curentCoordintate[0] + 1).get(curentCoordintate[1]));
                isEndTileFound = true;
            } else if (startCoordinate[0] == mazeHeight && curentCoordintate[0] - 1 == 0) {
                setEndToCorridor(maze.get(curentCoordintate[0] - 1).get(curentCoordintate[1]));
                isEndTileFound = true;
            } else if (startCoordinate[1] == 0 && curentCoordintate[1] + 1 == mazeWidth - 1) {
                setEndToCorridor(maze.get(curentCoordintate[0]).get(curentCoordintate[1] + 1));
                isEndTileFound = true;
            } else if (startCoordinate[1] == mazeWidth && curentCoordintate[1] - 1 == 0) {
                setEndToCorridor(maze.get(curentCoordintate[0]).get(curentCoordintate[1] - 1));
                isEndTileFound = true;
            }
        }
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

    private void setEndToCorridor(Node tile) {
        tile.removeWall();
        mazeOrder.add(tile);
        end = tile;
    }

    private void addNeighbors(Node tile) {
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        Node neighbor;
        int[] tileCoordinate;

        for (int[] dir: dirs) {
            tileCoordinate = tile.getCoordinate();
            if(isCoordinateInBound(tileCoordinate, dir)) {

                neighbor = maze.get(tileCoordinate[0] + dir[0]).get(tileCoordinate[1] + dir[1]);
                tile.addNeighbor(neighbor);
            }
        }
    }
}
