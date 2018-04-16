module nts.uk.at.view.kwr001.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
//        screenModel.startPage().done(function() {
//            __viewContext.bind(screenModel);
//        });
        __viewContext.bind(screenModel);
    });
}
