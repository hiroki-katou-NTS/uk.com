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
        
        constructor() {
            this.details = ko.observable("");
        }
        
        gotoLogin() {
            nts.uk.ui.windows.rgc().nts.uk.request.login.jumpToUsedLoginPage();
        }
    }
}