apiData = {
    getMazeData: function (mazeType) {
        $.getJSON(`http://localhost:60001/api/generate/` + mazeType, function (mazeData) {
            dom.dataFunctions.saveMazeData(mazeData);
        });
    },
    
    postMazeData: function (type, width, height) {
        $.jpost("http://localhost:60001/api/generate/",
            {
                algoType: type,
                mazeColNum: width,
                mazeRowNum: height
            }
        ).then(res => {
            let response = res;
            console.log(response);
        });
    },

};

$.extend({
    jpost: function(url, body) {
        return $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(body),
            contentType: 'application/json',
        });
    }
});