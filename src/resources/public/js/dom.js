dom = {
    data: {
        mazeOrder: [31,22,13,14,15,16,17,18,12,11,20],
        iterator: 0,
        delay: 500,
        interrupted: false,
        colorGray: "#333333",
        colorOrange: "#ff6b33",
        colorTransparent: "rgba(255, 255, 255, 0.8)"
    },
    saveCanvas: function () {
        return $("section.canvas").html();
    },
    navigationEventListener: function (canvasString) {
        $("nav li").click(function(){
            $("nav li").removeClass("active_nav");
            let selectedMenu = $(this);
            let selectedMenuId = selectedMenu.attr("id");
            //getMazeByAlgo(selectedMenuId);
            selectedMenu.addClass("active_nav");
            switch (selectedMenuId) { /*todo will need to return the menu point id or just the order*/
                case "dfs":
                    dom.loadCanvas(canvasString);
                    break;
                default:
                    $(".canvas").html("<h2>Next sprint</h2><h5>hopefully</h5>");
            }
            dom.generateMazeBtnEventListener();
        });
    },
    loadCanvas: function (canvasString) {
        $(".canvas").html(canvasString);
        /*todo create grid items in .maze*/
    },
    generateMazeBtnEventListener: function () {
        $("#generate").click(function() {

            $(this).hide();
            dom.loadButtons();  //TODO rename
            dom.playerBtnEventlistener();
            dom.mazeGeneration();
        });
    },
    loadButtons: function () {
        $(".player").html(`<button class="btn left_btn" id="start"><i class="fas fa-step-backward fa-2x"></i></button>
            <button class="btn" id="rew"><i class="fas fa-backward fa-2x"></i></button>
            <button class="btn" id="pause"><i class="fas fa-pause fa-2x"></i></button>
            <button class="btn" id="ffwd"><i class="fas fa-forward fa-2x"></i></button>
            <button class="btn right_btn" id="end"><i class="fas fa-step-forward fa-2x"></i></button>`);
    },
    playerBtnEventlistener: function () {
        $(".player button").click(function(){

            switch ($(this).attr("id")) {
                case "start":
                    dom.loadEmptyMaze();
                    dom.resetBtnFontColor();
                    break;
                case "rew":
                    /*mazeGenerationRewerse();*/
                    $("#rew").css("color", dom.data.colorGray);
                    break;
                case "pause":
                    dom.mazeGenerationStop();
                    break;
                case "play":
                    dom.data.iterator = 0;
                    dom.data.delay = 500;
                    dom.loadEmptyMaze();  // Todo after pause it is not okay.
                    dom.mazeGeneration();
                    dom.togglePlayPauseBtn();
                    dom.resetBtnFontColor();
                    break;
                case "ffwd":
                    /*mazeGenerationFastForward();*/
                    dom.resetBtnFontColor();
                    $("#ffwd").css("color", dom.data.colorGray);
                    break;
                case "end":
                    dom.data.iterator = 0;
                    dom.data.delay = 0;
                    dom.mazeGeneration();
                    dom.resetBtnFontColor();
                    break;
                case "generate":
                    break;
                default:
                    alert("error: button not exists " + $(this).attr("id"));
            }
        });
    },
    loadEmptyMaze: function () {
        dom.data.interrupted = true;
        $(".maze .grid-item").css("background-color", "black");
    },
    resetBtnFontColor: function () {  //Todo runs even if not necessary
        $("#rew").css("color", "white");
        $("#ffwd").css("color", "white");
    },
    togglePlayPauseBtn: function () {
        let btn = $("#rew").next();
        if(btn.attr("id") === "pause") {
            btn.attr("id", "play").children().toggleClass("fa-play").toggleClass("fa-pause");
        } else {
            btn.attr("id", "pause").children().toggleClass("fa-play").toggleClass("fa-pause");
        }
    },
    mazeGeneration: function () {
        console.log(dom.data.interrupted);
        function f() {
            let timeOutId;
            if(dom.data.iterator > 1) {
                $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator - 1] + ")").css("background-color", dom.data.colorTransparent);
            } else if(dom.data.iterator === 1){
                $(".maze div:nth-child(" + dom.data.mazeOrder[0] + ")").css("background-color", dom.data.colorTransparent);
            }
            $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").css("background-color", dom.data.colorOrange);
            dom.data.iterator++;  // Todo reverse
            if( dom.data.iterator < dom.data.mazeOrder.length ){
                timeOutId = setTimeout(f, dom.data.delay);

                if(dom.data.interrupted) {
                    clearTimeout(timeOutId);
                }
            } else {
                dom.togglePlayPauseBtn();
            }
        }
        f();
    },
    mazeGenerationStop: function () {
        dom.data.interrupted = true;
        dom.togglePlayPauseBtn();
        dom.resetBtnFontColor();
    }
};
