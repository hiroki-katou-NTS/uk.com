module nts.uk.at.view.kal002.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            $('#idFocus').focus();
            __viewContext.bind(screenModel);
        });
    });
}