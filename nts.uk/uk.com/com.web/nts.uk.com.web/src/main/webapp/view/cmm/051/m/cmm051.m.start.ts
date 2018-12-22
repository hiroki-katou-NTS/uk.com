module nts.uk.com.view.cmm051.m {  
    __viewContext.ready(function() {
        let screenModel = new cmm051.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#basedate').focus();
    });
}