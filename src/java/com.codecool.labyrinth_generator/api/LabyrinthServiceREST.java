package com.codecool.labyrinth_generator.api;

import com.codecool.labyrinth_generator.factory.LabyrinthGeneratorFactory;
import com.codecool.labyrinth_generator.Information.AlgorithmInfo;
import com.codecool.labyrinth_generator.factory.LabyrinthInfoFactory;
import com.codecool.labyrinth_generator.generator.Algorithms;
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
        String typeString = data.get("algoType");
        String widthString = data.get("mazeColNum");
        String heightString = data.get("mazeRowNum");
        return generateLabyrinth(typeString, widthString, heightString);
    }

    @PostMapping(value = "/api/info")
    public ResponseEntity getAlgoInfoPost(@RequestBody Map<String, String> data) {
        int type;
        String typeString = data.get("algoType");
        try {
            type = Integer.parseInt(typeString);
        } catch (NumberFormatException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        LabyrinthInfoFactory factory = new LabyrinthInfoFactory();
        AlgorithmInfo algorithm = factory.getInfo(convertType(type));

        if(algorithm == null){
            return new ResponseEntity("Wrong type. (0-8)", HttpStatus.BAD_REQUEST);
        }

        CreateJson createJson = new CreateJson();
        JSONObject jsonObject = createJson.getInfoJson(algorithm);
        return new ResponseEntity(jsonObject, HttpStatus.OK);
    }

    @PostMapping(value = "/api/solve")
    public ResponseEntity getSolveMazePost(@RequestBody Map<String, String> data) {
        String typeString = data.get("maze");
        String widthString = data.get("start");
        String heightString = data.get("end");
        CreateJson createJson = new CreateJson();
        JSONObject jsonObject = createJson.getSolutionJson(typeString);
        return new ResponseEntity(jsonObject, HttpStatus.OK);
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

        Labyrinth labyrinth = labyrinthFactory.generateLabyrinth(convertType(type), width, height);

        if(labyrinth != null) {
            CreateJson createJson = new CreateJson();
            JSONObject jsonObject = createJson.getLabyrinthJson(labyrinth);
            return new ResponseEntity(jsonObject, HttpStatus.OK);
        } else {
            return new ResponseEntity("Wrong input", HttpStatus.BAD_REQUEST);
        }
    }

    public Algorithms convertType(int type) {
        switch (type) {
            case 0:
                return Algorithms.DFS;
            case 1:
                return Algorithms.KRUSKAL;
            case 2:
                return Algorithms.MYALGO;
            default:
                return null;
        }
    }
}
