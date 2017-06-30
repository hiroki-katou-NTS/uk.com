module nts.uk.com.view.ccg013.f {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.com.view.ccg013.f.viewmodel.ScreenModel();
//        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
//        });
    });
}