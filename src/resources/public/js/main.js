$(document).ready(function(){
    let canvasString = dom.saveCanvas();
    dom.navigationEventListener(canvasString);
    dom.playerBtnEventlistener();
    dom.generateMazeBtnEventListener();
});