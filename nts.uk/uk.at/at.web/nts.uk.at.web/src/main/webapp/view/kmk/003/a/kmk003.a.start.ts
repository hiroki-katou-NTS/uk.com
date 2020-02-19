module nts.uk.at.view.kmk003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#search-daily-atr').focus();
        });
    });
}