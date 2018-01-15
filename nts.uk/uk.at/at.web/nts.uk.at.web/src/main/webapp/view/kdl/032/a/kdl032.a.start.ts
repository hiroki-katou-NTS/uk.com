module nts.uk.at.view.kdl032.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl032.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
           $("#single-list_container").focus();
        });
    });
}