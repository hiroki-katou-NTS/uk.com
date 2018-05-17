module nts.uk.at.view.kdm001.b {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdm001.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#B2_1').focus();
        });
    });
}
