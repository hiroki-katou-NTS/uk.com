module nts.uk.at.view.kdw009.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.lstBusinessType().length == 0){
                $("#inpCode").focus();
            }else{
                setTimeout(function(){ $("#inpPattern").focus();}, 500);
            }
        });
    });
}