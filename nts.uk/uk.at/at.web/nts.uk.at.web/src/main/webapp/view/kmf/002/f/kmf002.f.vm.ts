module nts.uk.at.view.kmf002.f {
    
    import viewModelTabA = nts.uk.at.view.kmf002.a.viewmodel;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export module viewmodel {
        export class ScreenModel {
            
            selectUnitCheck: KnockoutObservable<boolean>;
            typeSelectUnitRadioBox: KnockoutObservableArray<any>;
            valueDefaultTypeSelect: KnockoutObservable<number>;
            selectEmployee: KnockoutObservable<boolean>;
            
            constructor() {
                let _self = this;
                _self.selectUnitCheck = ko.observable(false);
                _self.typeSelectUnitRadioBox = ko.observable([
                                                                    {"id":0,"name": nts.uk.resource.getText("Com_Employment"),"enable":true},
                                                                    {"id":1,"name": nts.uk.resource.getText("Com_Workplace"),"enable":true}
                                                                ]);
                _self.valueDefaultTypeSelect = ko.observable(1);
                _self.selectEmployee = ko.observable(true);
            }
            
            public start_page(): JQueryPromise<any> {
                var dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
            
            public closeSaveDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
        
    }
}