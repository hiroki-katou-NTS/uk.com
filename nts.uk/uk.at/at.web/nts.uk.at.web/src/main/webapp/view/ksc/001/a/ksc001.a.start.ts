module nts.uk.at.view.ksc001.a {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#buttonCreate').focus();
        });
    });
}
