module nts.uk.at.view.kmk007.a {
    var __viewContext = window['__viewContext'] || {};
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#clear-button').focus();
        });
    });
} 