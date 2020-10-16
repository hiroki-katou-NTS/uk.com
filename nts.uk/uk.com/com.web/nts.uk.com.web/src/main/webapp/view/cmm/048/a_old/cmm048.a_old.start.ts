module nts.uk.com.view.cmm048.a_old {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => screenModel.setInitialFocus());
            $('#tab-panel').removeAttr('tabIndex');
        });
    });
}