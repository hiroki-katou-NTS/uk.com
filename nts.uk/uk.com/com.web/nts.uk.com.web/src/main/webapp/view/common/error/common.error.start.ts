module common.error.system {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel);

    });
    
    class ScreenModel {
        
        gotoLogin() {
            nts.uk.request.login.jumpToUsedLoginPage();
        }
    }
}