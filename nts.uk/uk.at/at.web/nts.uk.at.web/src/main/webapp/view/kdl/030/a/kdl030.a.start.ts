module nts.uk.at.view.kdl030.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl030.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#sendAtr_0').focus();
        });
    });
}
