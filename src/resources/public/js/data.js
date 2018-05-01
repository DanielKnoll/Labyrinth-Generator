apiData = {
    getMazeData: function (mazeType) {
        $.getJSON(`/api/generate/` + mazeType, function (mazeData) {
            dom.dataFunctions.saveMazeData(mazeData);
            dom.initFunctions.createGrid();
        });
    },
    
    postMazeData: function (type, width, height) {
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
    }
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
