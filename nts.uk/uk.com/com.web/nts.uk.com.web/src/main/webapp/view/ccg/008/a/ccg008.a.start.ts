module nts.uk.com.view.ccg008.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
        
        screenModel.start().done(function() {
            __viewContext.bind(screenModel); 
            
            var transferData = __viewContext.transferred.value;
            if (transferData && "login" == transferData.screen) {
                var dScreenModel = new nts.uk.com.view.ccg008.d.alertmessage.ScreenModel();
                dScreenModel.start();
            }
        });
    }); 
}