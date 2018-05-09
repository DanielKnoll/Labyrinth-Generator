package com.codecool.labyrinth_generator.api;

import com.codecool.labyrinth_generator.Information.AlgorithmInfo;
import com.codecool.labyrinth_generator.generator.Labyrinth;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class CreateJson {

    public JSONObject getLabyrinthJson(Labyrinth labyrinth) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = createIntJsonArray(labyrinth.getMazeOrder());
        jsonObject.put("algoName", labyrinth.getAlgoName());
        jsonObject.put("mazeColNum", labyrinth.getMazeWidth());
        jsonObject.put("mazeRowNum", labyrinth.getMazeHeight());
        jsonObject.put("mazeOrder", jsonArray);
        return jsonObject;
    }

    public JSONObject getInfoJson(AlgorithmInfo algoInfo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", algoInfo.getName());
        jsonObject.put("apiValues", algoInfo.getDefaultApiLink());
        jsonObject.put("classNames", createStringJsonArray(algoInfo.getClassNames()));
        jsonObject.put("classCodes", createStringJsonArray(algoInfo.getClassCodes()));
        jsonObject.put("algoWikiInfo", algoInfo.getAlgoWikiInfo());
        jsonObject.put("imageNames", createStringJsonArray(algoInfo.getImageNames()));
        return jsonObject;
    }

    public JSONObject getSolutionJson(String maze) {
        List<String> hardcoded = Arrays.asList("0", "1", "2");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", "hardcoded");
        jsonObject.put("solutionOrder", createStringJsonArray(hardcoded));
        return jsonObject;
    }

    private JSONArray createIntJsonArray(List<Integer> mazeOrderArray) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(mazeOrderArray);
        return jsonArray;
    }

    private JSONArray createStringJsonArray(List<String> strings) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(strings);
        return jsonArray;
    }
}
