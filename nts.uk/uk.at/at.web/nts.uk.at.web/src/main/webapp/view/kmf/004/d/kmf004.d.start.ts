module nts.uk.at.view.kmf004.d {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            
            if(screenModel.editMode()){
                setTimeout(function() { $("#inpPattern").focus();}, 200);
            } else {
                setTimeout(function() {
                    $("#inpCode").focus(); 
                    nts.uk.ui.errors.clearAll();
                }, 200);
            }
        });
    });
}      