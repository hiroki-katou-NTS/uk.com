module nts.uk.at.view.kdl006.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
