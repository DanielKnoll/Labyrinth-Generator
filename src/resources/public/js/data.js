apiData = {
    getMazeData: function (mazeType) {
        $.getJSON(`http://localhost:60001/` + mazeType, function (mazeData) {
            dom.dataFunctions.saveMazeDate(mazeData);
        });
    }
};
