package com.codecool.labyrinth_generator.api;

import com.codecool.labyrinth_generator.Information.AlgorithmInfo;
import com.codecool.labyrinth_generator.generator.Labyrinth;
import com.codecool.labyrinth_generator.generator.Node;
import net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateJson {

    public JSONObject getLabyrinthJson(Labyrinth labyrinth) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", labyrinth.getAlgoName());
        jsonObject.put("mazeColNum", labyrinth.getMazeWidth());
        jsonObject.put("mazeRowNum", labyrinth.getMazeHeight());
        jsonObject.put("start", labyrinth.getMazeOrder().get(0).getCoordinate());
        jsonObject.put("mazeOrder", getMazeOrder(labyrinth.getMazeOrder(), labyrinth.getMazeWidth()));
        //jsonObject.put("mazeOrder2D", getMazeOrder2D(labyrinth.getMazeOrder(), labyrinth.getMazeWidth()));
        return jsonObject;
    }

    public JSONObject getInfoJson(AlgorithmInfo algoInfo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", algoInfo.getName());
        jsonObject.put("apiValues", algoInfo.getDefaultApiLink());
        jsonObject.put("classNames", algoInfo.getClassNames());
        jsonObject.put("classCodes", algoInfo.getClassCodes());
        jsonObject.put("algoWikiInfo", algoInfo.getAlgoWikiInfo());
        jsonObject.put("imageNames", algoInfo.getImageNames());
        return jsonObject;
    }

    public JSONObject getSolutionJson(String maze) {
        List<String> hardcoded = Arrays.asList("0", "1", "2");  // TODO delete when algo done
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", "hardcoded");
        jsonObject.put("solutionOrder", hardcoded);
        return jsonObject;
    }

    private List<Integer> getMazeOrder(List<Node> mazeOrder, int mazeWidth) {
        List<Integer> result = new ArrayList<>();

            for (Node node : mazeOrder) {
                int[] coordinate = node.getCoordinate();
                result.add((coordinate[0] * mazeWidth + coordinate[1]));
            }
        return result;
    }
}
