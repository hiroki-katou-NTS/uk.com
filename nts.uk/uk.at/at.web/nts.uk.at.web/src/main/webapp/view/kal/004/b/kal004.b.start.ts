module nts.uk.com.view.kal004.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#search").focus();
        $("#search").keyup(function(event) {
            if (event.keyCode == 13) {
                $("#searchButton").click();
            }
        });
    });
}