module nts.uk.at.view.kdr001.b {


    export module viewmodel {
        export class ScreenModel {
            textName1: KnockoutObservable<string>;
            textName2: KnockoutObservable<string>;
            items: KnockoutObservableArray<ItemModel>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            currentCodeList: KnockoutObservableArray<any>;
            count: number = 100;
            switchOptions: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                self.textName1 = ko.observable('');
                self.textName2 = ko.observable('');

                this.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "2010/1/1"));
                }

                this.columns2 = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KWD001_40"), key: "code", width: 150 },
                    { headerText: nts.uk.resource.getText("KWD001_41"), key: "name", width: 250 }
                ]);

                this.switchOptions = ko.observableArray([
                    { code: "1", name: '四捨五入' },
                    { code: "2", name: '切り上げ' },
                    { code: "3", name: '切り捨て' }
                ]);
                this.currentCodeList = ko.observableArray([]);
                // Fire event.
                $("#multi-list").on('itemDeleted', (function(e: Event) {
                    alert("Item is deleted in multi grid is " + e["detail"]["target"]);
                }));

            }
            /**
           * start page data 
           */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                dfd.resolve(self);
                return dfd.promise();
            }

        }
        class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            switchValue: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
                this.deletable = deletable;
                this.switchValue = ((code % 3) + 1).toString();
            }
        }
    }
}