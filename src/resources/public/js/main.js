$(document).ready(function(){
    apiData.getMazeData("0&19&13");  // TODO delete when page starts with choose algo
    apiData.getMazeInfo(0);
    $(".infoBtnsGrid").hide();
    $(".playerBtns").hide();
    dom.data.mazeOrderLength = dom.data.mazeOrder.length; //todo delete
    dom.initFunctions.loadEventListeners();
});
