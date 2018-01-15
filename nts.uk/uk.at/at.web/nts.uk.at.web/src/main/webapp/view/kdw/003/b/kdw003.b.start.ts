module nts.uk.at.view.kdw003.b {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        let param = nts.uk.ui.windows.getShared("paramToGetError");
        screenModel.startPage(param).done(function() {
            __viewContext.bind(screenModel);
        });
    });
}