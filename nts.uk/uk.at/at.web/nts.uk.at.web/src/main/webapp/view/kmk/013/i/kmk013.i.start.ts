module nts.uk.at.view.kmk013.i {
    __viewContext.ready(function() {
        var screenModel = new i.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
