module nts.uk.com.view.cas013.m {  
    __viewContext.ready(function() {
        let screenModel = new cas013.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#basedate').focus();
    });
}