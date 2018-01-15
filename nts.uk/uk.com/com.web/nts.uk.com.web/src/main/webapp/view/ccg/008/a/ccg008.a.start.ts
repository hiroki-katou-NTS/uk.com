module nts.uk.com.view.ccg008.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
        
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}