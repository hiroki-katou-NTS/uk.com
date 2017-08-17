module nts.uk.at.view.kmf004.h {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            $("#inpCode").focus();
    });
} 