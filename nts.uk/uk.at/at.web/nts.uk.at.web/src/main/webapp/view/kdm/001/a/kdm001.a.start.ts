module nts.uk.at.view.kdm001.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdm001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#emp-component').focus();
        });
    });
}
