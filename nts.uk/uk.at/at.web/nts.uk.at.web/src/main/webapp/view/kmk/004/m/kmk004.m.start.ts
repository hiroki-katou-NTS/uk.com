module nts.uk.com.view.kmk004.m {  
    __viewContext.ready(function() {
        let screenModel = new kmk004.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#baseStartDate').focus();
    });
}