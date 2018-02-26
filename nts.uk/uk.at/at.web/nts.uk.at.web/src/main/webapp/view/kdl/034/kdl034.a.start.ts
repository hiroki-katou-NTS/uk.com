module nts.uk.at.view.kdl001.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#inputStartTime").focus();
        });
    });
}
