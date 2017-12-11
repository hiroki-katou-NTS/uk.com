module nts.uk.com.view.cas013.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.screenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}