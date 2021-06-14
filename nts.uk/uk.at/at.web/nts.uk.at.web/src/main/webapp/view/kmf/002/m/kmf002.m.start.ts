module nts.uk.at.view.kmf002.m {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $( "#category" ).focus();
        });
    });
}