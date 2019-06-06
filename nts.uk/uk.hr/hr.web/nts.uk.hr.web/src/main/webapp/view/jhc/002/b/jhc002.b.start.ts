module nts.uk.hr.view.jhc002.b {
    import setShared = nts.uk.ui.windows.setShared;
    __viewContext.ready(function() {
        __viewContext.transferred.ifPresent(data => {
            setShared("DataShareCareerToBScreen", data);
        });
        var screenModel = __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}