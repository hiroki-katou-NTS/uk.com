module nts.uk.at.view.kwr001.d {
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<ItemModel>;
            selectedCode: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;
            D1_6_value: KnockoutObservable<string>;
            D1_7_value: KnockoutObservable<string>;
        
            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.itemList = ko.observableArray([
                    new ItemModel('1', '基本給'),
                    new ItemModel('2', '役職手当'),
                    new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
                ]);
        
                self.selectedCode = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
                self.D1_6_value = ko.observable('');
                self.D1_7_value = ko.observable('');
            }
            
            startPage() {
                var self = this;
                var dfd = $.Deferred();
                var data = nts.uk.ui.windows.getShared('KWR001_D');
                if (data) dfd.resolve();
                return dfd.promise();
            }
            
            initData() : JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        
        };
        
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