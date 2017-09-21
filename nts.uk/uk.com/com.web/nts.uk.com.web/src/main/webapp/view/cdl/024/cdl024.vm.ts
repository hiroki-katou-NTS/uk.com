module nts.uk.at.view.cdl024 {
    export module viewmodel {
        export class ScreenModel {
            
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;
            
            constructor() {
                var self = this;
                
                //Init data for fillter list
                this.items = ko.observableArray([]);
                var str = ['a0', 'b0', 'c0', 'd0'];
                for(var j = 0; j < 4; j++) {
                    for(var i = 1; i < 51; i++) {    
                        var code = i < 10 ? str[j] + '0' + i : str[j] + i;         
                        this.items.push(new ItemModel(code,code));
                    } 
                }
                this.columns = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称', prop: 'name', width: 230 }
                ]);
                this.currentCodeList = ko.observableArray([]);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
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
}
