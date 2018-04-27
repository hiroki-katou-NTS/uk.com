module nts.uk.at.view.ktg027.a.viewmodel {

    export class ScreenModel {
        /**YM Picker **/
        yearMonth: KnockoutObservable<number>;
        cssRangerYM = ko.observable({});
        /**ComboBox**/
        itemList: KnockoutObservableArray<ItemModel>;
        selectedCBB: KnockoutObservable<string>;
        constructor() {
            var self = this;

            self.yearMonth = ko.observable(200002);
            self.itemList = ko.observableArray([
                new ItemModel('1', '基本給'),
                new ItemModel('2', '役職手当'),
                new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            selectedCBB = ko.observable('1');


        }

        startPage(): JQueryPromise<any> {
            var self = this;

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
