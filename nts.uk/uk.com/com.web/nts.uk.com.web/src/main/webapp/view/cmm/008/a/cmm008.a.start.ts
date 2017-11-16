module nts.uk.com.view.cmm008.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
//            nts.uk.ui.confirmSave(screenModel.dirtyChecker);
            __viewContext.bind(screenModel);
            // Load Component
//            $('#emp-component').ntsListComponent(screenModel.listComponentOption);
        });
    });
}