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
        int[] mazeStart2D = labyrinth.getMazeOrder().get(0).getCoordinate();
        int mazeWidth = labyrinth.getMazeWidth();
        int mazeStart = mazeStart2D[0] * mazeWidth + mazeStart2D[1];  // x * width + y = 1D array
        List<int[]> mazeOrder2D = getMazeOrder2D(labyrinth.getMazeOrder());
        List<Integer> mazeOrder = getMazeOrder(mazeOrder2D, mazeWidth);
        List<List<Short>> maze2D = getMaze2D(labyrinth.getMaze());
        List<Short> maze = getMaze(maze2D);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("algoName", labyrinth.getAlgoName());
        jsonObject.put("mazeColNum", mazeWidth);
        jsonObject.put("mazeRowNum", labyrinth.getMazeHeight());
        jsonObject.put("maze", maze);
        jsonObject.put("start", mazeStart);
        jsonObject.put("end", "coming soon");
        jsonObject.put("mazeOrder", mazeOrder);
        jsonObject.put("maze2D", maze2D);
        jsonObject.put("start2D", mazeStart2D);
        jsonObject.put("end2D", "coming soon");
        jsonObject.put("mazeOrder2D", mazeOrder2D);
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

    private List<Integer> getMazeOrder(List<int[]> mazeOrder, int mazeWidth) {
        List<Integer> result = new ArrayList<>();
            for (int[] coordinate : mazeOrder) {
                result.add(coordinate[0] * mazeWidth + coordinate[1]);  // x * width + y = 1D array
            }
        return result;
    }

    private List<int[]> getMazeOrder2D(List<Node> mazeOrder) {
        List<int[]> result = new ArrayList<>();

        for (Node node : mazeOrder) {
            result.add(node.getCoordinate());
        }
        return result;
    }

    private List<Short> getMaze(List<List<Short>> maze2D) {
        List<Short> result = new ArrayList<>();

        for (List<Short> row : maze2D) {
            result.addAll(row);
        }
        return result;
    }

    private List<List<Short>> getMaze2D(List<List<Node>> maze) {
        List<List<Short>> result = new ArrayList<>();

        for (int i = 0; i < maze.size(); i++) {
            result.add(new ArrayList<>());
            for (Node node : maze.get(i)) {
                result.get(i).add((short) (node.isWall() ? 0 : 1));
            }
        }
        return result;
    }

}
