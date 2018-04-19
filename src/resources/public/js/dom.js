dom = {
    data: {
        mazeColNum: 18,
        mazeRowNum: 10,
        mazeOrder: [167, 149, 148, 130, 112, 113, 95, 77, 59, 41, 42, 43, 44, 62, 80, 79, 97, 115, 133, 151, 152, 153, 154, 155, 137, 119, 120, 121, 122, 104, 86, 87, 69, 70, 71, 72, 147, 146, 128, 110, 92, 75, 57, 56, 38, 20, 21, 22, 24, 26, 27, 28, 46, 64, 82, 100, 99, 117, 29, 83, 84, 66, 48, 49, 50, 32, 33, 34, 35, 156, 157, 158, 159, 160, 161, 143, 125, 107, 106],
        iterator: 0,
        delay: 500,
        interrupted: false,
        colorGray: "#333333",
        colorOrange: "#ff6b33",
        colorTransparent: "rgba(255, 255, 255, 0.8)"
    },
    createGrid: function () {
        let cssColumValue ="";
        let mazeDom = $(".maze");
        for(let i = 0; i < dom.data.mazeColNum; i++) {
            cssColumValue += "auto ";
        }
        mazeDom.css("grid-template-columns", cssColumValue);
        for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
            mazeDom.append(`<div class="grid-item\">&nbsp;</div>`);
        }
    },

    saveCanvas: function () {
        dom.createGrid();
        return $("section.canvas").html();
    },

    navigationEventListener: function (canvasString) {
        $("nav li").click(function(){
            $("nav li").removeClass("active_nav");
            let selectedMenu = $(this);
            let selectedMenuId = selectedMenu.attr("id");
            // todo getMazeByAlgo(selectedMenuId);
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
                    dom.jumpToStart();
                    break;
                case "rew":
                    dom.rewindMazeGen();
                    break;
                case "pause":
                    dom.stopMazeGen();
                    break;
                case "play":
                    dom.playMazeGen();
                    break;
                case "ffwd":
                    /*fastForwardMazeGen();*/
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
                    console.log("error: button not exists " + $(this).attr("id"));
            }
        });
    },

    jumpToStart: function () {
        dom.data.interrupted = true;
        $(".maze .grid-item").css("background-color", "black");
        dom.resetBtnFontColor();
        dom.data.iterator = 0;
        dom.data.delay = 500;
        $("#rew").next().attr("id", "play").children().addClass("fa-play").removeClass("fa-pause");  //change pause to play
    },

    resetBtnFontColor: function () {
        let btnRew = $("#rew");
        let btnFfwd = $("#ffwd");
        if(btnRew.css("color") === "rgb(51, 51, 51)") {
            btnRew.css("color", "white");
        }
        if(btnFfwd.css("color") === "rgb(51, 51, 51)") {
            btnFfwd.css("color", "white");
        }
    },

    rewindMazeGen: function () {
        dom.resetBtnFontColor();
        $("#rew").css("color", dom.data.colorGray);
    },

    stopMazeGen: function () {
        dom.data.interrupted = true;
        dom.togglePlayPauseBtn();
        dom.resetBtnFontColor();
    },

    togglePlayPauseBtn: function () {
        let btn = $("#rew").next();
        let value = (btn.attr("id") === "pause") ? "play" : "pause";
        btn.attr("id", value).children().toggleClass("fa-play").toggleClass("fa-pause");
    },

    playMazeGen: function () {
        console.log(dom.data.iterator);
        if(dom.data.iterator === 0) {
            dom.jumpToStart();
        }
        dom.mazeGeneration();
        dom.togglePlayPauseBtn();
        dom.resetBtnFontColor();
    },

    mazeGeneration: function () {
        dom.data.interrupted = false;
        function f() {
            let timeOutId;
            dom.changeCurrentAndPreviousMazeTileColor();
            dom.data.iterator++;  // Todo reverse
            if( dom.data.iterator < dom.data.mazeOrder.length ){
                timeOutId = setTimeout(f, dom.data.delay);
                console.log(timeOutId);
                if(dom.data.interrupted) {
                    clearTimeout(timeOutId); // TODO it will do one more step
                }
            } else {
                dom.togglePlayPauseBtn();
            }
        }
        f();
    },

    changeCurrentAndPreviousMazeTileColor: function() {
        if(dom.data.iterator === 1) {
            $(".maze div:nth-child(" + dom.data.mazeOrder[0] + ")").css("background-color", dom.data.colorTransparent);
        } else if(dom.data.iterator > 1){
            $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator - 1] + ")").css("background-color", dom.data.colorTransparent);
        }
        $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").css("background-color", dom.data.colorOrange);
    }

};
