module qrm007.a.viewmodel {
    export class ScreenModel {
        retirementPayItemList: KnockoutObservableArray<RetirementPayItemModel>;
        currentItem: KnockoutObservable<RetirementPayItemModel>;
        currentCode: KnockoutObservable<any>;
        oldCode: any;
        dirty: nts.uk.ui.DirtyChecker;
        constructor() {
            var self = this;
            self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel(null,null,null,null)]);
            self.currentCode = ko.observable(0);
            self.oldCode = self.currentCode();
            self.currentItem = ko.observable(new RetirementPayItemModel("","","",""));
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findRetirementPayItemList(false).
            done(function(){
                dfd.resolve();
                self.currentCode.subscribe(function(newValue) {
                    if(self.oldCode != newValue) { self.checkDirty(newValue); }
                }); }).
            fail(function(){});
            return dfd.promise();
        }
        findRetirementPayItemList(notFirstTime) {
            var self = this;
            var dfd = $.Deferred();
            qrm007.a.service.getRetirementPayItemList().
            done(function(data) {
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
                    if(!notFirstTime){ self.currentCode(_.first(self.retirementPayItemList()).itemCode); self.oldCode = self.currentCode(); }
                    self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); })));              
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                }
                dfd.resolve(); }).
            fail(function(res) { self.retirementPayItemList.removeAll(); dfd.resolve(); });
            return dfd.promise();
        }
        updateRetirementPayItemList(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.currentItem());
            qrm007.a.service.updateRetirementPayItem(command).
            done(function(data) { self.findRetirementPayItemList(true); dfd.resolve(); }).
            fail(function(res) { dfd.resolve(); });
            return dfd.promise();
        }
        saveData(){
            var self = this;
            var dfd = $.Deferred();
            if(!self.dirty.isDirty()){
                self.updateRetirementPayItemList(); dfd.resolve();
            } else {
                nts.uk.ui.dialog.confirm("Do you want to update Item ?").
                ifYes(function(){ self.updateRetirementPayItemList(); dfd.resolve(); }).
                ifNo(function(){ dfd.resolve(); });     
            }
            return dfd.promise();    
        }
        checkDirty(newValue) {
            var self = this;
            if (self.dirty.isDirty()) {
                nts.uk.ui.dialog.confirm("Do you want to change Item ?").
                ifYes(function(){
                    self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); }))); 
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.oldCode = newValue; }).
                ifNo(function(){
                    self.currentCode(self.oldCode);
                });
            } else {
                self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); }))); 
                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);   
                self.oldCode = newValue;
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