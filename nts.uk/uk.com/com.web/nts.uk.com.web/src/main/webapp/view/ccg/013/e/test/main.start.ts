module nts.uk.com.view.ccg013.e.test {
    __viewContext.ready(function() {
        var screenModel = new e.test.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}