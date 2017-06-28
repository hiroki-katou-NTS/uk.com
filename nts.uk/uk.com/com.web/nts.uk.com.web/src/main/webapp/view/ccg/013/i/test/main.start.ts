module nts.uk.com.view.ccg013.i.test {
    __viewContext.ready(function() {
        var screenModel = new i.test.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}