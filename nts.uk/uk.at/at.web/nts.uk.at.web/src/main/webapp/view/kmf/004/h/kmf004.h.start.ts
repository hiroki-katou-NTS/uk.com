module nts.uk.at.view.kmf004.h {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.lstRelationship().length>0){
                $("#inpPattern").focus();
            }
            else{
                $("#inpCode").focus();
            }
        });
    });
} 