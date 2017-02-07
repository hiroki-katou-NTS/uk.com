module nts.uk.pr.view.qmm017.p {
    export module viewmodel {
        export class ScreenModel {
            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<any>;
            currentCodeList: KnockoutObservableArray<any>;

            constructor() {
                this.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, "other" + i));
                }

                this.columns = ko.observableArray([
                    { headerText: 'コード', prop: 'code', width: 100 }
                ]);

                this.currentCodeList = ko.observableArray([]);
                
            }
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        constructor(code: string, name: string, description: string, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
        }
    }
}