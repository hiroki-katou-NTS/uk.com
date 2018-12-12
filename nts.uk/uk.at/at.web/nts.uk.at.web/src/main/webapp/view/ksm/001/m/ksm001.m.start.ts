module nts.uk.com.view.ksm001.m {  
    __viewContext.ready(function() {
        let screenModel = new ksm001.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#basedate').focus();
    });
}