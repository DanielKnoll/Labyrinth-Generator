package com.codecool.labyrinth_generator.api;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LabyrinthServiceREST {
    String name = "Depth-first search algorithm";
    int[] mazeOrder = {167, 149, 148, 130, 112, 113, 95, 77, 59, 41, 42, 43, 44, 62, 80, 79, 97, 115, 133, 151, 152, 153, 154, 155, 137, 119, 120, 121, 122, 104, 86, 87, 69, 70, 71, 72, 147, 146, 128, 110, 92, 76, 75, 57, 56, 38, 20, 21, 22, 24, 26, 27, 28, 46, 64, 82, 100, 99, 117, 29, 83, 84, 66, 48, 49, 50, 32, 33, 34, 35, 156, 157, 158, 159, 160, 161, 143, 125, 107, 106};

    @GetMapping(value = "/dfs")
    public ResponseEntity getAllMazeData() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = createRatingJsonArray(mazeOrder);
        jsonObject.put("algoName", name);
        jsonObject.put("mazeOrder", jsonArray);
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    private JSONArray createRatingJsonArray(int[] mazeOrderArray) {
        JSONArray jsonArray = new JSONArray();
        for (int order : mazeOrderArray) {
            jsonArray.add(order);
        }
        return jsonArray;
    }
}
