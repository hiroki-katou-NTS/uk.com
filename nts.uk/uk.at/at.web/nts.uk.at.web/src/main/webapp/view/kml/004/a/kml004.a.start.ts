module nts.uk.at.view.kml004.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.lstCate().length > 0){
                $("#nameCtg").focus();
            }else{
                screenModel.checkUpdate(true);
                $("#code-text").focus(); 
            }
        });
    });  
}   