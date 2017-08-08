module nts.uk.at.view.kdw009.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
//        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#inpCode").focus();
//        });
    });
}