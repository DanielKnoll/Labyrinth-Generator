dom = {
    data: {
        demoAreaString: "",
        mazeData: "",
        mazeColNum: 0,
        mazeRowNum: 0,
        mazeOrder: [],
        mazeOrderLength: 0,
        iterator: 0,
        delay: 500,
        interrupted: false,
        reverseOrder: false,
        infoData: "",
    },

    initFunctions: {

        createGrid: function () {
            let mazeDom = $(".maze");
            let tiles = "";

            $(".infoBtnsGrid").hide();
            $(".playerBtns").hide();
            $("#generate").show();

            mazeDom.css("grid-template-columns", "repeat(" + dom.data.mazeColNum + ", auto)");
            for(let i = 0; i < (dom.data.mazeColNum * dom.data.mazeRowNum); i++) {
                tiles += `<div class="mazeWall"></div>`;
            }
            mazeDom.html(tiles);
        },

        loadEventListeners: function () {
            dom.eventListeners.navigationEventListener();
            dom.eventListeners.generateMazeBtnEventListener();
            dom.eventListeners.anyMazeGenBtnEventListener();
            dom.eventListeners.jumpToStartEventListener();
            dom.eventListeners.rewindMazeGenEventListener();
            dom.eventListeners.playPauseMazeGenEventListener();
            dom.eventListeners.fastForwardMazeGenEventListener();
            dom.eventListeners.jumpToEndEventListener();
            dom.eventListeners.newMazeEventListener();
            dom.eventListeners.infoBtnsEventListener();
        },
    },

    eventListeners: {

        navigationEventListener: function () {
            $("nav li").click(function () {
                let selectedMenu = $(this);
                let selectedMenuId = selectedMenu.attr("id");
                dom.data.interrupted = true;
                $("nav li").removeClass("activeNav");
                selectedMenu.addClass("activeNav");
                $(".infoBtnsGrid").hide();
                $(".appInfoSection").html("");

                switch (selectedMenuId) {
                    case "dfs":
                        apiData.getMazeData("0&18&10"); // TODO generate?wall=0&...
                        break;
                    case "cellular":
                        dom.data.mazeColNum = 50;
                        dom.data.mazeRowNum = 30;
                        dom.data.mazeOrder = [];
                        break;
                    default:
                        apiData.getMazeData("1&19&13");
                }
                $(".demoArea > .title").html(dom.data.mazeData.algoName);
                dom.data.mazeOrderLength = dom.data.mazeOrder.length;
                dom.initFunctions.createGrid();
            });
        },

        generateMazeBtnEventListener: function () {
            $("#generate").click(function () {
                dom.dataFunctions.resetIterDelayOrder();
                $(this).hide();
                $(".playerBtns").slideDown(100);
                $(".infoBtnsGrid").slideDown(200);
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
                dom.utility.changePauseToPlay();  // Todo any btn
            });
        },

        rewindMazeGenEventListener: function () {
            $("#rew").click(function () {
                if(dom.data.iterator <= dom.data.mazeOrderLength + 1) {
                    dom.utility.changePlayToPause();  // Todo any btn
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
                        dom.utility.changePauseToPlay();  // Todo any btn
                        break;
                    case "play":
                        dom.data.delay = 500;
                        dom.utility.changePlayToPause();  // Todo any btn
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
                    dom.utility.changePlayToPause();  // Todo any btn
                    $("#ffwd").addClass("activeBtn");
                    dom.data.delay = 100;
                    dom.utility.mazeGeneration();
                }
            });
        },

        jumpToEndEventListener: function () {
            $("#end").click(function () {
                let tile;
                for (let i = dom.data.iterator; i < dom.data.mazeOrderLength; i++) {
                    tile = dom.data.mazeOrder[i];
                    $(".maze div").eq(tile).removeClass("mazeWall").addClass("mazeCorridor");
                }
                dom.utility.changePauseToPlay();  // Todo any btn
            });
        },

        newMazeEventListener: function () {
            $("#newMaze").click(function () {
                dom.data.interrupted = true;
                dom.utility.resetMaze();
                apiData.getMazeData("0&18&10");
                dom.utility.changePlayToPause();  // Todo any btn
                dom.utility.mazeGeneration();
            });
        },

        infoBtnsEventListener: function () {
            /**
             * Info Area toggle.
             * If you click a button and it is not active there is an active button, takes away class.
             * If the area is not empty it slides up. And after the animation is done clears area.
             * If you click a button which has info and it is active then it removes class
             * otherwise it becomes active button and loads info with animation.
             */
            let showInfoBtns = ["showApi", "showCode", "showInfo"];
            let infoSection = $(".appInfoSection");
            let animationSpeed = 400;
            $(".infoBtnsGrid > div").click(function () {  // TODO simplify
                let clickedBtn = $(this);
                if( !clickedBtn.hasClass("activeInfoBtn") && $(".infoBtnsGrid > div").hasClass("activeInfoBtn") ){
                    $(".infoBtnsGrid  div.activeInfoBtn").removeClass("activeInfoBtn");
                }
                if(infoSection.html().toString().length > 0) {
                    infoSection.slideUp(animationSpeed);
                    setTimeout(function (){
                        infoSection.html("");
                    }, animationSpeed);
                }
                if(showInfoBtns.includes(clickedBtn.attr("id"))) {
                    if (clickedBtn.hasClass("activeInfoBtn")) {
                        clickedBtn.removeClass("activeInfoBtn");
                    } else {
                        clickedBtn.addClass("activeInfoBtn");
                        setTimeout(function () {
                            dom.utility.loadInfo(clickedBtn.attr("id"));
                        }, animationSpeed);
                        infoSection.slideDown(animationSpeed);
                    }
                }
            });
        }
    },

    utility: {

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
                $(".maze div").eq(order[0]).removeClass("mazeGenPointer").addClass("mazeCorridor");
            } else if(iterator > 1){
                $(".maze div").eq(order[iterator - 1]).removeClass("mazeGenPointer").addClass("mazeCorridor");
            }
            if(iterator <= dom.data.mazeOrderLength -1 ) {
                $(".maze div").eq(order[iterator]).removeClass("mazeWall").addClass("mazeGenPointer");
            }
        },

        changeBackCurrentAndPreviousMazeTileColor: function () {
            let order = dom.data.mazeOrder;
            let iterator = dom.data.iterator;
            let length = dom.data.mazeOrderLength;
            if(iterator === length) {
                $(".maze div").eq(order[length - 1]).removeClass("mazeCorridor").addClass("mazeGenPointer");
            } else if(iterator > 0){
                $(".maze div").eq(order[iterator - 1]).removeClass("mazeCorridor").addClass("mazeGenPointer");
            }
            if(iterator < length) {
                $(".maze div").eq(order[iterator]).removeClass("mazeGenPointer").addClass("mazeWall");
            }
        },

        loadInfo: function(btnId) {
            let infoSection = $(".appInfoSection");
            switch (btnId) {
                case "showApi":
                    infoSection.html(dom.htmlStructures.apiInfo);
                    $(".apiValues").html(`wall=0&amp;algo=0&amp;width=18&amp;height=10`);
                    $(".json").html(JSON.stringify(dom.data.mazeData, null, 2));
                    break;
                case "showCode":
                    infoSection.html(dom.htmlStructures.codeInfo);
                    break;
                case "showInfo":
                    infoSection.html(dom.htmlStructures.algoInfo);
                    $(".algoInfoText > .title").html(dom.data.mazeData.algoName + " algorithm");
                    break;
                default:
            }
        },
    },

    dataFunctions: {

        saveMazeData: function (mazeData) {
            dom.data.mazeData = mazeData;
            dom.data.mazeOrder = mazeData.mazeOrder;
            dom.data.mazeOrderLength = dom.data.mazeOrder.length;
            dom.data.mazeColNum = mazeData.mazeColNum;
            dom.data.mazeRowNum = mazeData.mazeRowNum;
        },

        resetIterDelayOrder: function () {
            dom.data.iterator = 0;
            dom.data.delay = 500;
            dom.data.reverseOrder = false;
        }
    },

    htmlStructures: {

        apiInfo: `
                <h3 class="title">API Info</h3>
                    <div class="snippet">
                        <code>http://www.future-domain-name.hu/api/generate?<span class="apiValues"></span></code>
                    </div>
                    <div class="apiInfo">
                        <ol>
                            <li>input: 0: thick wall / 1: thin wall</li>
                            <li>input: 0-4 generation algorithms</li>
                            <li>input: maze width (3-100)</li>
                            <li>input: maze height (3-100)</li>
                        </ol>
                        <div class="form">
                            <form>
                                <select id="wallType" name="wall">
                                    <option value="0">thick wall</option>
                                    <option value="1">thin wall</option>
                                </select>
                                <select id="algoType" name="algo">
                                    <option value="0">DFS</option>
                                    <option value="1">Kruskal</option>
                                    <option value="1">Random</option>
                                </select>
                                <input type="number" value="3" max="100"/>
                                <input type="number" value="3" max="100"/>
                                <input class="btn singleBtn formSubmit" type="submit" value="Send"/>
                            </form>
                        </div>
                    </div>
                    <div class="snippet">
                        <pre><code class="json"></code></pre>
                    </div>
        `,

        algoInfo: `
                <h3 class="title">Algorithm Info</h3>
                <div class="algoInfo">
                    <article class="algoInfoText">
                        <h4 class="title"></h4>
                        <div class="text">
                            <p>Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures. One starts at the root (selecting some arbitrary node as the root in the case of a graph) and explores as far as possible along each branch before backtracking.</p>
                            <p>A version of depth-first search was investigated in the 19th century by French mathematician Charles Pierre Trémaux[1] as a strategy for solving mazes.</p>
                            <p>Nunc condimentum, nulla in faucibus commodo, elit massa pharetra nibh, at tincidunt libero dolor quis augue. Donec pulvinar consectetur tortor, eget gravida ligula molestie at. Aenean ullamcorper tempor fermentum. Vestibulum metus ante, aliquam sed ligula vitae, auctor tempus quam. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Cras vitae imperdiet justo, sed egestas nulla. Nunc sit amet malesuada mauris, sed pellentesque arcu. Sed venenatis mattis mi ac tincidunt. Pellentesque nec justo ullamcorper, cursus massa at, condimentum justo. Mauris pharetra ligula in nibh efficitur, ut facilisis nisi pulvinar. Nullam eu consectetur mi. In in dapibus sem, a pharetra sem. Pellentesque in blandit magna. Nulla in orci finibus, ornare massa ut, mollis velit.</p>
                            <p>Etiam condimentum congue est, eget accumsan enim ornare eget. Morbi sed dui sit amet purus hendrerit egestas. Mauris at tellus sit amet dolor commodo suscipit sit amet eget elit. Curabitur nec diam id risus pulvinar euismod. Sed et rutrum lacus. Fusce sit amet augue auctor nulla semper luctus eget at dolor. Vivamus egestas tincidunt tincidunt. Donec sapien velit, venenatis eu tincidunt nec, consequat eu lacus. Nulla finibus sodales mauris, sed consequat urna hendrerit sed. Morbi eleifend consectetur imperdiet. Morbi eu venenatis mauris. Morbi non mi vel orci ultricies semper. Pellentesque rutrum, odio sit amet auctor accumsan, enim arcu vestibulum quam, vel fringilla leo odio eget lorem. Sed nisl purus, porttitor et sagittis vel, pharetra lacinia lectus.</p>
                        </div>
                    </article>
                    <aside class="algoInfoAside">
                        <h4 class="title">Images:</h4>
                        <div class="images">
                            <img src="img/300px-Depth-first-tree.svg.png"/>
                            <img src="img/300px-Depth-first-tree.svg.png"/>
                        </div>
                    </aside>
                </div>
        `,

        codeInfo: `
                <h3 class="title">Class Name</h3>
                    <div class="snippet">
                        <pre><code class="algoCode">code will be here</code></pre>
                    </div>
        `
    }
};
