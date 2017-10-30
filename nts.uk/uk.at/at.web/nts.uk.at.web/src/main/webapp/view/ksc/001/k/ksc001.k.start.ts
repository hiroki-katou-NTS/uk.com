module nts.uk.at.view.ksc001.k {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.k.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#btn-close').focus();
        });
    });
}