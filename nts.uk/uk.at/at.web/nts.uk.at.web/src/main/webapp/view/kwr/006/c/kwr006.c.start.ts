module nts.uk.at.view.kwr006.c {
    import blockUI = nts.uk.ui.block;
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        })
    });
}