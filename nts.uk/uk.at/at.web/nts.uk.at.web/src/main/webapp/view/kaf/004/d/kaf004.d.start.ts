module nts.uk.at.view.kaf004.d {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kaf004.d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}