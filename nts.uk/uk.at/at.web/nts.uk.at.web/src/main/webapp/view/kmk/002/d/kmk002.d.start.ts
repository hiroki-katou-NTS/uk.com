module nts.uk.at.view.kmk002.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#radio-left-selection").focus();
        });
    });
}
