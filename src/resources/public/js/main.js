$(document).ready(function(){
    let canvasString = dom.saveDemoArea();
    dom.navigationEventListener(canvasString);
    dom.playerBtnEventlistener();
    dom.generateMazeBtnEventListener();
});