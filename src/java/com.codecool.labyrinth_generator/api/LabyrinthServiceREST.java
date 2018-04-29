package com.codecool.labyrinth_generator.api;

import com.codecool.labyrinth_generator.factory.LabyrinthGeneratorFactory;
import com.codecool.labyrinth_generator.generator.Labyrinth;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LabyrinthServiceREST {

    @GetMapping(value = "/api/generate/{type}&{width}&{height}")
    public ResponseEntity getAllMazeDataGet(@PathVariable("type") String typeString, @PathVariable("width") String widthString,
                                         @PathVariable("height") String heightString) {
        return generateLabyrinth(typeString, widthString, heightString);
    }


    @PostMapping(value = "/api/generate")
    public ResponseEntity getAllMazeDataPost(@RequestBody Map<String, String> data) {
        String typeString = data.get("algoType").toString();
        String widthString = data.get("mazeColNum").toString();
        String heightString = data.get("mazeRowNum").toString();
        return generateLabyrinth(typeString, widthString, heightString);
    }

    private ResponseEntity generateLabyrinth(String typeString, String widthString, String heightString) {
        int type;
        int width;
        int height;
        try {
            type = Integer.parseInt(typeString);
            width = Integer.parseInt(widthString);
            height = Integer.parseInt(heightString);
        } catch (NumberFormatException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        LabyrinthGeneratorFactory labyrinthFactory = new LabyrinthGeneratorFactory();

        Labyrinth labyrinth = labyrinthFactory.generateLabyrinth(type,width,height);

        if(labyrinth != null) {
            CreateJson createJson = new CreateJson();
            JSONObject jsonObject = createJson.getJson(labyrinth);
            return new ResponseEntity(jsonObject, HttpStatus.OK);
        } else {
            return new ResponseEntity("Wrong input", HttpStatus.BAD_REQUEST);
        }
    }
}
