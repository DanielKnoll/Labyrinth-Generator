package com.codecool.labyrinth_generator.factory;

import com.codecool.labyrinth_generator.Information.AlgorithmInfo;
import com.codecool.labyrinth_generator.Information.DfsInfo;
import com.codecool.labyrinth_generator.Information.KruskalInfo;
import com.codecool.labyrinth_generator.generator.Algorithms;
import com.codecool.labyrinth_generator.generator.Dfs;
import com.codecool.labyrinth_generator.generator.Kruskal;
import com.codecool.labyrinth_generator.generator.Labyrinth;

public class LabyrinthInfoFactory {

    public AlgorithmInfo getInfo(int type) {

        AlgorithmInfo algoinfo = null;
        LabyrinthGeneratorFactory factory = new LabyrinthGeneratorFactory();
        Algorithms algoType = factory.convertType(type);

        if (algoType != null) {
            switch (algoType) {
                case DFS:
                    algoinfo =  new DfsInfo();
                case KRUSKAL:
                    algoinfo = new KruskalInfo();
            default:
                algoinfo = null;
            }
        }
        return algoinfo;
    }

}
