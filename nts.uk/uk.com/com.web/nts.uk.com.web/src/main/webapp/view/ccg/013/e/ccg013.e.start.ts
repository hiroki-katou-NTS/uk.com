module nts.uk.com.view.ccg013.e {
    __viewContext.ready(function() {
        var screenModel = new e.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}