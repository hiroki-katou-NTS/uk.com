module qrm007.a.viewmodel {
    export class ScreenModel {
        retirementPayItemList: KnockoutObservableArray<RetirementPayItemModel>;
        currentItem: KnockoutObservable<RetirementPayItemModel>;
        currentCode: KnockoutObservable<any>;
        constructor() {
            var self = this;
            self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel(null,null,null,null)]);
            self.currentCode = ko.observable();
            self.currentItem = ko.observable(new RetirementPayItemModel(null,null,null,null));
            self.currentCode.subscribe(function(newValue) {
                self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(item) { return item.itemCode == newValue; })));
            });
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qrm007.a.service.getRetirementPayItemList().done(function(data) {
                self.retirementPayItemList.removeAll();
                if(data.length){
                    data.forEach(function(dataItem){
                        self.retirementPayItemList.push(ko.mapping.toJS(
                            new RetirementPayItemModel(
                                dataItem.itemCode,
                                dataItem.itemName,
                                dataItem.printName,
                                dataItem.memo)));
                    });
                    self.currentCode(1);  
                    self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                }
                dfd.resolve();
            }).fail(function(res) {
                //self.retirementPayItemList.removeAll();
            });
            return dfd.promise();
        }
        updateRetirementPayItemList(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.currentItem());
            console.log(self.currentItem());
            qrm007.a.service.updateRetirementPayItem(command).done(function(data) {
                qrm007.a.service.getRetirementPayItemList().done(function(data) {
                    self.retirementPayItemList.removeAll();
                    if(data.length){
                        data.forEach(function(dataItem){
                            self.retirementPayItemList.push(ko.mapping.toJS(
                                new RetirementPayItemModel(
                                    dataItem.itemCode,
                                    dataItem.itemName,
                                    dataItem.printName,
                                    dataItem.memo)));
                        }); 
                    }
                    dfd.resolve();
                }).fail(function(res) {
                    //self.retirementPayItemList.removeAll();
                    dfd.resolve();
                });
            }).fail(function(res) {
                dfd.resolve();
            });
            return dfd.promise();
        }
    }
    
    interface RPItem {
        itemCode: any;
        itemName: any;
        printName: any;
        memo: any;    
    }
        
    class RetirementPayItemModel {
        itemCode: KnockoutObservable<any>;
        itemName: KnockoutObservable<any>;
        printName: KnockoutObservable<any>;
        memo: KnockoutObservable<any>;
        constructor(code: string, name: string, printName: string, memo: string) {
            var self = this;
            self.itemCode = ko.observable(code);
            self.itemName = ko.observable(name);
            self.printName = ko.observable(printName);
            self.memo = ko.observable(memo);
        }
        static converToObject(object: RPItem): RetirementPayItemModel {
            return new RetirementPayItemModel(object.itemCode,object.itemName,object.printName,object.memo);
        }
    }
}