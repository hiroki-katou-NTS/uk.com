module qmm011.d {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<viewmodel.ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                this.items = ko.observableArray([
                    new ItemModel('001', '基本給', "description 1"),
                    new ItemModel('150', '役職手当', "description 2"),
                    new ItemModel('ABC', '基12本ghj給', "description 3")
                ]);
                this.columns = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 100 },
                    { headerText: '名称', prop: 'name', width: 150 },
                    { headerText: '説明', prop: 'description', width: 200 }
                ]);
                this.currentCode = ko.observable();
                this.currentCodeList = ko.observableArray([]);
            }
            selectSomeItems() {
                this.currentCode('150');
                this.currentCodeList.removeAll();
                this.currentCodeList.push('001');
                this.currentCodeList.push('ABC');
            }

            deselectAll() {
                this.currentCode(null);
                this.currentCodeList.removeAll();
            }
        }
        export class ItemModel {
            code: string;
            name: string;
            description: string;

            constructor(code: string, name: string, description: string) {
                this.code = code;
                this.name = name;
                this.description = description;
            }
        }
    }
     
}
