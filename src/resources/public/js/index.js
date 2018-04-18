function saveCanvas() {
    return $("section.canvas").html();
}

function navigationEventListener(canvasString) {
    $("nav li").click(function(){
        $("nav li").removeClass("active_nav");
        $(this).addClass("active_nav");  /*todo will need the menu point*/

        switch ($(this).attr("id")) {
            case "dfs":
                loadCanvas(canvasString);
                break;
            default:
                $(".canvas").html("<h2>Next sprint</h2><h5>hopefully</h5>");
        }
        generateMazeBtnEventListener();
    });
}

function loadCanvas(canvasString) {
    $(".canvas").html(canvasString);
    /*todo fill maze with 0*/
}

function generateMazeBtnEventListener() {
    $("#generate").click(function() {
        $(this).hide();
        loadButtons();
        playerBtnEventlistener();
    });
}

function loadButtons() {
    $(".player").html(`<button class="btn left_btn" id="start"><i class="fas fa-step-backward fa-2x"></i></button>
        <button class="btn" id="rew"><i class="fas fa-backward fa-2x"></i></button>
        <button class="btn" id="pause"><i class="fas fa-pause fa-2x"></i></button>
        <button class="btn" id="ffwd"><i class="fas fa-forward fa-2x"></i></button>
        <button class="btn right_btn" id="end"><i class="fas fa-step-forward fa-2x"></i></button>`); /*fa-play*/
}

function playerBtnEventlistener() {
    $(".player button").click(function(){

        switch ($(this).attr("id")) {
            case "start":
                /*loadEmptyMaze();*/
                resetBtnFontColor();
                break;
            case "rew":
                /*mazeGenerationRewerse();*/
                $("#rew").css("color", "#333333");
                break;
            case "pause":
                /*mazeGenerationStop();*/
                $("#pause i").toggleClass("fa-pause").toggleClass("fa-play");
                $("#pause").attr("id", "play");
                resetBtnFontColor();
                break;
            case "play":
                /*mazeGeneration();*/
                $("#play i").toggleClass("fa-play").toggleClass("fa-pause");
                $("#play").attr("id", "pause");
                resetBtnFontColor();
                break;
            case "ffwd":
                /*mazeGenerationFastForward();*/
                resetBtnFontColor();
                $("#ffwd").css("color", "#333333");
                break;
            case "end":
                /*loadGeneratedMaze();*/
                resetBtnFontColor();
                break;
            case "generate":
                break;
            default:
                alert("error: button not exists " + $(this).attr("id"));
        }
    });
}

function resetBtnFontColor() {
    $("#rew").css("color", "white");
    $("#ffwd").css("color", "white");
}

function mazeGeneration() {

}