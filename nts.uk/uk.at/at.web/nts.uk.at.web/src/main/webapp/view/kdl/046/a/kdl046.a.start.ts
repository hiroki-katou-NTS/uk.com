module  nts.uk.at.view.kdl046.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel); 
            $("#switchButtonkdl046").focus();
        });
    });
}