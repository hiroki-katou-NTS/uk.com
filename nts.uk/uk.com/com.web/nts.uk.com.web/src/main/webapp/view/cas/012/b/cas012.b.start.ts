module nts.uk.com.view.cas012.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#search").keyup(function(event) {
            if (event.keyCode == 13) {
                $("#searchButton").click();
            }
        });
    });
}