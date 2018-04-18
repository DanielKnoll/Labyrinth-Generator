$(document).ready(function(){
    let canvasString = saveCanvas();
    navigationEventListener(canvasString);
    playerBtnEventlistener();
    generateMazeBtnEventListener();
});