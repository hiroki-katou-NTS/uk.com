module qmm018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCodeListSwap: KnockoutObservableArray<ItemModel>;
        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.currentCodeListSwap = ko.observableArray([new ItemModel("0001","支給1")]);
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.b.service.getItemList(nts.uk.ui.windows.getShared('categoryAtr')).done(function(data) {
                if(!data.length) { $("#label-span").ntsError('set', 'ER010');}
                else {
                    data.forEach(function(dataItem){
                        self.items().push(new ItemModel(dataItem.itemCode,dataItem.itemAbName));
                    });
                    self.currentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList')());
                    self.currentCodeListSwap.subscribe(function(value){
                        if(!value.length) $("#label-span").ntsError('set', 'ER010');
                        else $("#label-span").ntsError('clear');    
                    });
                }
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
        code: any;
        name: any;
        constructor(code: any, name: any) {
            this.code = code;
            this.name = name;
        }
    }
}