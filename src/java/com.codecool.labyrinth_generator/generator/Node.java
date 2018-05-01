package com.codecool.labyrinth_generator.generator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Node {
    private boolean isWall = true;
    private int[] place = new int[2];
    private Set<Node> neighbor = new HashSet<>();
    private boolean isVisited = false;  //TODO delete?

    public Node(int x, int y) {
        place[0] = x;
        place[1] = y;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public int[] getPlace() {
        return place;
    }

    public void addNeighbor(Node tile) {
        neighbor.add(tile);
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
