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
        setDefaultApiLink("wall=0&amp;algo=0&amp;width=18&amp;height=10");
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
                "        private int[] place = new int[2];\n" +
                "    \n" +
                "        public Node(int x, int y) {\n" +
                "            place[0] = x;\n" +
                "            place[1] = y;\n" +
                "        }\n" +
                "    \n" +
                "        public boolean isWall() {\n" +
                "            return isWall;\n" +
                "        }\n" +
                "    \n" +
                "        public void removeWall() {\n" +
                "            isWall = false;\n" +
                "        }\n" +
                "    \n" +
                "        public int[] getCoordinate() {\n" +
                "            return place;\n" +
                "        }\n" +
                "    \n" +
                "        @Override\n" +
                "        public String toString() {\n" +
                "            return (isWall) ? \"0\": \"1\";\n" +
                "        }\n" +
                "    }\n");
        classNames.add("Dfs Class");
        classCodes.add(
                "    import java.util.*;\n" +
                "    \n" +
                "    public class Dfs {\n" +
                "        List<Node> mazeOrder = new ArrayList<>();\n" +
                "        Stack<Node> stack = new Stack<>();\n" +
                "        private int mazeWidth;\n" +
                "        private int mazeHeight;\n" +
                "        private List<List<Node>> allTiles = new ArrayList<>();\n" +
                "        private Random rnd = new Random(12345);\n" +
                "    \n" +
                "        public Dfs(int width, int height) {\n" +
                "            mazeHeight = height;\n" +
                "            mazeWidth = width;\n" +
                "    \n" +
                "            createGrid();\n" +
                "            printMaze();\n" +
                "            Node startTile = randomStart();\n" +
                "            startTile.removeWall();\n" +
                "            stack.push(startTile);\n" +
                "            mazeOrder.add(startTile);\n" +
                "            generateLabyrinth(startTile);\n" +
                "        }\n" +
                "    \n" +
                "        public void generateLabyrinth(Node start) {\n" +
                "            Node currentTile = start;\n" +
                "            while(!stack.empty()) {\n" +
                "                List<Node> nextTiles = checkNeighbors(currentTile);\n" +
                "                if (nextTiles.size() > 0) {\n" +
                "                    int num = rnd.nextInt(nextTiles.size());\n" +
                "                    Node next = nextTiles.get(num);\n" +
                "                    next.removeWall();\n" +
                "                    stack.push(next);\n" +
                "                    mazeOrder.add(next);\n" +
                "                    currentTile = next;\n" +
                "                } else if (!stack.empty()) {\n" +
                "                    currentTile = stack.pop();\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    \n" +
                "        /**\n" +
                "         * Checks 4 adjacent neighbor. 1 is only OK, if:\n" +
                "         * - It is a wall\n" +
                "         * - Not maze edge\n" +
                "         * - That neighbor has 8 neighbors and all of them are walls OR\n" +
                "         *      the neighbor is one of the top two element in the stack\n" +
                "         */\n" +
                "        private List<Node> checkNeighbors(Node node) {\n" +
                "            List<Node> result = new ArrayList<>();\n" +
                "    \n" +
                "            for (Node neighbour : getAdjacentNeighbours(node)) {\n" +
                "                if (neighbour.isWall() && // do not go back\n" +
                "                    !isEdge(neighbour) && // do not dig into edges\n" +
                "                    hasNoVisitedNearby(neighbour)) { // do not dig if there is a tunnel nearby\n" +
                "                    result.add(neighbour);\n" +
                "                }\n" +
                "            }\n" +
                "            return result;\n" +
                "        }\n" +
                "    \n" +
                "        /**\n" +
                "         * Returns north, east, south, west neighbours if they exist\n" +
                "         */\n" +
                "        private List<Node> getAdjacentNeighbours(Node node) {\n" +
                "            List<Node> neighbors = new ArrayList<>();\n" +
                "            int[] nodeCoordinate = node.getCoordinate();\n" +
                "            int[][] adjacentDirections = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};\n" +
                "    \n" +
                "            for (int[] direction : adjacentDirections) {\n" +
                "                if (isCoordinateInBound(nodeCoordinate, direction)) {\n" +
                "                    Node neighbor = allTiles.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);\n" +
                "                    neighbors.add(neighbor);\n" +
                "                }\n" +
                "            }\n" +
                "            return neighbors;\n" +
                "        }\n" +
                "    \n" +
                "        private boolean isCoordinateInBound(int[] nodeCoordinate, int[] direction) {\n" +
                "            return nodeCoordinate[0] + direction[0] >= 0 && nodeCoordinate[0] + direction[0] < mazeHeight &&\n" +
                "                    nodeCoordinate[1] + direction[1] >= 0 && nodeCoordinate[1] + direction[1] < mazeWidth;\n" +
                "        }\n" +
                "    \n" +
                "        /**\n" +
                "         * Checks if there are no corridor tiles nearby\n" +
                "         * except for the current searches head (top 2 elements of the Stack)\n" +
                "         */\n" +
                "        private boolean hasNoVisitedNearby(Node node) {\n" +
                "            int[] nodeCoordinate = node.getCoordinate();\n" +
                "            int[][] allDirections = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}, {0, -1}, {-1, 0}, {0, 1}, {1, 0}};\n" +
                "    \n" +
                "            for (int[] direction : allDirections) {\n" +
                "                    Node neighbor = allTiles.get(nodeCoordinate[0] + direction[0]).get(nodeCoordinate[1] + direction[1]);\n" +
                "                    if (!neighbor.isWall() && !(stack.search(neighbor) == 1 || stack.search(neighbor) == 2)) {\n" +
                "                        return false;  // Todo bug\n" +
                "                    }\n" +
                "            }\n" +
                "            return true;\n" +
                "        }\n" +
                "    \n" +
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
                "    \n" +
                "    \n" +
                "        private void createGrid() {\n" +
                "            allTiles.clear();\n" +
                "            for (int i = 0; i < mazeHeight; i++) {\n" +
                "                allTiles.add(new ArrayList<>());\n" +
                "                for (int j = 0; j < mazeWidth; j++) {\n" +
                "                    Node tile = new Node(i, j);\n" +
                "                    allTiles.get(i).add(tile);\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    \n" +
                "        private Node randomStart() {\n" +
                "            int randomCol;\n" +
                "            int randomRow = rnd.nextInt(mazeHeight);\n" +
                "    \n" +
                "            if (randomRow == 0 || randomRow == mazeHeight - 1) {\n" +
                "                randomCol = rnd.nextInt(mazeWidth - 2) + 1;  //To avoid corners: between 1 and width-1\n" +
                "            } else {\n" +
                "                randomCol = (((int) (rnd.nextInt(1) + 0.5)) == 0) ? 0 : mazeWidth - 1;\n" +
                "            }\n" +
                "            return allTiles.get(randomRow).get(randomCol);\n" +
                "        }\n" +
                "    }");
        imageNames.add("300px-Depth-first-tree.svg.png");
        imageNames.add("DFSimg.png");
        algoWikiInfo =
                "<p>This algorithm is a randomized version of the depth-first search algorithm. Frequently implemented with a stack, this approach is one of the simplest ways to generate a maze using a computer. Consider the space for a maze being a large grid of cells (like a large chess board), each cell starting with four walls. Starting from a random cell, the computer then selects a random neighbouring cell that has not yet been visited. The computer removes the wall between the two cells and marks the new cell as visited, and adds it to the stack to facilitate backtracking. The computer continues this process, with a cell that has no unvisited neighbours being considered a dead-end. When at a dead-end it backtracks through the path until it reaches a cell with an unvisited neighbour, continuing the path generation by visiting this new, unvisited cell (creating a new junction). This process continues until every cell has been visited, causing the computer to backtrack all the way back to the beginning cell. We can be sure every cell is visited.</p>" +
                "<p>As given above this algorithm involves deep recursion which may cause stack overflow issues on some computer architectures. The algorithm can be rearranged into a loop by storing backtracking information in the maze itself. This also provides a quick way to display a solution, by starting at any given point and backtracking to the beginning.</p>" +
                "<p>Mazes generated with a depth-first search have a low branching factor and contain many long corridors, because the algorithm explores as far as possible along each branch before backtracking.</p>";
    }
}
