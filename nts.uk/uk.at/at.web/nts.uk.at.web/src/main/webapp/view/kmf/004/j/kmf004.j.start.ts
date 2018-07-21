module nts.uk.at.view.kmf004.j {
    var __viewContext = window['__viewContext'] || {};
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#data-items").focus();
        });
    });
}