dom = {
    data: {
        mazeData: "",
        mazeColNum: 18,
        mazeRowNum: 10,
        mazeOrder: [],
        iterator: 0,
        delay: 500,
        interrupted: false,
        colorGray: "#333333", // TODO class
        colorOrange: "#ff6b33", // TODO class
        colorTransparent: "rgba(255, 255, 255, 0.8)" // TODO class
    },
    createGrid: function () {
        apiData.getMazeData("dfs");  //TODO remove
        let mazeDom = $(".maze");
        for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
            mazeDom.append(`<div></div>`); // TODO no class, fixed height
        }
    },

    saveMazeDate: function (mazeData) {
        dom.data.mazeData = mazeData;
        dom.data.mazeOrder = mazeData.mazeOrder;
    },

    saveDemoArea: function () {
        dom.createGrid();
        return $("section.demoArea").html();
    },

    navigationEventListener: function (demoAreaString) {
        $("nav li").click(function(){
            dom.data.interrupted = true;
            $("nav li").removeClass("active_nav");
            let selectedMenu = $(this);
            let selectedMenuId = selectedMenu.attr("id");
            if(selectedMenuId === "dfs") {
                apiData.getMazeData(selectedMenuId);
            }
            selectedMenu.addClass("active_nav");
            switch (selectedMenuId) { /*todo will need to return the menu point id or just the order*/
                case "dfs":
                    dom.loadDemoArea(demoAreaString);
                    break;
                default:
                    $(".demoArea").html("<h2>Next sprint</h2><h5>hopefully</h5>");
            }
            dom.generateMazeBtnEventListener();
        });
    },

    loadDemoArea: function (demoAreaString) {
        $(".demoArea").html(demoAreaString);
    },

    generateMazeBtnEventListener: function () {
        $("#generate").click(function() {
            dom.resetIterAndDelay();
            $(this).hide();
            dom.loadButtons();  //TODO rename
            dom.playerBtnEventlistener();
            dom.mazeGeneration();
        });
    },

    resetIterAndDelay: function () {
        dom.data.iterator = 0;
        dom.data.delay = 500;
    },

    loadButtons: function () {
        $(".playerBtns").html(`<button class="btn left_btn" id="start"><i class="fas fa-step-backward fa-2x"></i></button>
            <button class="btn" id="rew"><i class="fas fa-backward fa-2x"></i></button>
            <button class="btn" id="pause"><i class="fas fa-pause fa-2x"></i></button>
            <button class="btn" id="ffwd"><i class="fas fa-forward fa-2x"></i></button>
            <button class="btn right_btn" id="end"><i class="fas fa-step-forward fa-2x"></i></button>`);
    },

    playerBtnEventlistener: function () {
        $(".playerBtns button").click(function(){

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
                    dom.fastForwardMazeGen();
                    break;
                case "end":
                    dom.jumpToEnd();
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
        $(".maze > div").css("background-color", "black"); // TODO class
        dom.resetBtnFontColor();
        dom.resetIterAndDelay();
        $("#rew").next().attr("id", "play").children().addClass("fa-play").removeClass("fa-pause");  //change pause to play
    },

    resetBtnFontColor: function () {
        let btnRew = $("#rew");
        let btnFfwd = $("#ffwd");
        if(btnRew.css("color") === "rgb(51, 51, 51)") { // TODO class
            btnRew.css("color", "white");
        }
        if(btnFfwd.css("color") === "rgb(51, 51, 51)") { // TODO class
            btnFfwd.css("color", "white");
        }
    },

    rewindMazeGen: function () {
        dom.data.interrupted = true;
        dom.resetBtnFontColor();
        $("#rew").css("color", dom.data.colorGray);  // TODO class
        dom.data.delay = 1000;
        dom.mazeGeneration();
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
        if(dom.data.iterator === 0 || dom.data.iterator === dom.data.mazeOrder.length) {
            dom.jumpToStart();
        }
        dom.data.delay = 500;
        dom.mazeGeneration();
        dom.togglePlayPauseBtn();
        dom.resetBtnFontColor();
    },

    mazeGeneration: function() {
        dom.data.interrupted = false;
        function f() {
            let timeOutId;
            dom.changeCurrentAndPreviousMazeTileColor();
            dom.data.iterator++;  // Todo reverse
            if( dom.data.iterator < dom.data.mazeOrder.length ){
                timeOutId = setTimeout(f, dom.data.delay);
                if(dom.data.interrupted) {
                    clearTimeout(timeOutId); // TODO it will do one more step
                }
            } else {
                dom.togglePlayPauseBtn();
            }
        }
        f();
    },

    changeCurrentAndPreviousMazeTileColor: function () {
        if(dom.data.iterator === 1) { // TODO class
            $(".maze div:nth-child(" + dom.data.mazeOrder[0] + ")").css("background-color", dom.data.colorTransparent);
        } else if(dom.data.iterator > 1){ // TODO class
            $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator - 1] + ")").css("background-color", dom.data.colorTransparent);
        }
        if(dom.data.iterator < dom.data.mazeOrder.length) { // TODO class
            $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").css("background-color", dom.data.colorOrange);
        }
    },

    fastForwardMazeGen: function () {
        dom.data.interrupted = true;
        dom.resetBtnFontColor();
        $("#ffwd").css("color", dom.data.colorGray); // TODO class
        dom.data.delay = 100;
        dom.mazeGeneration();
    },

    jumpToEnd: function () {
        dom.data.interrupted = true;
        dom.data.iterator = dom.data.mazeOrder.length -1;
        for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
            if(dom.data.mazeOrder.includes(i)) {
                $(".maze div:nth-child(" + i + ")").css("background-color", dom.data.colorTransparent); // TODO class
            }
        }
        dom.resetBtnFontColor();
    }
};
