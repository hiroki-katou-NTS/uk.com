module nts.uk.com.view.ksp001.a {

    __viewContext.ready(function() {
        let screenModel = new nts.uk.com.view.ksp001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#menuSet').focus();
        });
    });
}