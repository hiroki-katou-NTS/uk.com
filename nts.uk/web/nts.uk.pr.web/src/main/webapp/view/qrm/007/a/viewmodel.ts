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
                    self.currentCode(_.first(self.retirementPayItemList()).itemCode);  
                    self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                }
                dfd.resolve();
                self.currentCode.subscribe(function(newValue) {
                    $('.data-required').ntsError('clear');
                    self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(item) { return item.itemCode == newValue; })));
                });
            }).fail(function(res) {
                self.retirementPayItemList.removeAll();
                dfd.resolve();
            });
            return dfd.promise();
        }
        findRetirementPayItemList(){
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
                    self.currentCode(_.first(self.retirementPayItemList()).itemCode);  
                    self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                }
                dfd.resolve();
            }).fail(function(res) {
                self.retirementPayItemList.removeAll();
                dfd.resolve();
            });
            return dfd.promise();
        }
        updateRetirementPayItemList(){
            var self = this;
            var dfd = $.Deferred();
            if(self.currentItem().onchange()){
                var command = ko.mapping.toJS(self.currentItem());
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
                        self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(item) { return item.itemCode == self.currentCode(); })));
                        dfd.resolve();
                    }).fail(function(res) {
                        self.retirementPayItemList.removeAll();
                        dfd.resolve();
                    });
                }).fail(function(res) {
                    dfd.resolve();
                });
            } else {
                $('.data-required').ntsError('set', 'ER001');  
            }
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
        onchange: KnockoutObservable<any>;
        itemCode: KnockoutObservable<any>;
        itemName: KnockoutObservable<any>;
        printName: KnockoutObservable<any>;
        memo: KnockoutObservable<any>;
        constructor(code: string, name: string, printName: string, memo: string) {
            var self = this;
            self.onchange = ko.observable(false);
            self.itemCode = ko.observable(code);
            self.itemName = ko.observable(name);
            self.printName = ko.observable(printName);
            self.memo = ko.observable(memo);
            self.printName.subscribe(function(value){ (value==printName)?self.onchange(false):self.onchange(true); });
        }
        static converToObject(object: RPItem): RetirementPayItemModel {
            return new RetirementPayItemModel(object.itemCode,object.itemName,object.printName,object.memo);
        }
    }
}