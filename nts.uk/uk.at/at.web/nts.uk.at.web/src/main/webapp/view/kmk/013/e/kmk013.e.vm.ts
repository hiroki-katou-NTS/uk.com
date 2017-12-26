module nts.uk.at.view.kmk013.e {
    export module viewmodel {
        export class ScreenModel {
            itemListUnit: KnockoutObservableArray<ItemModel>;
            itemListRounding: KnockoutObservableArray<ItemModel>;
            selectedCodeRounding: KnockoutObservable<string>;
            selectedCodeUnit: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            /**
             * Constructor.
             */
            constructor() {
                var self = this;
                self.itemListUnit = ko.observableArray([
                    new ItemModel('1','1'),
                    new ItemModel('2','5'),
                    new ItemModel('3','6'),
                    new ItemModel('4','10'),
                    new ItemModel('5','15'),
                    new ItemModel('6','20'),
                    new ItemModel('7','30'),
                    new ItemModel('8','60'),
                ]);
                self.itemListRounding = ko.observableArray([
                    new ItemModel('1','切り捨て'),
                    new ItemModel('2','未満切捨、以上切上'),
                    new ItemModel('3','切り上げ'),
                ]);
                self.selectedCodeUnit = ko.observable('1');
                self.selectedCodeRounding = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);
               
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
                dfd.resolve();
                return dfd.promise();
            }
        };

        class ItemModel {
            code: string;
            name:string;
            constructor(code: string,name?:string) {
                this.code = code;
                this.name=name;
            }

        }
    }
}