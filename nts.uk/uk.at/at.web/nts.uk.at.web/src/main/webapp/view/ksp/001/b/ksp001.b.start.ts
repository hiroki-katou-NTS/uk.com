module nts.uk.at.view.ksp001.b {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksp001.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $($('div.swBtn')[0]).focus();
        });
    });
}