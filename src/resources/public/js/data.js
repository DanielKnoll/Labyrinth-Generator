apiData = {
    getMazeData: function (mazeType) {
        $.getJSON(`http://0.0.0.0:60001/` + mazeType, function (mazeData) {
            dom.saveMazeDate(mazeData);
        });
    }
};
