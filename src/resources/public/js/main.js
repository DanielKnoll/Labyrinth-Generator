$(document).ready(function(){
    apiData.getMazeData("0&18&10");  // TODO delete when page starts with choose algo
    apiData.getMazeInfo(0);
    dom.data.mazeOrderLength = dom.data.mazeOrder.length; //todo delete
    dom.initFunctions.loadEventListeners();
});
