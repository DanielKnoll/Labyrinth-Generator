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
            dom.initFunctions.createGrid();
            $(".gridGenButtons").hide();
            $(".playerBtns").hide();
            return $("section.demoArea").html();
        },

        createGrid: function () {
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
                dom.dataFunctions.resetIterAndDelay();
                $(this).hide();
                $(".playerBtns").show();
                $(".gridGenButtons").show(200);  // Todo another animation
                dom.utility.mazeGeneration();
            });
        },

        anyMazeGenBtnEventListener: function () {
            let interruptorBtns = ["start", "rew", "pause", "ffwd", "end"];
            $(".playerBtns button").click(function () {
                dom.utility.resetBtnFontColor();

                if(interruptorBtns.includes($(this).attr("id"))) {
                    dom.data.interrupted = true;
                }

                // Todo Reset Maze
            });
        },

        jumpToStartEventListener: function () {
            $("#start").click(function () {
                dom.utility.resetMaze();
                dom.utility.changePauseToPlay();
            });
        },

        rewindMazeGenEventListener: function () {
            $("#rew").click(function () {
                if(dom.data.iterator < dom.data.mazeOrder.length) {
                    dom.utility.changePlayToPause();
                    $("#rew").addClass("activeBtn");
                    dom.data.delay = 200;
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
                        if (dom.data.iterator === 0 || dom.data.iterator >= dom.data.mazeOrder.length) {
                            dom.utility.resetMaze();
                        }
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
                if(dom.data.iterator < dom.data.mazeOrder.length) {
                    dom.utility.changePlayToPause();
                    $("#ffwd").addClass("activeBtn");
                    dom.data.delay = 100;
                    dom.utility.mazeGeneration();
                }
            });
        },

        jumpToEndEventListener: function () {
            $("#end").click(function () {
                dom.utility.resetMaze();
                dom.data.iterator = dom.data.mazeOrder.length;
                for (let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                    if (dom.data.mazeOrder.includes(i)) {
                        $(".maze div:nth-child(" + i + ")").addClass("mazeCorridor");
                    }
                }
                dom.utility.changePauseToPlay();
            });
        },
    },

    utility: {

        loadDemoArea: function (canvasString) {
            $(".demoArea").html(canvasString);
        },

        resetMaze: function () {
            $(".maze > div").removeClass("mazeCorridor").removeClass("mazeGenPointer").addClass("mazeWall");
            dom.dataFunctions.resetIterAndDelay();
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
            let timeOutId = setTimeout(function () {
                if(!dom.data.interrupted && dom.data.iterator <= dom.data.mazeOrder.length) {
                    dom.utility.changeCurrentAndPreviousMazeTileColor();
                    dom.data.iterator++;  // Todo reverse
                } else {
                    clearTimeout(timeOutId);
                    return;
                }
                if (dom.data.iterator < dom.data.mazeOrder.length) {
                    dom.utility.mazeGeneration();
                }  else {
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
