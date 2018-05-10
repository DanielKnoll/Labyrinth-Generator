apiData = {
    getMazeData: function (mazeType) {
        $.getJSON(`/api/generate/` + mazeType, function (mazeData) {
            dom.dataFunctions.saveMazeData(mazeData);
            dom.initFunctions.createGrid();
        });
    },
    
    getMazeDataPost: function (type, width, height) {
        $.postJSON("/api/generate/",
            {
                algoType: type,
                mazeColNum: width,
                mazeRowNum: height
            }
        ).done(function( mazeData ) {
            dom.dataFunctions.saveMazeData(mazeData);
            dom.initFunctions.createGrid();
            $(".json").html(JSON.stringify(dom.data.mazeData, null, 2));
        });
    },

    getMazeInfo: function (mazeType) {
        $.postJSON( "/api/info/", { algoType: mazeType })
            .done(function( infoData ) {
                dom.dataFunctions.saveInfo(infoData);
            });
    },

    getMazeSolveOrder: function (maze, start, end) {
        $.postJSON("/api/solve/",
            {
                maze: maze,
                start: start,
                end: end
            }
        ).done(function( solutionOrder ) {
            dom.utility.showMazeSolve(solutionOrder);
        });
    },
};

$.postJSON = function(url, data, callback) {
    return jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': url,
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback
    });
};
