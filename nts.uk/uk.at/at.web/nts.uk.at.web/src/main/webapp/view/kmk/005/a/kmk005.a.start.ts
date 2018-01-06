module nts.uk.at.view.kmk005.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#openTimeItemBtn").focus();
        });
    });
}
