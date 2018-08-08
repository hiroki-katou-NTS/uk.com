module nts.uk.com.view.cli003.i {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
            $('#multi-list_container').focus();
    });
}