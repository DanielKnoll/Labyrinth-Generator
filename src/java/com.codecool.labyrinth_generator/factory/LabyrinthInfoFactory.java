package com.codecool.labyrinth_generator.factory;

import com.codecool.labyrinth_generator.Information.AlgorithmInfo;
import com.codecool.labyrinth_generator.Information.DfsInfo;
import com.codecool.labyrinth_generator.Information.KruskalInfo;
import com.codecool.labyrinth_generator.Information.MyAlgoInfo;
import com.codecool.labyrinth_generator.generator.Algorithms;
import com.codecool.labyrinth_generator.generator.Dfs;
import com.codecool.labyrinth_generator.generator.Kruskal;
import com.codecool.labyrinth_generator.generator.Labyrinth;

public class LabyrinthInfoFactory {

    public AlgorithmInfo getInfo(Algorithms algoType) {

        AlgorithmInfo algoinfo = null;

        if (algoType != null) {
            switch (algoType) {
                case DFS:
                    algoinfo =  new DfsInfo();
                    break;
                case KRUSKAL:
                    algoinfo = new KruskalInfo();
                    break;
                case MYALGO:
                    algoinfo = new MyAlgoInfo();
                    break;
            default:
                algoinfo = null;
            }
        }
        return algoinfo;
    }

}
