dom = {
    data: {
        mazeData: "",
        mazeColNum: 18,
        mazeRowNum: 10,
        mazeOrder: [],
        iterator: 0,
        delay: 500,
        interrupted: false,
    },
    createGrid: function () {
        apiData.getMazeData("dfs");  //TODO remove
        let mazeDom = $(".maze");
        for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
            mazeDom.append(`<div class="mazeWall"></div>`);
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
            $("nav li").removeClass("activeNav");
            let selectedMenu = $(this);
            let selectedMenuId = selectedMenu.attr("id");
            if(selectedMenuId === "dfs") {
                apiData.getMazeData(selectedMenuId);
            }
            selectedMenu.addClass("activeNav");
            switch (selectedMenuId) { /*todo will need to return the menu point id or just the order*/
                case "dfs":
                    dom.loadDemoArea(demoAreaString);
                    break;
                default:
                    $(".demoArea").html("<h2 class='title'>Next sprint</h2><h5 class='title'>hopefully</h5>");
            }
            dom.generateMazeBtnEventListener();
        });
    },

    loadDemoArea: function (canvasString) {
        $(".demoArea").html(canvasString);
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
        $(".playerBtns").html(`
            <button class="btn lefBtn" id="start"><i class="fas fa-step-backward fa-2x"></i></button>
            <button class="btn" id="rew"><i class="fas fa-backward fa-2x"></i></button>
            <button class="btn" id="pause"><i class="fas fa-pause fa-2x"></i></button>
            <button class="btn" id="ffwd"><i class="fas fa-forward fa-2x"></i></button>
            <button class="btn rightBtn" id="end"><i class="fas fa-step-forward fa-2x"></i></button>`);
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
        $(".maze > div").removeClass("mazeCorridor").removeClass("mazeGenPointer").addClass("mazeWall");
        dom.resetBtnFontColor();
        dom.resetIterAndDelay();
        dom.changeBtnToPlay();
    },

    changeBtnToPlay: function () {
        $("#rew").next().attr("id", "play").children().addClass("fa-play").removeClass("fa-pause");
    },

    resetBtnFontColor: function () {
        let btnRew = $("#rew");
        let btnFfwd = $("#ffwd");
        if(btnRew.hasClass("activeBtn")) {
            btnRew.removeClass("activeBtn");
        }
        if(btnFfwd.hasClass("activeBtn")) {
            btnFfwd.removeClass("activeBtn");
        }
    },

    rewindMazeGen: function () {
        dom.data.interrupted = true;
        dom.resetBtnFontColor();
        $("#rew").addClass("activeBtn");
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
                    clearTimeout(timeOutId); // TODO it will still do one more step
                    $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").removeClass("mazeGenPointer").addClass("mazeWall");
                    dom.data.iterator -= 1;
                    $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").removeClass("mazeGenCorridor").addClass("mazeGenPointer");
                    dom.changeBtnToPlay();
                }
            } else {
                dom.togglePlayPauseBtn();
            }
        }
        f();
    },

    changeCurrentAndPreviousMazeTileColor: function () {
        let order = dom.data.mazeOrder;
        let iterator = dom.data.iterator;
        if(iterator === 1) {
            $(".maze div:nth-child(" + order[0] + ")").removeClass("mazeGenPointer").addClass("mazeCorridor");
        } else if(iterator > 1){
            $(".maze div:nth-child(" + order[iterator - 1] + ")").removeClass("mazeGenPointer").addClass("mazeCorridor");
        }
        if(iterator < order.length -1 ) {
            $(".maze div:nth-child(" + order[iterator] + ")").removeClass("mazeWall").addClass("mazeGenPointer");
        }
    },

    fastForwardMazeGen: function () {
        dom.data.interrupted = true;
        dom.resetBtnFontColor();
        $("#ffwd").addClass("activeBtn");
        dom.data.delay = 100;
        dom.mazeGeneration();
    },

    jumpToEnd: function () {
        dom.jumpToStart();
        dom.data.iterator = dom.data.mazeOrder.length -1;
        for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
            if(dom.data.mazeOrder.includes(i)) {
                $(".maze div:nth-child(" + i + ")").addClass("mazeCorridor");
            }
        }
        dom.resetBtnFontColor();
    }
};
