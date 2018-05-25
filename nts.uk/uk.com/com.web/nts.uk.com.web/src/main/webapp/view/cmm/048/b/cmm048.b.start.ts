module nts.uk.com.view.cmm048.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => $('[tabindex=1]').focus());
        });
    });
}