package com.codecool.labyrinth_generator.factory;

import com.codecool.labyrinth_generator.generator.Algorithms;
import com.codecool.labyrinth_generator.generator.Dfs;
import com.codecool.labyrinth_generator.generator.Kruskal;
import com.codecool.labyrinth_generator.generator.Labyrinth;
import com.codecool.labyrinth_generator.generator.MyAlgo;

public class LabyrinthGeneratorFactory {

    public Labyrinth generateLabyrinth(Algorithms algoType, int width, int height) {
        int minDimension = 3;
        int maxDimension = 50;

        Labyrinth labyrinth = null;

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

}
