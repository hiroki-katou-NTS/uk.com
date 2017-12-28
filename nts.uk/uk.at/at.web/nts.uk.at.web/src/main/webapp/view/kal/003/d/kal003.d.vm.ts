module nts.uk.at.view.kal003.d.viewmodel {
    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCode: KnockoutObservable<string>;


        constructor() {
            var self = this;
        }

        startPage(): JQueryPromise<any> {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給')
            ]);
            self.selectedCode = ko.observable('1');

            var dfd = $.Deferred();
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