module qrm007.a.viewmodel {
    export class ScreenModel {
        retirementPayItemList: KnockoutObservableArray<RetirementPayItemModel>;
        currentItem: KnockoutObservable<RetirementPayItemModel>;
        currentCode: KnockoutObservable<any>;
        dirty: nts.uk.ui.DirtyChecker;
        constructor() {
            var self = this;
            self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel(null,null,null,null)]);
            self.currentCode = ko.observable(0);
            self.currentItem = ko.observable(new RetirementPayItemModel("","","",""));
            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem().printName);
            self.currentItem().printName.subscribe(function(value){console.log(value);});
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findRetirementPayItemList(false).done(function(){
                self.currentCode.subscribe(function(newValue) {
                    self.checkDirty();
                });     
            }).fail(function(){});
            dfd.resolve();     
            return dfd.promise();
        }
        findRetirementPayItemList(firstTime) {
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
                    if(!firstTime){self.currentCode(_.first(self.retirementPayItemList()).itemCode);}
                    self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); })));              
                }
                dfd.resolve();
                self.currentCode.subscribe(function(newValue) {
                    self.checkDirty();
                });
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
                    self.findRetirementPayItemList(true);
                }).fail(function(res) {
                });
            } else {
            }
            return dfd.promise();
        }
        checkDirty() {
            var self = this;
            if (self.dirty.isDirty()) {
                alert("Data is changed.");
            } else {
                self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); }))); 
                
            }
        };
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