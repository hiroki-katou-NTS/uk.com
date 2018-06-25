module nts.uk.at.view.kwr001.b {
    export module viewmodel {
        
        import service = nts.uk.at.view.kwr001.b.service;
        
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            currentCodeList: KnockoutObservableArray<any>;
            showTableInIE: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;
                self.items = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR001_42"), key: 'code', width: 70},
                    { headerText: nts.uk.resource.getText("KWR001_43"), key: 'name', width: 280}
                ]); 
                self.currentCodeList = ko.observableArray([]);
                self.showTableInIE = ko.observable(false || !!document['documentMode']);
            }
            
             startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                self.currentCodeList([]);
                self.currentCodeList(nts.uk.ui.windows.getShared('KWR001_B_errorAlarmCode'));
                service.getErrorAlarmCode().done(function(data: any) {
                    self.items(data);
                    dfd.resolve();     
                })
                
                return dfd.promise();
            }
            
            closeDialog(): void {
                nts.uk.ui.windows.close();    
            }
            
            saveDialog(): void {
                let self = this;
                nts.uk.ui.windows.setShared('KWR001_B_errorAlarmCode', self.currentCodeList(), true);
                nts.uk.ui.windows.close();    
            }
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}