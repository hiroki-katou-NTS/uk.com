module nts.uk.at.view.kmk004.e {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#checkbox-manage-display').focus();
        });
    });
}