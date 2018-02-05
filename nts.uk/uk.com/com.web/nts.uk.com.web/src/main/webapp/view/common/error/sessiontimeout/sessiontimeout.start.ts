module common.error.system {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel);
    });
    
    class ScreenModel {
        constructor() {
        }
        
        gotoLogin() {
            nts.uk.ui.windows.rgc().nts.uk.request.login.jumpToUsedLoginPage();
        }
    }
}