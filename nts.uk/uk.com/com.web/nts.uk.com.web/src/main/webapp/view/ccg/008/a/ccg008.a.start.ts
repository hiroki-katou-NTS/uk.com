module nts.uk.com.view.ccg008.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
//        var dScreenModel = new nts.uk.com.view.ccg008.d.alertmessage.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
//            dScreenModel.start();
        });
    }); 
}