module nts.uk.at.view.cdl025.test.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    export class ScreenModel {

        currentCode: KnockoutObservable<string>;
        currentCodes: KnockoutObservableArray<string>;
        listCodeSelect: KnockoutObservableArray<string>;
        constructor() {
            var self = this;
            self.currentCode = ko.observable("");
            self.currentCodes = ko.observableArray([]);
            self.listCodeSelect = ko.observableArray([]);
            
        }
        
        openCDL025() {
            var self = this;
            let param = {
                    roleType : 1,
                    multiple : true
                };
            nts.uk.ui.windows.setShared("paramCdl025",param);
            nts.uk.ui.windows.sub.modal("/view/cdl/025/index.xhtml").onClosed(function(){
                let data = nts.uk.ui.windows.getShared("dataCdl025"); 
                var arr = [];
                for(var i = 0; i < data.currentCode.length; i++) {
                    arr.push(""+data.currentCode[i]);
                }
                self.listCodeSelect(arr);    
            });
        }
    }
    
}
