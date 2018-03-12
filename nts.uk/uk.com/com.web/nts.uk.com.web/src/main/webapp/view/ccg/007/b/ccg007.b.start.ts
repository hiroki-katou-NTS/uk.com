module nts.uk.pr.view.ccg007.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#login-id-inp').focus();
        });
    });
}