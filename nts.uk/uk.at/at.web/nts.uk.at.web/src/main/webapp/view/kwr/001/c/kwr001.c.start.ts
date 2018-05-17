module nts.uk.at.view.kwr001.c {
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();

//        _.defer(function() {
//            screenModel.startPage().done(function() {
//                __viewContext.bind(screenModel);
//            });
//        });
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
