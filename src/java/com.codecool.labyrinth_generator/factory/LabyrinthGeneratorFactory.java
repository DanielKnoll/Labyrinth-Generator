package com.codecool.labyrinth_generator.factory;

import com.codecool.labyrinth_generator.generator.Algorithms;
import com.codecool.labyrinth_generator.generator.Dfs;
import com.codecool.labyrinth_generator.generator.Kruskal;
import com.codecool.labyrinth_generator.generator.Labyrinth;

public class LabyrinthGeneratorFactory {

    public Labyrinth generateLabyrinth(int type, int width, int height) {
        int minDimension = 3;
        int maxDimension = 100;

        Labyrinth labyrinth = null;
        Algorithms algoType = convertType(type);

        if (algoType != null && ( minDimension <= width && width <= maxDimension) &&
                ( minDimension <= height && height <= maxDimension)) {
            switch (algoType) {
                case DFS:
                    return new Dfs(width, height);
                case KRUSKAL:
                    return new Kruskal(width, height);
            default:
                return null;
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
            default:
                return null;
        }
    }
}
