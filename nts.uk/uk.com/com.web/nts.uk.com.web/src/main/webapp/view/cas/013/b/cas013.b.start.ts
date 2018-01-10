module nts.uk.com.view.cas013.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#search").focus();
        $("#search").keydown(function(event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                $("#searchButton").click();
            }
        });
    });
}