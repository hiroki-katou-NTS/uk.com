module nts.uk.at.view.kdp010.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
