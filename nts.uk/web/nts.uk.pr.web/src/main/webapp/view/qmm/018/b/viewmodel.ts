module qmm018.b.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;

        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        currentItemList: KnockoutObservableArray<ItemModel>;
        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.items = ko.observableArray([
                new ItemModel('001', 'name1'),
                new ItemModel('002', 'name2'),
                new ItemModel('003', 'name3')
            ]);
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
            qmm018.b.service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
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