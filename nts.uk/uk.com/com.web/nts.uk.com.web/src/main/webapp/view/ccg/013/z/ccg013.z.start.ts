module nts.uk.com.view.ccg013.z {
    __viewContext.ready(function() {
        var screenModel = new z.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
        });
    }); 
}