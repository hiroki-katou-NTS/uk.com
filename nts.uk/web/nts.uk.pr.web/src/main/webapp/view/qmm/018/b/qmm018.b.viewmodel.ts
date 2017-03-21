module qmm018.b.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCodeListSwap: KnockoutObservableArray<ItemModel>; // Item Selected
        unselectedCodeListSwap: KnockoutObservableArray<ItemModel>; // Item Unselected
        oldCurrentCodeListSwap: KnockoutObservableArray<ItemModel>; // Item selected form B screen, n = 0: ItemSalary, n = 2: ItemAttend
        oldUnselectedCodeListSwap: KnockoutObservableArray<ItemModel>; // Item unselected form B screen, n = 0: ItemSalary, n = 2: ItemAttend
        constructor() {
            var self = this;
            self.items = ko.observableArray([]);
            self.currentCodeListSwap = ko.observableArray([]);
            self.unselectedCodeListSwap = ko.observableArray([]);
            self.oldCurrentCodeListSwap = ko.observableArray([]);
            self.oldUnselectedCodeListSwap = ko.observableArray([]);
            
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.b.service.itemSelect(nts.uk.ui.windows.getShared('categoryAtr')).done(function(data) {
                if(!data.length) {}
                else {
                    data.forEach(function(dataItem){
                        self.items().push(new ItemModel(dataItem.itemCode,dataItem.itemAbName));
                    });
                    self.currentCodeListSwap.subscribe(function(value){
                        self.unselectedCodeListSwap(_.difference(self.items(),self.currentCodeListSwap()));
                        if(!value.length) $("#label-span").ntsError('set', 'ER007');
                        else $("#label-span").ntsError('clear');    
                    });
                }
                dfd.resolve();
                self.currentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList'));
                self.oldCurrentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList'));
                self.oldUnselectedCodeListSwap(_.differenceBy(self.items(),self.oldCurrentCodeListSwap(), "code"));
            }).fail(function(res) {
            });
            return dfd.promise();
        }
        submitData() {
            var self = this;
            nts.uk.ui.windows.setShared('selectedItemList', self.currentCodeListSwap());
            nts.uk.ui.windows.setShared('unSelectedItemList', self.unselectedCodeListSwap());
            nts.uk.ui.windows.close();
        }
        closeWindow() {
            var self = this;
            nts.uk.ui.windows.setShared('selectedItemList', self.oldCurrentCodeListSwap());
            nts.uk.ui.windows.setShared('unSelectedItemList', self.oldUnselectedCodeListSwap());
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