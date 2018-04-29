package com.codecool.labyrinth_generator.api;

import com.codecool.labyrinth_generator.generator.Labyrinth;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class CreateJson {

    public JSONObject getJson(Labyrinth labyrinth) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = createRatingJsonArray(labyrinth.getMazeOrder());
        jsonObject.put("algoName", labyrinth.getAlgoName());
        jsonObject.put("mazeColNum", labyrinth.getMazeWidth());
        jsonObject.put("mazeRowNum", labyrinth.getMazeHeight());
        jsonObject.put("mazeOrder", jsonArray);
        return jsonObject;
    }

    private JSONArray createRatingJsonArray(int[] mazeOrderArray) {
        JSONArray jsonArray = new JSONArray();
        for (int order : mazeOrderArray) {
            jsonArray.add(order);
        }
        return jsonArray;
    }
}
