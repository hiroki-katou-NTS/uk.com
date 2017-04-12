module nts.uk.pr.view.qmm017.q {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                this.items = ko.observableArray([]);

                for (let i = 1; i < 4; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i));
                }

                this.columns = ko.observableArray([
                    { headerText: '計算式の項目名', prop: 'code', width: 150 },
                    { headerText: '値', prop: 'value', width : 100}
                ]);

                this.currentCodeList = ko.observableArray([]);
                
            }
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        constructor(name: string,code: string, description: string) {
            this.name = name;
            this.code = code;
            this.description = description;
        }
    }
}