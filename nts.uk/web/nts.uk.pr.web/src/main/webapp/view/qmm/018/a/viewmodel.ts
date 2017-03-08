module qmm018.a.viewmodel {
    export class ScreenModel {
        averagePay: KnockoutObservable<AveragePay>;
        selectedItemList1: KnockoutObservableArray<ItemModel>;
        selectedItemList2: KnockoutObservableArray<ItemModel>;
        texteditor1: KnockoutObservable<any>;
        texteditor2: KnockoutObservable<any>;
        isUpdate: any;
        constructor() {
            var self = this;
            self.averagePay = ko.observable(new AveragePay(null, null, null, null));
            self.selectedItemList1 = ko.observableArray([]);
            self.selectedItemList2 = ko.observableArray([]);
            self.texteditor1 = ko.observable({
                value: ko.computed(function(){
                    let s: any;
                    ko.utils.arrayForEach(self.selectedItemList1(),function(item){if(!s){s=item.name} else {s+=" + "+item.name}});
                    return s;
                })
            });
            self.texteditor2 = ko.observable({
                value: ko.computed(function(){
                    let s: any;
                    ko.utils.arrayForEach(self.selectedItemList2(),function(item){if(!s){s=item.name} else {s+=" + "+item.name}});
                    return s;
                })
            });
            self.selectedItemList1.subscribe(function(value){
                if(!value.length) $("#inp-3").ntsError('set', 'ER007'); else $("#inp-3").ntsError('clear');       
            });
            self.selectedItemList2.subscribe(function(value){
                if(!value.length) $("#inp-1").ntsError('set', 'ER007'); else $("#inp-1").ntsError('clear');       
            });
            self.isUpdate = false;
            
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.a.service.qapmt_Ave_Pay_SEL_1().done(function(data) {
                if(data){
                    qmm018.a.service.qcamt_Item_SEL_4().done(function(data2) {
                        console.log(data2);
                        dfd.resolve();
                    }).fail(function(res) {
                    });
                    self.averagePay(
                        new AveragePay(
                            data.roundTimingSet,
                            data.attendDayGettingSet,
                            data.roundDigitSet,
                            data.exceptionPayRate));
                    self.isUpdate = true;
                } else {
                    self.averagePay(new AveragePay(0,0,0,null));
                    self.isUpdate = false;
                }
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
        saveData(isUpdate){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.averagePay());
                if(isUpdate){
                    qmm018.a.service.qapmt_Ave_Pay_UPD_1(command).done(function(data) {
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert("Update Fail");
                    });
                } else {
                    qmm018.a.service.qapmt_Ave_Pay_INS_1(command).done(function(data) {
                        qmm018.a.service.qcamt_Item_UPD_2(0, 1).done(function(){
                            if(self.averagePay().attendDayGettingSet()){
                                if(self.isUpdate){
                                    qmm018.a.service.qcamt_Item_SEL_8(2, 1).done(function(){
                                       qmm018.a.service.qcamt_Item_UPD_2(2, 1).done(function(){
                                           
                                       });
                                   });     
                                }
                                qmm018.a.service.qcamt_Item_UPD_2(2, 1).done(function(){
                                           
                                }); 
                            }    
                        });
                        dfd.resolve();
                    }).fail(function(res) {
                        nts.uk.ui.dialog.alert("Register Fail");
                    });
                }
            return dfd.promise();    
        }
        openSubWindow(n) {
            var self = this;
            if(!n){
                nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList1);
                nts.uk.ui.windows.setShared('categoryAtr', 1);        
            } else {
                nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList2);
                nts.uk.ui.windows.setShared('categoryAtr', 2);
            }
            nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", {title: "労働日数項目一覧", dialogClass: "no-close"}).onClosed(function(){
                let selectedList: KnockoutObservableArray<ItemModel> = nts.uk.ui.windows.getShared('selectedItemList');
                if(!n){
                    self.selectedItemList1.removeAll();
                    if(selectedList().length){
                        ko.utils.arrayForEach(selectedList(),function(item){self.selectedItemList1.push(item)});
                    }
                } else {
                    self.selectedItemList2.removeAll();
                    if(selectedList().length){
                        ko.utils.arrayForEach(selectedList(),function(item){self.selectedItemList2.push(item)});
                    }
                }
            }); 
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
    
    class AveragePay {
        roundTimingSet: KnockoutObservable<any>;
        attendDayGettingSet: KnockoutObservable<any>;
        roundDigitSet: KnockoutObservable<any>;
        exceptionPayRate: KnockoutObservable<any>;
        oldExceptionPayRate: KnockoutObservable<any>;
        constructor(roundTimingSet: any, attendDayGettingSet: any, roundDigitSet: any, exceptionPayRate: any){
            var self = this;
            self.roundTimingSet = ko.observable(roundTimingSet);
            self.attendDayGettingSet = ko.observable(attendDayGettingSet); 
            self.roundDigitSet = ko.observable(roundDigitSet);  
            self.exceptionPayRate = ko.observable(exceptionPayRate);   
            self.oldExceptionPayRate = ko.observable(exceptionPayRate);
            self.roundTimingSet.subscribe(function(value){self.roundTimingSet(value?1:0);});
            self.exceptionPayRate.subscribe(function(value){
                if($("#inp-2").ntsError("hasError")) { self.oldExceptionPayRate(exceptionPayRate); }
                else { exceptionPayRate = value; self.oldExceptionPayRate(value); }        
            });
        }
    }
}