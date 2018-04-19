module nts.uk.at.view.kwr001.c {
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();
//        screenModel.startPage().done(function() {
//            __viewContext.bind(screenModel);
//        });
        __viewContext.bind(screenModel);
    });
}
