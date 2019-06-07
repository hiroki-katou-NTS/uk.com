module nts.uk.hr.view.jhc002.a {
    import setShared = nts.uk.ui.windows.setShared;
    __viewContext.ready(function() {
        __viewContext.transferred.ifPresent(data => {
            setShared("DataShareCareerToAScreen", data, false);
            nts.uk.sessionStorage.removeItem("nts.uk.request.STORAGE_KEY_TRANSFER_DATA");
        });
        var screenModel =  __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}