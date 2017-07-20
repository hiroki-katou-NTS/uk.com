module nts.uk.pr.view.ksm006.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.pr.view.ksm006.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}