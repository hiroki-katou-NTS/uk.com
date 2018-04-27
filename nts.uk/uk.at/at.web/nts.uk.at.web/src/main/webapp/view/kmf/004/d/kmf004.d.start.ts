module nts.uk.at.view.kmf004.d {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.checkUpdate(true)){
                $("#inpPattern").focus();
            }else{
                $("#inpCode").focus();
            }
        });
    });
}      