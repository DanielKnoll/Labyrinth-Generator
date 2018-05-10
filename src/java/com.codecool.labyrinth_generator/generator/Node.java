package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private boolean isWall = true;
    private int[] coordinate = new int[2];
    private List<Node> neighbors = new ArrayList<>();
    private boolean unbreakable = false;

    public Node(int x, int y) {
        coordinate[0] = x;
        coordinate[1] = y;
    }

    public boolean isWall() {
        return isWall;
    }

    public void removeWall() {
        isWall = false;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void addNeighbor(Node tile) {
        neighbors.add(tile);
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable() {
        this.unbreakable = true;
    }

    @Override
    public String toString() {
        //return (isWall) ? "0" : "1";
        return Arrays.toString(coordinate);
    }
}
