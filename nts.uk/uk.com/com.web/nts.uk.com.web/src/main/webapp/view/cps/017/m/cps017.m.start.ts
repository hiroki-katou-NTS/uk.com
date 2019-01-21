module nts.uk.com.view.cps017.m {  
    __viewContext.ready(function() {
        let screenModel = new cps017.m.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        $('#basedate').focus();
    });
}