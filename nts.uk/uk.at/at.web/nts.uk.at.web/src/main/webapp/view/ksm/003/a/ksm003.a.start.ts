module  nts.uk.at.view.ksm003.a {
    __viewContext.ready(function(){
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
//            if(screenModel.checkModel()){  
//                $("#inpPattern").focus();
//            }else{
//                $("#inpCode").focus();
//            }
        });
    });
}