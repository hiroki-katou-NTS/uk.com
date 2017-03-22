module qrm007.a.viewmodel {
    export class ScreenModel {
        retirementPayItemList: KnockoutObservableArray<RetirementPayItem>;
        currentItem: KnockoutObservable<RetirementPayItem>;
        currentCode: KnockoutObservable<any>;
        dirty: nts.uk.ui.DirtyChecker;
        constructor() {
            var self = this;
            self.retirementPayItemList = ko.observableArray([new RetirementPayItem(null,null,null,null)]);
            self.currentCode = ko.observable(0);
            self.currentItem = ko.observable(new RetirementPayItem("","","",""));
            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.findRetirementPayItemList(false)
            .done(function(){
                $(document).delegate("#lst-001", "iggridselectionrowselectionchanging", function (evt, ui) {
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。 ").
                        ifYes(function(){
                            $('#inp-1').ntsError('clear');
                            self.currentCode(ui.row.id);
                            self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); }))); 
                            self.dirty.reset();
                        }).ifNo(function(){
                            self.currentCode(ui.selectedRows[0].id);
                        });
                    } else {
                        $('#inp-1').ntsError('clear');
                        self.currentCode(ui.row.id);
                        self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); })));  
                        self.dirty.reset();
                    }
                });
                dfd.resolve();
            }).fail(function(){
                dfd.reject();
            });
            return dfd.promise();
        }
        findRetirementPayItemList(notFirstTime) {
            var self = this;
            var dfd = $.Deferred();
            qrm007.a.service.retirePayItemSelect()
            .done(function(data) {
                self.retirementPayItemList.removeAll();
                if(data.length){
                    data.forEach(function(dataItem){
                        self.retirementPayItemList.push(ko.mapping.toJS(
                            new RetirementPayItem(
                                dataItem.itemCode,
                                dataItem.itemName,
                                dataItem.printName,
                                dataItem.memo)));
                    });
                    if(!notFirstTime){ self.currentCode(_.first(self.retirementPayItemList()).itemCode); }
                    self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function(o) { return o.itemCode == self.currentCode(); })));              
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                }
                dfd.resolve(); 
            })
            .fail(function(res) { self.retirementPayItemList.removeAll(); dfd.reject(); });
            return dfd.promise();
        }
        updateRetirementPayItemList(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.currentItem());
            qrm007.a.service.retirePayItemUpdate(command)
            .done(function(data) { 
                self.findRetirementPayItemList(true); 
                dfd.resolve(); 
            }).fail(function(res) {
                dfd.reject(); });
            return dfd.promise();
        }
        saveData(){
            var self = this;
            var dfd = $.Deferred();
            self.updateRetirementPayItemList().done(function(){dfd.resolve();}).fail(function(){dfd.reject();});
            return dfd.promise();    
        }
    }
    
    interface RPItem {
        itemCode: any;
        itemName: any;
        printName: any;
        memo: any;    
    }
        
    class RetirementPayItem {
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
        static converToObject(object: RPItem): RetirementPayItem {
            return new RetirementPayItem(object.itemCode,object.itemName,object.printName,object.memo);
        }
    }
}