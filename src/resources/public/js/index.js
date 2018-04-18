function saveCanvas() {
    return $("section.canvas").html();
}

function navigationEventListener(canvasString) {
    $("nav li").click(function(){
        $("nav li").removeClass("active_nav");
        let selectedMenu = $(this);
        let selectedMenuId = selectedMenu.attr("id");

        selectedMenu.addClass("active_nav");
        switch (selectedMenuId) { /*todo will need to return the menu point id or just the order*/
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
}

function generateMazeBtnEventListener() {
    $("#generate").click(function() {
        let order = [31,22,13,14,15,16,17,18,12,11,20]; //getOrder();
        let iterator = 0;
        let delay = 1000;

        $(this).hide();
        loadButtons(); //TODO rename
        playerBtnEventlistener(order, iterator, delay);
        mazeGeneration(order, iterator, delay);
        console.log("out: " + order + " " + iterator);
    });
}

function loadButtons() {
    $(".player").html(`<button class="btn left_btn" id="start"><i class="fas fa-step-backward fa-2x"></i></button>
        <button class="btn" id="rew"><i class="fas fa-backward fa-2x"></i></button>
        <button class="btn" id="pause"><i class="fas fa-pause fa-2x"></i></button>
        <button class="btn" id="ffwd"><i class="fas fa-forward fa-2x"></i></button>
        <button class="btn right_btn" id="end"><i class="fas fa-step-forward fa-2x"></i></button>`); /*fa-play*/
}

function playerBtnEventlistener(order, iterator, delay) {
    console.log("list: " + order + " " + iterator);
    $(".player button").click(function(){

        switch ($(this).attr("id")) {
            case "start":
                loadEmptyMaze();
                resetBtnFontColor();
                break;
            case "rew":
                /*mazeGenerationRewerse();*/
                $("#rew").css("color", "#333333");
                break;
            case "pause":
                mazeGenerationStop();
                togglePlayPauseBtn();
                resetBtnFontColor();
                break;
            case "play":
                loadEmptyMaze();
                mazeGeneration(order, iterator, delay);
                togglePlayPauseBtn();
                resetBtnFontColor();
                break;
            case "ffwd":
                /*mazeGenerationFastForward();*/
                resetBtnFontColor();
                $("#ffwd").css("color", "#333333");
                break;
            case "end":
                mazeGeneration(order, iterator, 0);
                resetBtnFontColor();
                break;
            case "generate":
                break;
            default:
                alert("error: button not exists " + $(this).attr("id"));
        }
    });
}

function loadEmptyMaze() {
    $(".maze .grid-item").css("background-color", "black");
}

function resetBtnFontColor() {  //Todo runs even if not necessary
    $("#rew").css("color", "white");
    $("#ffwd").css("color", "white");
}

function togglePlayPauseBtn() {
    let btn = $("#rew").next();
    if(btn.attr("id") === "pause") {
        btn.attr("id", "play").children().toggleClass("fa-play").toggleClass("fa-pause");
    } else {
        btn.attr("id", "pause").children().toggleClass("fa-play").toggleClass("fa-pause");
    }
}

function mazeGeneration(order, iterator, delay) {
    function f() {
        console.log(order + " " + iterator);
        if(iterator > 1) {
            $(".maze div:nth-child(" + order[iterator - 1] + ")").css("background-color", "rgba(255, 255, 255, 0.8)");
        } else if(iterator === 1){
            $(".maze div:nth-child(" + order[0] + ")").css("background-color", "rgba(255, 255, 255, 0.8)");
        }
        $(".maze div:nth-child(" + order[iterator] + ")").css("background-color", "#ff6b33");
        iterator++;
        if( iterator < order.length ){
            setTimeout(f, delay);
        } else {
            togglePlayPauseBtn();
        }
    }
    f();

}

function mazeGenerationStop() {
    //mazeGeneration().stop();
    togglePlayPauseBtn();
}
