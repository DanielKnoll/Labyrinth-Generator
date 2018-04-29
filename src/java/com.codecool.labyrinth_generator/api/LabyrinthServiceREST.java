package com.codecool.labyrinth_generator.api;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LabyrinthServiceREST {
    String name = "Depth-first search algorithm";
    int[] mazeOrder = {166, 148, 147, 129, 111, 112, 94, 76, 58, 40, 41, 42, 43, 61, 79, 78, 96, 114, 132, 150, 151, 152, 153, 154, 136, 118, 119, 120, 121, 103, 85, 86, 68, 69, 70, 71, 146, 145, 127, 109, 91, 75, 74, 56, 55, 37, 19, 20, 21, 23, 25, 26, 27, 45, 63, 81, 99, 98, 116, 28, 82, 83, 65, 47, 48, 49, 31, 32, 33, 34, 155, 156, 157, 158, 159, 160, 142, 124, 106, 105};

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
