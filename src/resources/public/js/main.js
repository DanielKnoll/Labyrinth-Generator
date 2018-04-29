$(document).ready(function(){
    apiData.getMazeData("dfs");  // TODO delete when page starts with choose algo
    dom.data.mazeOrderLength = dom.data.mazeOrder.length; //todo delete
    dom.initFunctions.createGrid();
    dom.initFunctions.loadEventListeners();
});