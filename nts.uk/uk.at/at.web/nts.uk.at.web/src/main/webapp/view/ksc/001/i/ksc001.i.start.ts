module nts.uk.at.view.ksc001.i {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.i.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#btn-close').focus();
        });
    });
}