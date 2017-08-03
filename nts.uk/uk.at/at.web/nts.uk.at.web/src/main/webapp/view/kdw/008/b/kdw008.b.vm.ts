module nts.uk.at.view.kdw008.b {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            
            constructor() {
                this.items = ko.observableArray([]);
            
                for(let i = 1; i < 3; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給'));
                }
                
                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 70},
                    { headerText: '勤務種別名称', key: 'name', width: 120 }
                ]);
                
                this.currentCode = ko.observable();
                   
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            
        }
        
        export class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}
