module qmm018.b.viewmodel {
    export class ScreenModel {
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        currentCodeListSwap: KnockoutObservableArray<ItemModel>;
        constructor() {
            var self = this;
            self.selectedPaymentDate = ko.observable(null);
            self.items = ko.observableArray([]);
            self.currentCodeListSwap = ko.observableArray([]);
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.b.service.getItemList().done(function(data) {
                data.forEach(function(dataItem){
                    self.items().push(new ItemModel(dataItem.itemCode,dataItem.itemAbName));
                });
                dfd.resolve();
            }).fail(function(res) {
            });
            return dfd.promise();
        }
        saveData() {
            var self = this;
            nts.uk.ui.windows.setShared('selectedItemList', self.currentCodeListSwap);
            nts.uk.ui.windows.close();
        }
        closeWindow() {
            nts.uk.ui.windows.setShared('selectedItemList', ko.observableArray([]));
            nts.uk.ui.windows.close();
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