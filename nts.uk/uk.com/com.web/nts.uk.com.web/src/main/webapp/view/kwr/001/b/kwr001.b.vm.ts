module nts.uk.at.view.kwr001.b {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            
            constructor() {
                this.items = ko.observableArray([]);
                
                for(let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, 'asrgasrga'));
                }
                
                this.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWR001_42"), key: 'code', width: 150},
                    { headerText: nts.uk.resource.getText("KWR001_43"), key: 'name', width: 250 }
                ]); 
                this.currentCodeList = ko.observableArray([]);
            }
            
             startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                var data = nts.uk.ui.windows.getShared('KWR001_B');
                dfd.resolve();
                return dfd.promise();
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