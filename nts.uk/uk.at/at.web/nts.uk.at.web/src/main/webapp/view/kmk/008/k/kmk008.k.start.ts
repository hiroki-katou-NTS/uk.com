module nts.uk.at.view.kmk008.k {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.isUpdate){
                $("#txt-year-error-time").focus(); 
            } else {
                $("#txt-year").focus();
            }
        });
    });
}