module nts.uk.at.view.kmk008.k {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<string>;
            items: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            count: number = 4;
            
            constructor() {
                let self = this;
                self.date = ko.observable('20000101');
                this.items = ko.observableArray([]);
                
                for(let i = 1; i < 4; i++) {
                    this.items.push(new ItemModel('00' + i, 2017-i));
                }
                
                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '年度', key: 'name', width: 150 }
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
