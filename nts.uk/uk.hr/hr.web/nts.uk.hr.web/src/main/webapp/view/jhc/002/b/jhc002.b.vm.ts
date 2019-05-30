module nts.uk.hr.view.jhc002.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        selectedHistId: KnockoutObservable<any>;
        itemList: KnockoutObservableArray<ScreenItem>;

        constructor() {
            var self = this;
            //table 
            self.itemList = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 246, width: 990 });

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            nts.uk.characteristics.restore("DataShareCareerToBScreen").done((obj) => { 
                console.log(obj);
                block.clear();
                dfd.resolve();
            }).fail(() => {
                dfd.resolve();
                block.clear();
            });
            return dfd.promise();
        }
    }

    class ScreenItem {
        val1: KnockoutObservable<String>;
        val2: KnockoutObservable<String>;
        val3: KnockoutObservable<String>;
        val4: KnockoutObservable<String>;
        val5: KnockoutObservable<String>;
        val6: KnockoutObservable<String>;
        val7: KnockoutObservable<String>;
        val8: KnockoutObservable<String>;
        val9: KnockoutObservable<String>;
        val10: KnockoutObservable<String>;
        code: KnockoutObservable<String>;
        comBoList: KnockoutObservableArray<ItemModel>;
        constructor(code: String, val1: String, val2: String, val3: String, val4: String, val5: String, val6: String, val7: String, val8: String, val9: String, val10: String) {
            var self = this;
            self.code = ko.observable(code);
            self.val1 = ko.observable(val1);
            self.val2 = ko.observable(val2);
            self.val3 = ko.observable(val3);
            self.val4 = ko.observable(val4);
            self.val5 = ko.observable(val5);
            self.val6 = ko.observable(val6);
            self.val7 = ko.observable(val7);
            self.val8 = ko.observable(val8);
            self.val9 = ko.observable(val9);
            self.val10 = ko.observable(val10);
            
            self.comBoList = ko.observableArray([
                new ItemModel('1', 'chon 1'),
                new ItemModel('2', 'chon 2'),
                new ItemModel('3', 'chon 3'),
                new ItemModel('4', 'chon 4'),
                new ItemModel('5', 'chon 5'),
                new ItemModel('6', 'chon 6')
            ]);
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
