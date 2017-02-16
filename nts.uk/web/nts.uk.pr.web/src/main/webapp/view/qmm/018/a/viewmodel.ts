module qmm018.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        averagePay: KnockoutObservable<AveragePay>;
        selectedItemList1: KnockoutObservableArray<ItemModel>;
        selectedItemList2: KnockoutObservableArray<ItemModel>;
        texteditor1: any;
        texteditor2: any;
        constructor() {
            var self = this;
            self.averagePay = ko.observable(new AveragePay(1, 1, 0, 1));
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedItemList1 = ko.observableArray([]);
            self.selectedItemList2 = ko.observableArray([]);
            self.texteditor1 = {
                value: ko.computed(function(){
                    let s: string;
                    ko.utils.arrayForEach(self.selectedItemList1(),function(item){if(!s){s=item.name} else {s+=" + "+item.name}});
                    return s;
                })
            };
            self.texteditor2 = {
                value: ko.computed(function(){
                    let s: string;
                    ko.utils.arrayForEach(self.selectedItemList2(),function(item){if(!s){s=item.name} else {s+=" + "+item.name}});
                    return s;
                })
            };
            self.averagePay().roundTimingSet.subscribe(function(value){ self.averagePay().roundTimingSet(value?1:0); });
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.a.service.getPaymentDateProcessingList().done(function(data) {
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
        saveData(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.averagePay());
            qmm018.a.service.saveData(command).done(function(data) {
                dfd.resolve();
            }).fail(function(res) {
            });
            return dfd.promise();    
        }
        updateData(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.averagePay());
            qmm018.a.service.updateData(command).done(function(data) {
                dfd.resolve();
            }).fail(function(res) {
            });
            return dfd.promise();    
        }
        removeData(){
            var self = this;
            var dfd = $.Deferred();
            var command = ko.mapping.toJS(self.averagePay());
            qmm018.a.service.removeData(command).done(function(data) {
                dfd.resolve();
            }).fail(function(res) {
            });
            return dfd.promise();    
        }
        openSubWindow(n) {
            var self = this;
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
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    class AveragePay {
        roundDigitSet: KnockoutObservable<any>;
        attendDayGettingSet: KnockoutObservable<any>;
        roundTimingSet: KnockoutObservable<any>;
        exceptionPayRate: KnockoutObservable<any>;
        constructor(roundDigitSet: any, attendDayGettingSet: any, roundTimingSet: any, exceptionPayRate: any){
            this.roundDigitSet = ko.observable(roundDigitSet); 
            this.attendDayGettingSet = ko.observable(attendDayGettingSet); 
            this.roundTimingSet = ko.observable(roundTimingSet); 
            this.exceptionPayRate = ko.observable(exceptionPayRate);   
        }
    }
}