package com.codecool.labyrinth_generator.Information;

import java.util.ArrayList;
import java.util.List;

public class DfsInfo extends AlgorithmInfo {
    private List<String> classNames = new ArrayList<String>();
    private List<String> classCodes = new ArrayList<String>();
    private String algoWikiInfo;
    private List<String> imageNames = new ArrayList<String>();

    public DfsInfo() {
        fillInfo();
        setName("Depth-first search algorithm");
        setDefaultApiLink("wall=0&amp;algo=0&amp;width=19&amp;height=13");
        setClassNames(classNames);
        setClassCodes(classCodes);
        setAlgoWikiInfo(algoWikiInfo);
        setImageNames(imageNames);

    }

    private void fillInfo() {
        classNames.add("Node Class");
        classCodes.add(
                "    public class Node {\n" +
                "        private boolean isWall = true;\n" +
                "        private int[] coordinate = new int[2];\n\n" +
                "        public Node(int x, int y) {\n" +
                "            coordinate[0] = x;\n" +
                "            coordinate[1] = y;\n" +
                "        }\n\n" +
                "        public boolean isWall() {\n" +
                "            return isWall;\n" +
                "        }\n\n" +
                "        public void removeWall() {\n" +
                "            isWall = false;\n" +
                "        }\n\n" +
                "        public int[] getCoordinate() {\n" +
                "            return coordinate;\n" +
                "        }\n\n" +
                "        @Override\n" +
                "        public String toString() {\n" +
                "            return Arrays.toString(coordinate);\n" +
                "        }\n" +
                "    }\n");
        classNames.add("Dfs Class");
        classCodes.add(
                "    import java.util.*;\n" +
                "\n" +
                "    public class Dfs {\n" +
                "        List<Node> mazeOrder = new ArrayList<>();\n" +
                "        Stack<Node> stack = new Stack<>();\n" +
                "        private int mazeWidth;\n" +
                "        private int mazeHeight;\n" +
                "        private List<List<Node>> maze = new ArrayList<>();\n" +
                "        private Random rnd = new Random(12345);\n" +
                "        Node end = new Node(0,0); // Fail safe\n" +
                "        private boolean isEndTileFound = false;\n" +
                "        boolean doubleStep = true;\n\n" +
                "\n" +
                "        public Dfs(int width, int height) {\n" +
                "            mazeHeight = height;\n" +
                "            mazeWidth = width;\n" +
                "\n" +
                "            createGrid();\n" +
                "            printMaze();\n" +
                "            Node startTile = randomStart();\n" +
                "            startTile.removeWall();\n" +
                "            stack.push(startTile);\n" +
                "            generateLabyrinth(startTile);\n" +
                "\n" +
                "            printMaze();\n" +
                "\n" +
                "            System.out.println(\"\\nmazeorder: \" + mazeOrder);\n" +
                "            System.out.println(\"\\ndone\");\n" +
                "        }\n" +
                "\n" +
                "        public void generateLabyrinth(Node start) {\n" +
                "            Node currentTile = start;\n" +
                "            while(!stack.empty()) {\n" +
                "                List<Node> nextTiles = checkNeighbors(currentTile);\n" +
                "                if (nextTiles.size() > 0) {\n" +
                "                    Node next = getNext(nextTiles);\n" +
                "                    next.removeWall();\n" +
                "                    stack.push(next);\n" +
                "                    mazeOrder.add(next);\n" +
                "                    setEndTile(stack.get(0), next);\n" +
                "                    currentTile = next;\n" +
                "                    printMaze();\n" +
                "                    if(mazeOrder.size() == 8) {\n" +
                "                        System.out.println(\"now\");\n" +
                "                    }\n" +
                "                } else if (!stack.empty()) {\n" +
                "                    currentTile = stack.pop();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * Checks 4 adjacent neighbor. 1 is only OK, if:\n" +
                "         * - It is a wall\n" +
                "         * - Not maze edge\n" +
                "         * - That neighbor has 8 neighbors and all of them are walls OR\n" +
                "         *      the neighbor is one of the top two element in the stack\n" +
                "         */\n" +
                "        private List<Node> checkNeighbors(Node node) {\n" +
                "            List<Node> result = new ArrayList<>();\n" +
                "\n" +
                "            for (Node neighbour : getAdjacentNeighbours(node)) {\n" +
                "                if (neighbour.isWall() && // do not go back\n" +
                "                        !isEdge(neighbour) && // do not dig into edges\n" +
                "                        hasNoVisitedNearby(neighbour, node)) { // do not dig if there is a tunnel nearby\n" +
                "                    result.add(neighbour);\n" +
                "                }\n" +
                "            }\n" +
                "            return result;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        /**\n" +
                "         * Returns north, east, south, west neighbours if they exist\n" +
                "         */\n" +
                "        private List<Node> getAdjacentNeighbours(Node node) {\n" +
                "            List<Node> neighbors = new ArrayList<>();\n" +
                "            int[] nodeCoordinate = node.getCoordinate();\n" +
                "            int[][] adjacentDirections = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};\n" +
                "\n" +
                "            for (int[] direction : adjacentDirections) {\n" +
                "                if (isCoordinateInBound(nodeCoordinate, direction)) {\n" +
                "                    Node neighbor = maze.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);\n" +
                "                    neighbors.add(neighbor);\n" +
                "                }\n" +
                "            }\n" +
                "            return neighbors;\n" +
                "        }\n" +
                "\n" +
                "        private boolean isCoordinateInBound(int[] nodeCoordinate, int[] direction) {\n" +
                "            return nodeCoordinate[0] + direction[0] >= 0 && nodeCoordinate[0] + direction[0] < mazeHeight &&\n" +
                "                    nodeCoordinate[1] + direction[1] >= 0 && nodeCoordinate[1] + direction[1] < mazeWidth;\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * Checks if there are no corridor tiles nearby\n" +
                "         * except for the current searches head (top 2 elements of the Stack)\n" +
                "         */\n" +
                "        private boolean hasNoVisitedNearby(Node node, Node lastStep) {\n" +
                "            int[] nodeCoordinate = node.getCoordinate();\n" +
                "            int[][] allDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};\n" +
                "            int corridorCounter = 0;\n" +
                "            List<Node> lastStepAndNeighbors = getAdjacentNeighbours(lastStep);\n" +
                "            lastStepAndNeighbors.add(lastStep);\n" +
                "\n" +
                "            for (int[] direction : allDirections) {\n" +
                "                Node neighbor = maze.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);\n" +
                "                /*if(!neighbor.isWall()) {\n" +
                "                    corridorCounter++;\n" +
                "                }*/\n" +
                "                if (!neighbor.isWall() && !lastStepAndNeighbors.contains(neighbor)) {\n" +
                "                    return false;  // Todo bug\n" +
                "                }\n" +
                "            }\n" +
                "            return corridorCounter < 3 ;\n" +
                "        }\n" +
                "\n" +
                "        /**\n" +
                "         * returns true if the node is on the edge\n" +
                "         */\n" +
                "        private boolean isEdge(Node node) {\n" +
                "            int[] nodeCoordinate = node.getCoordinate();\n" +
                "            if(nodeCoordinate[0] == 0 || nodeCoordinate[0] == mazeHeight - 1 ||\n" +
                "                nodeCoordinate[1] == 0 || nodeCoordinate[1] == mazeWidth - 1) {\n" +
                "                return true;\n" +
                "            }\n" +
                "            return false;\n" +
                "        }\n" +
                "\n" +
                "        private Node getNext(List<Node> nextTiles) {\n" +
                "            if(doubleStep) {\n" +
                "                doubleStep = false;\n" +
                "            } else {\n" +
                "                doubleStep = true;\n" +
                "                int[] lastStep = stack.peek().getCoordinate();\n" +
                "                int[] lastDirection = getLastDirection(lastStep);\n" +
                "                for (Node neighbor: nextTiles) {\n" +
                "                    int[] coordinate = neighbor.getCoordinate();\n" +
                "                    if(lastStep[0] + lastDirection[0] == coordinate[0] &&\n" +
                "                            lastStep[1] + lastDirection[1] == coordinate[1]){\n" +
                "                        return neighbor;\n" +
                "                    }\n" +
                "                }\n" +
                "                /*if(isCoordinateInBound(lastStep, lastDirection)) {  // interesting result\n" +
                "                    return maze.get(lastStep[0] + lastDirection[0]).get(lastStep[1] + lastDirection[1]);\n" +
                "                }*/\n" +
                "            }\n" +
                "            return nextTiles.get(rnd.nextInt(nextTiles.size()));\n" +
                "        }\n" +
                "\n" +
                "        private int[] getLastDirection(int[] lastStep) {\n" +
                "            int[] beforeLastStep = mazeOrder.get(mazeOrder.size() - 2).getCoordinate();\n" +
                "            int[] direction = new int[2];\n" +
                "            direction[0] = lastStep[0] - beforeLastStep[0];\n" +
                "            direction[1] = lastStep[1] - beforeLastStep[1];\n" +
                "            return direction;\n" +
                "        }\n" +
                "\n" +
                "        private void createGrid() {\n" +
                "            maze.clear();\n" +
                "            for (int i = 0; i < mazeHeight; i++) {\n" +
                "                maze.add(new ArrayList<>());\n" +
                "                for (int j = 0; j < mazeWidth; j++) {\n" +
                "                    Node tile = new Node(i, j);\n" +
                "                    maze.get(i).add(tile);\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        private Node randomStart() {\n" +
                "            int randomCol;\n" +
                "            int randomRow = rnd.nextInt(mazeHeight);\n" +
                "\n" +
                "            if (randomRow == 0 || randomRow == mazeHeight - 1) {\n" +
                "                randomCol = rnd.nextInt(mazeWidth - 2) + 1;  //To avoid corners: between 1 and width-1\n" +
                "            } else {\n" +
                "                randomCol = (((int) (rnd.nextInt(1) + 0.5)) == 0) ? 0 : mazeWidth - 1;\n" +
                "            }\n" +
                "            Node start = maze.get(randomRow).get(randomCol);\n" +
                "            start.removeWall();\n" +
                "            mazeOrder.add(start);\n" +
                "            return start;\n" +
                "        }\n" +
                "\n" +
                "        private void setEndTile(Node start, Node curentTile) {  // TODO WET\n" +
                "            int[] startCoordinate = start.getCoordinate();\n" +
                "            int[] curentCoordintate = curentTile.getCoordinate();\n" +
                "\n" +
                "            if(mazeOrder.size() > 1 && !isEndTileFound) {\n" +
                "                if (startCoordinate[0] == 0 && curentCoordintate[0] + 1 == mazeHeight - 1) {\n" +
                "                    setEndToCorridor(maze.get(curentCoordintate[0] + 1).get(curentCoordintate[1]));\n" +
                "                    isEndTileFound = true;\n" +
                "                } else if (startCoordinate[0] == mazeHeight && curentCoordintate[0] - 1 == 0) {\n" +
                "                    setEndToCorridor(maze.get(curentCoordintate[0] - 1).get(curentCoordintate[1]));\n" +
                "                    isEndTileFound = true;\n" +
                "                } else if (startCoordinate[1] == 0 && curentCoordintate[1] + 1 == mazeWidth - 1) {\n" +
                "                    setEndToCorridor(maze.get(curentCoordintate[0]).get(curentCoordintate[1] + 1));\n" +
                "                    isEndTileFound = true;\n" +
                "                } else if (startCoordinate[1] == mazeWidth && curentCoordintate[1] - 1 == 0) {\n" +
                "                    setEndToCorridor(maze.get(curentCoordintate[0]).get(curentCoordintate[1] - 1));\n" +
                "                    isEndTileFound = true;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        private void setEndToCorridor(Node tile) {\n" +
                "            tile.removeWall();\n" +
                "            mazeOrder.add(tile);\n" +
                "            end = tile;\n" +
                "        }\n" +
                "\n" +
                "        private void printMaze() {\n" +
                "            for (List<Node> row : maze) {\n" +
                "                System.out.println(row);\n" +
                "            }\n" +
                "            System.out.println();\n" +
                "            for (List<Node> row : maze) {\n" +
                "                for (Node node: row) {\n" +
                "                    System.out.print((node.isWall()) ? \"█\": \"░\");\n" +
                "                }\n" +
                "                System.out.println();\n" +
                "            }\n" +
                "            System.out.println();\n" +
                "        }\n" +
                "\n" +
                "    }\n");
        imageNames.add("dfs.gif");
        imageNames.add("DFSimg.png");
        algoWikiInfo =
                "<p>This algorithm is a randomized version of the depth-first search algorithm. Frequently implemented with a stack, this approach is one of the simplest ways to generate a maze using a computer. Consider the space for a maze being a large grid of cells (like a large chess board), each cell starting with four walls. Starting from a random cell, the computer then selects a random neighbouring cell that has not yet been visited. The computer removes the wall between the two cells and marks the new cell as visited, and adds it to the stack to facilitate backtracking. The computer continues this process, with a cell that has no unvisited neighbours being considered a dead-end. When at a dead-end it backtracks through the path until it reaches a cell with an unvisited neighbour, continuing the path generation by visiting this new, unvisited cell (creating a new junction). This process continues until every cell has been visited, causing the computer to backtrack all the way back to the beginning cell. We can be sure every cell is visited.</p>" +
                "<p>As given above this algorithm involves deep recursion which may cause stack overflow issues on some computer architectures. The algorithm can be rearranged into a loop by storing backtracking information in the maze itself. This also provides a quick way to display a solution, by starting at any given point and backtracking to the beginning.</p>" +
                "<p>Mazes generated with a depth-first search have a low branching factor and contain many long corridors, because the algorithm explores as far as possible along each branch before backtracking.</p>";
    }
}
