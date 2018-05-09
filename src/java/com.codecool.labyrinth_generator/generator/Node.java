package com.codecool.labyrinth_generator.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private boolean isWall = true;
    private int[] place = new int[2];
    private List<Node> neighbors = new ArrayList<>();
    private boolean isVisited = false;  //TODO delete?

    public Node(int x, int y) {
        place[0] = x;
        place[1] = y;
    }

    public boolean isWall() {
        return isWall;
    }

    public void removeWall() {
        isWall = false;
    }

    public int[] getPlace() {
        return place;
    }

    public List<Node> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Node tile) {
        neighbors.add(tile);
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @Override
    public String toString() {
        //return (isWall) ? "0" : "1";
        return Arrays.toString(place);
    }
}
