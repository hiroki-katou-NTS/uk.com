module nts.uk.at.view.kmk008.h {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#checkboxEmp").focus(); 
        });
    });
}