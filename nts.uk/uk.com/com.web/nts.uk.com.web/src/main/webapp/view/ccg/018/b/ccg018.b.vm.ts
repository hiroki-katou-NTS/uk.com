module ccg018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCode: KnockoutObservable<any>;
        columns: KnockoutObservableArray<NtsGridListColumn>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.currentCode = ko.observable();
            for (let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, '基本給', "description " + i, "description " + i));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 50 },
                { headerText: '名称', key: 'name', width: 100 },
                { headerText: '説明', key: 'description', width: 150 },
                { headerText: '説明1', key: 'other1', width: 150 },
            ]);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        openToScreenC() { }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        constructor(code: string, name: string, description: string, other1: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
        }
    }
}