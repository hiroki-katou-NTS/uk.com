module nts.uk.pr.view.kmf001.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
             $('#priority' ).focus();
        });
    });
}