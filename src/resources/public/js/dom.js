dom = {
    data: {
        demoAreaString: "",
        mazeData: "",
        mazeColNum: 18,
        mazeRowNum: 10,
        mazeOrder: [],
        iterator: 0,
        delay: 500,
        interrupted: false,
        infoData: ""
    },

    initFunctions: {

        saveDemoArea: function () {
            dom.utility.createGrid();
            $(".gridGenButtons").hide();
            $(".playerBtns").hide();
            return $("section.demoArea").html();
        },

        loadEventListeners: function (canvasString) {
            dom.eventListeners.navigationEventListener(canvasString);
            dom.eventListeners.generateMazeBtnEventListener();
            dom.eventListeners.anyMazeGenBtnEventListener();
            dom.eventListeners.jumpToStartEventListener();
            dom.eventListeners.rewindMazeGenEventListener();
            dom.eventListeners.playPauseMazeGenEventListener();
            dom.eventListeners.fastForwardMazeGenEventListener();
            dom.eventListeners.jumpToEndEventListener();
        },
    },

    eventListeners: {

        navigationEventListener: function () {
            $("nav li").click(function () {
                dom.data.interrupted = true;
                $(".gridGenButtons").hide();
                $("nav li").removeClass("activeNav");
                let selectedMenu = $(this);
                let selectedMenuId = selectedMenu.attr("id");
                if (selectedMenuId === "dfs") {
                    apiData.getMazeData(selectedMenuId);
                }
                selectedMenu.addClass("activeNav");
                switch (selectedMenuId) { /*todo will need to return the menu point id or just the order*/
                    case "dfs":
                        dom.utility.loadDemoArea(dom.data.demoAreaString);
                        break;
                    default:
                        $(".demoArea").html("<h2 class='title'>Next sprint</h2><h5 class='title'>hopefully</h5>");
                }
                dom.initFunctions.loadEventListeners();
            });
        },

        generateMazeBtnEventListener: function () {
            $("#generate").click(function () {
                dom.dataFunctions.resetIterAndDelay();
                $(this).hide();
                $(".playerBtns").show();
                $(".gridGenButtons").show(200);  // Todo another animation
                //dom.utility.mazeGeneration();
            });
        },

        anyMazeGenBtnEventListener: function () {
            $(".playerBtns button").click(function () {
                dom.utility.resetBtnFontColor();
            });
        },

        jumpToStartEventListener: function () {
            $("#start").click(function () {
                dom.data.interrupted = true;
                dom.utility.resetMaze();
                dom.utility.changePauseToPlay();
                console.log("stopVisualization - start");
            });
        },

        rewindMazeGenEventListener: function () {
            $("#rew").click(function () {
                dom.data.interrupted = true;
                dom.utility.changePlayToPause();
                $("#rew").addClass("activeBtn");
                dom.data.delay = 200;
                //dom.utility.mazeGeneration();
                console.log("runVisualization - rew, delay: " +dom.data.delay);
            });

        },

        playPauseMazeGenEventListener: function () {
            let btn = $("#rew").next();  // play/pause button
            btn.click(function () {
                switch (btn.attr("id")) {
                    case "pause":
                        dom.data.interrupted = true;
                        dom.utility.changePauseToPlay();
                        console.log("StopVisualization");
                        break;
                    case "play":
                        dom.data.interrupted = false;
                        if (dom.data.iterator === 0 || dom.data.iterator === dom.data.mazeOrder.length) {
                            dom.utility.resetMaze();
                        }
                        dom.data.delay = 500;
                        dom.utility.changePlayToPause();
                        //dom.utility.mazeGeneration();
                        console.log("runVisualization - norm, delay: " +dom.data.delay);
                        break;
                    default:
                        console.log("error: button not exists");
                }
            });
        },

        fastForwardMazeGenEventListener: function () {
            $("#ffwd").click(function () {
                dom.data.interrupted = true;
                dom.utility.changePlayToPause();
                $("#ffwd").addClass("activeBtn");
                dom.data.delay = 100;
                //dom.utility.mazeGeneration();
                console.log("runVisualization - fast, delay: " +dom.data.delay);
            });
        },

        jumpToEndEventListener: function () {
            $("#end").click(function () {
                dom.data.interrupted = true;
                dom.utility.resetMaze();
                /*dom.data.iterator = dom.data.mazeOrder.length -1;*/
                for (let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                    if (dom.data.mazeOrder.includes(i)) {
                        $(".maze div:nth-child(" + i + ")").addClass("mazeCorridor");
                    }
                }
                console.log("stopVisualization - end");
            });
        },
    },

    utility: {

        createGrid: function () {
            apiData.getMazeData("dfs");  //TODO remove
            let mazeDom = $(".maze");
            for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                mazeDom.append(`<div class="mazeWall"></div>`);
            }
        },

        loadDemoArea: function (canvasString) {
            $(".demoArea").html(canvasString);
        },

        resetMaze: function () {
            $(".maze > div").removeClass("mazeCorridor").removeClass("mazeGenPointer").addClass("mazeWall");
            dom.dataFunctions.resetIterAndDelay();
            //dom.utility.changePauseToPlay();
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
        changePauseToPlay: function () {  // TODO debug
            $("#rew").next().attr("id", "play").children().removeClass("fa-pause").addClass("fa-play");
        },

        changePlayToPause: function () {
            $("#rew").next().attr("id", "pause").children().removeClass("fa-play").addClass("fa-pause");
        },

        mazeGeneration: function() {
            dom.data.interrupted = false;
            function f() {
                let timeOutId;
                dom.utility.changeCurrentAndPreviousMazeTileColor();
                dom.data.iterator++;  // Todo reverse
                if( dom.data.iterator < dom.data.mazeOrder.length ){
                    timeOutId = setTimeout(f, dom.data.delay);
                    if(dom.data.interrupted) {
                        clearTimeout(timeOutId); // TODO it will still do one more step
                        $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator] + ")").removeClass("mazeGenPointer").addClass("mazeWall");
                        /*dom.data.iterator -= 1;*/
                        $(".maze div:nth-child(" + dom.data.mazeOrder[dom.data.iterator - 1] + ")").removeClass("mazeGenCorridor").addClass("mazeGenPointer");
                    }
                } else {
                    dom.utility.changePauseToPlay();
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
    },

    dataFunctions: {

        saveMazeDate: function (mazeData) {
            dom.data.mazeData = mazeData;
            dom.data.mazeOrder = mazeData.mazeOrder;
        },

        resetIterAndDelay: function () {
            dom.data.iterator = 0;
            dom.data.delay = 500;
        },
    },
};
