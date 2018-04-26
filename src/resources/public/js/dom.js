dom = {
    data: {
        demoAreaString: "",
        mazeData: "",
        mazeColNum: 18,
        mazeRowNum: 10,
        mazeOrder: [],
        mazeOrderLength: 0,
        iterator: 0,
        delay: 500,
        interrupted: false,
        reverseOrder: false,
    },

    initFunctions: {

        saveDemoArea: function () {
            dom.initFunctions.createGrid();
            $(".gridGenButtons").hide();
            $(".playerBtns").hide();
            return $("section.demoArea").html();
        },

        createGrid: function () {
            apiData.getMazeData("dfs");  // TODO delete when page starts with choose algo
            let mazeDom = $(".maze");
            for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                mazeDom.append(`<div class="mazeWall"></div>`);
            }
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
            dom.eventListeners.newMazeEventListener();
        },
    },

    eventListeners: {

        navigationEventListener: function () {
            $("nav li").click(function () {
                dom.data.interrupted = true;
                $(".gridGenButtons").hide();
                $("nav li").removeClass("activeNav");
                let selectedMenu = $(this);
                selectedMenu.addClass("activeNav");

                let selectedMenuId = selectedMenu.attr("id");
                switch (selectedMenuId) {
                    case "dfs":
                        apiData.getMazeData("dfs"); // TODO generate?wall=0&...
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
                dom.dataFunctions.resetIterDelayOrder();
                $(this).hide();
                $(".playerBtns").show(100);
                $(".gridGenButtons").show(200);  // Todo another animation
                dom.utility.mazeGeneration();
            });
        },

        anyMazeGenBtnEventListener: function () {  //TODO how to include newMaze and Solve? is this a good practice?
            let interruptorBtns = ["start", "rew", "pause", "ffwd", "end", "newMaze"];
            let forwardBtns = ["start", "play", "ffwd", "end"];
            let resetMazeBtns = ["start", "end", "newMaze"];
            $(".playerBtns button").click(function () {
                dom.utility.resetBtnFontColor();
                let btnId = $(this).attr("id");

                if(interruptorBtns.includes(btnId)) {
                    dom.data.interrupted = true;
                }
                if(forwardBtns.includes(btnId)) {
                    dom.data.reverseOrder = false;
                }
                if(resetMazeBtns.includes(btnId) || (btnId === "play" && (dom.data.iterator === 0 ||
                        dom.data.iterator >= dom.data.mazeOrderLength))) {
                    dom.utility.resetMaze();
                }
            });
        },

        jumpToStartEventListener: function () {
            $("#start").click(function () {
                dom.utility.changePauseToPlay();
            });
        },

        rewindMazeGenEventListener: function () {
            $("#rew").click(function () {
                if(dom.data.iterator <= dom.data.mazeOrderLength + 1) {
                    dom.utility.changePlayToPause();
                    $("#rew").addClass("activeBtn");
                    dom.data.delay = 100;
                    dom.data.reverseOrder = true;
                    dom.utility.mazeGeneration();
                }
            });

        },

        playPauseMazeGenEventListener: function () {
            let btn = $("#rew").next();  // play/pause button
            btn.click(function () {
                switch (btn.attr("id")) {
                    case "pause":
                        dom.utility.changePauseToPlay();
                        break;
                    case "play":
                        dom.data.delay = 500;
                        dom.utility.changePlayToPause();
                        dom.utility.mazeGeneration();
                        break;
                    default:
                        console.log("error: button not exists");
                }
            });
        },

        fastForwardMazeGenEventListener: function () {
            $("#ffwd").click(function () {
                if(dom.data.iterator < dom.data.mazeOrderLength) {
                    dom.utility.changePlayToPause();
                    $("#ffwd").addClass("activeBtn");
                    dom.data.delay = 100;
                    dom.utility.mazeGeneration();
                }
            });
        },

        jumpToEndEventListener: function () {
            $("#end").click(function () {
                dom.data.iterator = dom.data.mazeOrderLength;
                for (let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                    if (dom.data.mazeOrder.includes(i)) {
                        $(".maze div:nth-child(" + i + ")").removeClass("mazeWall").addClass("mazeCorridor");
                    }
                }
                dom.utility.changePauseToPlay();
            });
        },

        newMazeEventListener: function () {
            $("#newMaze").click(function () {
                dom.data.interrupted = true;
                dom.utility.resetMaze();
                apiData.getMazeData("dfs");
                dom.utility.changePlayToPause();
                dom.utility.mazeGeneration();
            });
        }
    },

    utility: {

        loadDemoArea: function (canvasString) {
            $(".demoArea").html(canvasString);
        },

        resetMaze: function () {
            $(".maze > div").removeClass("mazeCorridor").removeClass("mazeGenPointer").addClass("mazeWall");
            dom.dataFunctions.resetIterDelayOrder();
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
        changePauseToPlay: function () {
            $("#rew").next().attr("id", "play").children().removeClass("fa-pause").addClass("fa-play");
        },

        changePlayToPause: function () {
            $("#rew").next().attr("id", "pause").children().removeClass("fa-play").addClass("fa-pause");
        },

        mazeGeneration: function() {
            dom.data.interrupted = false;
            let timeOutId = setTimeout(function () {
                if(!dom.data.interrupted && dom.data.iterator <= dom.data.mazeOrderLength) {
                    if(dom.data.reverseOrder) {
                        dom.utility.changeBackCurrentAndPreviousMazeTileColor();
                        dom.data.iterator--;
                    } else {
                        dom.utility.changeCurrentAndPreviousMazeTileColor();
                        dom.data.iterator++;
                    }
                } else {
                    clearTimeout(timeOutId);
                    return;
                }
                if ( (dom.data.iterator < dom.data.mazeOrderLength && !dom.data.reverseOrder) ||
                    (dom.data.iterator >= 0 && dom.data.reverseOrder)) {
                    dom.utility.mazeGeneration();
                }  else {
                    dom.data.iterator = (dom.data.reverseOrder) ? 0 : dom.data.mazeOrderLength;  //TODO still goes over.
                    dom.utility.changePauseToPlay();
                    dom.utility.resetBtnFontColor();
                    clearTimeout(timeOutId);
                }
            }, dom.data.delay)
        },

        changeCurrentAndPreviousMazeTileColor: function () {
            let order = dom.data.mazeOrder;
            let iterator = dom.data.iterator;
            if(iterator === 1) {
                $(".maze div:nth-child(" + order[0] + ")").removeClass("mazeGenPointer").addClass("mazeCorridor");
            } else if(iterator > 1){
                $(".maze div:nth-child(" + order[iterator - 1] + ")").removeClass("mazeGenPointer").addClass("mazeCorridor");
            }
            if(iterator <= dom.data.mazeOrderLength -1 ) {
                $(".maze div:nth-child(" + order[iterator] + ")").removeClass("mazeWall").addClass("mazeGenPointer");
            }
        },

        changeBackCurrentAndPreviousMazeTileColor: function () {
            let order = dom.data.mazeOrder;
            let iterator = dom.data.iterator;
            let length = dom.data.mazeOrderLength;
            if(iterator === length) {
                $(".maze div:nth-child(" + order[length - 1] + ")").removeClass("mazeCorridor").addClass("mazeGenPointer");
            } else if(iterator > 0){
                $(".maze div:nth-child(" + order[iterator - 1] + ")").removeClass("mazeCorridor").addClass("mazeGenPointer");
            }
            if(iterator < length) {
                $(".maze div:nth-child(" + order[iterator] + ")").removeClass("mazeGenPointer").addClass("mazeWall");
            }
        }
    },

    dataFunctions: {

        saveMazeData: function (mazeData) {
            dom.data.mazeData = mazeData;
            dom.data.mazeOrder = mazeData.mazeOrder;
            dom.data.mazeOrderLength = dom.data.mazeOrder.length;
        },

        resetIterDelayOrder: function () {
            dom.data.iterator = 0;
            dom.data.delay = 500;
            dom.data.reverseOrder = false;
        }
    },
};
