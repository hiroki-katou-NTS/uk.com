module nts.uk.at.view.cdl025.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {

        currentCode: KnockoutObservable<string>;
        currentCodes: KnockoutObservableArray<string>;
        constructor() {
            var self = this;
            self.currentCode = ko.observable("");
            self.currentCodes = ko.observableArray([]);
            
        }
        
        openCDL025() {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/cdl/025/index.xhtml").onClosed(function(){
                let param = nts.uk.ui.windows.getShared("paramCdl025");
                if(param != null){
                    if(param.multiple)
                        self.currentCodes = param.currentCode;
                    else
                        self.currentCode = param.currentCode;
                }                    
            });
            
        }
    }
    
}
