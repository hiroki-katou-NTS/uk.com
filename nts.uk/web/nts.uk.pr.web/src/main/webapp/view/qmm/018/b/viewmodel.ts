module qmm018.b.viewmodel {
    export class ScreenModel {
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItemList: KnockoutObservableArray<ItemModel>;
        constructor() {
            var self = this;
            self.selectedPaymentDate = ko.observable(null);
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 40 },
                { headerText: '名称', prop: 'name', width: 130 },
            ]);
            self.currentCodeList = ko.observableArray([]);
            self.currentItemList = ko.observableArray([]);
            self.currentCodeList.subscribe(function(newCodeList){
                self.currentItemList.removeAll();
                ko.utils.arrayForEach(newCodeList,function(newCode){
                    self.currentItemList.push(_.find(self.items(), function(item) { return item.code === newCode; }));
                });
            });
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
            nts.uk.ui.windows.setShared('selectedItemList', this.currentItemList);
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