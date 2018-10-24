module nts.uk.at.view.kdw009.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        nts.uk.ui.block.invisible();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.lstBusinessType().length == 0){
                // $("#inpCode").focus();
            }else{
                setTimeout(function(){ $("#inpPattern").focus();}, 500);
            }
        }).always(() => {
            nts.uk.ui.block.clear();
        });
    });
}