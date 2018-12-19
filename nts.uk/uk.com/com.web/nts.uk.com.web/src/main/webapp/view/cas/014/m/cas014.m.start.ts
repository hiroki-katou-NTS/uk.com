module nts.uk.com.view.cas014.m {  
    __viewContext.ready(function() {
        let screenModel = new cas014.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#basedate').focus();
    });
}