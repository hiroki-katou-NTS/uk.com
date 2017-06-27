module nts.uk.com.view.ccg013.d.test {
    __viewContext.ready(function() {
        var screenModel = new d.test.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}