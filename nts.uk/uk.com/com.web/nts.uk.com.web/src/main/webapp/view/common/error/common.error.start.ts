module common.error.system {
    __viewContext.ready(function() {
        var screenModel = new ScreenModel();
        __viewContext.bind(screenModel);

        var errorInfo = nts.uk.ui.windows.getShared("errorInfo");
        if (!nts.uk.util.isNullOrUndefined(errorInfo)) {
            screenModel.details(errorInfo.errorMessage + "\r\n" + errorInfo.stackTrace);
        }
    });
    
    class ScreenModel {
        
        details: KnockoutObservable<string>;
        isDebugMode: KnockoutObservable<boolean>;
        
        constructor() {
            this.details = ko.observable("");
            if(__viewContext.program.isDebugMode == true){
                this.isDebugMode = ko.observable(true);    
            } else {
                this.isDebugMode = ko.observable(false);    
            }
        }
        
        gotoLogin() {
            nts.uk.ui.windows.rgc().nts.uk.request.login.jumpToUsedLoginPage();
        }
    }
}