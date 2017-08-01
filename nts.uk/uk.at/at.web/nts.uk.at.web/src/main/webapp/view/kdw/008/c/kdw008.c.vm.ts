module nts.uk.at.view.kdw008.c {
    export module viewmodel {
        export class ScreenModel {
            idList:KnockoutObservable<string>;
            itemsSwap: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
            testSingle: KnockoutObservable<any>;

            constructor() {
                var self = this;
                this.idList = ko.observable('');
                this.itemsSwap = ko.observableArray([]);

                var array = [];
                for (var i = 0; i < 13; i++) {
                    array.push(new ItemModel("test" + i,"test" + i, '基本給'));
                }
                this.itemsSwap(array);

                this.columns = ko.observableArray([
                    { headerText: '', key: 'code', width: 100 },
                    { headerText: '', key: 'name', width: 150 }
                ]);
                this.testSingle = ko.observable(null);
            }

            update(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                var idList = this.idList();
                var itemsSwap = this.itemsSwap();
                service.update(new UpdateData(idList, itemsSwap)).done(function(data) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_3" });
                    dfd.resolve();
                });
                return dfd.promise();
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
        }

        export class UpdateData{
            idList: string;
            itemsSwap: ItemModel[];
            constructor(idList:string, itemsSwap: ItemModel[]){
                this.idList = idList;
                this.itemsSwap = itemsSwap;
            }
        }
        
        export class ItemModel {
            id: string;
            code: string;
            name: string;
            constructor(id: string, code: string, name: string) {
                this.id = id;
                this.code = code;
                this.name = name;
            }
        }
    }
}
