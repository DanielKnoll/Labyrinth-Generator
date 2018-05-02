package com.codecool.labyrinth_generator.factory;

import com.codecool.labyrinth_generator.generator.Algorithms;
import com.codecool.labyrinth_generator.generator.Dfs;
import com.codecool.labyrinth_generator.generator.Kruskal;
import com.codecool.labyrinth_generator.generator.Labyrinth;
import com.codecool.labyrinth_generator.generator.MyAlgo;

public class LabyrinthGeneratorFactory {

    public Labyrinth generateLabyrinth(int type, int width, int height) {
        int minDimension = 3;
        int maxDimension = 50;

        Labyrinth labyrinth = null;
        Algorithms algoType = convertType(type);

        if (algoType != null && ( minDimension <= width && width <= maxDimension) &&
                ( minDimension <= height && height <= maxDimension)) {
            switch (algoType) {
                case DFS:
                    labyrinth = new Dfs(width, height);
                    break;
                case KRUSKAL:
                    labyrinth = new Kruskal(width, height);
                    break;
                case MYALGO:
                    labyrinth = new MyAlgo(width, height);
                    break;
            default:
                labyrinth =  null;
            }
        }
        return labyrinth;
    }

    public Algorithms convertType(int type) {
        switch (type) {
            case 0:
                return Algorithms.DFS;
            case 1:
                return Algorithms.KRUSKAL;
            case 2:
                return Algorithms.MYALGO;
            default:
                return null;
        }
    }
}
