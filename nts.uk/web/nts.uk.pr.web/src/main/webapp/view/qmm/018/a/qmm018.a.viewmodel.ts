module qmm018.a.viewmodel {
    export class ScreenModel {
        averagePay: KnockoutObservable<AveragePay>;
        selectedItemList1: KnockoutObservableArray<ItemModel>; // ItemSalary Selected
        selectedItemList2: KnockoutObservableArray<ItemModel>; // ItemAttend Selected
        unSelectedItemList1: KnockoutObservableArray<ItemModel>; // ItemSalary Unselected
        unSelectedItemList2: KnockoutObservableArray<ItemModel>; // ItemAttend Unselected
        texteditor1: KnockoutObservable<any>;
        texteditor2: KnockoutObservable<any>;
        isUpdate: any;
        constructor() {
            var self = this;
            self.averagePay = ko.observable(new AveragePay(null, null, null, null));
            self.selectedItemList1 = ko.observableArray([]);
            self.selectedItemList2 = ko.observableArray([]);
            self.unSelectedItemList1 = ko.observableArray([]);
            self.unSelectedItemList2 = ko.observableArray([]);
            self.texteditor1 = ko.observable({
                value: ko.computed(function() {
                    let s: any;
                    ko.utils.arrayForEach(self.selectedItemList1(), function(item) { if (!s) { s = item.name } else { s += " + " + item.name } });
                    return s;
                })
            });
            self.texteditor2 = ko.observable({
                value: ko.computed(function() {
                    let s: any;
                    ko.utils.arrayForEach(self.selectedItemList2(), function(item) { if (!s) { s = item.name } else { s += " + " + item.name } });
                    return s;
                })
            });
            self.isUpdate = false;

        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            qmm018.a.service.averagePayItemSelect().done(function(data) { // QAPMT_AVE_PAY SEL_1: get average pay items
                if (data) {
                    // if data exist go to update case
                    qmm018.a.service.averagePayItemSelectBySalary().done(function(dataSalary) { //QCAMT_ITEM SEL_5 by salary items ( after QCAMT_ITEM_SALARY SEL_3 ): get salary items
                        if (dataSalary.length) {
                            dataSalary.forEach(function(dataSalaryItem) {
                                self.selectedItemList1.push(new ItemModel(dataSalaryItem.itemCode, dataSalaryItem.itemAbName));
                            });
                        }
                    }).fail(function(res) {
                    });
                    qmm018.a.service.averagePayItemSelectByAttend().done(function(dataAttend) { //QCAMT_ITEM SEL_5 by attend items ( after QCAMT_ITEM_ATTEND SEL_4 ): get attend items
                        if (dataAttend.length) {
                            dataAttend.forEach(function(dataAttendItem) {
                                self.selectedItemList2.push(new ItemModel(dataAttendItem.itemCode, dataAttendItem.itemAbName));
                            });
                        }
                    }).fail(function(res) {
                    });
                    self.averagePay(
                        new AveragePay(data.roundTimingSet, data.attendDayGettingSet, data.roundDigitSet, data.exceptionPayRate));
                    self.isUpdate = true;
                } else {
                    // if data no exist go to insert case
                    self.averagePay(new AveragePay(0, 0, 0, null));
                    self.selectedItemList1([]);
                    self.selectedItemList2([]);
                    self.isUpdate = false;
                }
                dfd.resolve();
                
                // error check on salary list and attend list
                self.selectedItemList1.subscribe(function(value) {
                    if (!value.length) $("#inp-3").ntsError('set', 'ER007'); else $("#inp-3").ntsError('clear');
                });
                self.selectedItemList2.subscribe(function(value) {
                    if (!value.length) $("#inp-1").ntsError('set', 'ER007'); else $("#inp-1").ntsError('clear');
                });
                
            }).fail(function(res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        saveData(isUpdate) {
            var self = this;
            var dfd = $.Deferred();
            let error = false;
            let selectedCodeList1 = [];
            
            // check errors on required
            if (self.selectedItemList1().length) {
                self.selectedItemList1().forEach(function(item) { selectedCodeList1.push(item.code); });
            } else { $("#inp-3").ntsError('set', 'ER007');  error = true; }
            let selectedCodeList2 = [];
            if (self.averagePay().attendDayGettingSet()) {
                if (self.selectedItemList2().length) {
                    self.selectedItemList2().forEach(function(item) { selectedCodeList2.push(item.code); });
                } else { $("#inp-1").ntsError('set', 'ER007'); error = true; }
            }
            if (self.averagePay().exceptionPayRate() == null) { $("#inp-2").ntsError('set', 'ER001'); error = true; }
            
            // insert or update if no error
            if (!error) {
                let command = {
                    attendDayGettingSet: self.averagePay().attendDayGettingSet(),
                    exceptionPayRate: self.averagePay().exceptionPayRate(),
                    roundDigitSet: self.averagePay().roundDigitSet(),
                    roundTimingSet: self.averagePay().roundTimingSet(),
                    salarySelectedCode: selectedCodeList1,
                    attendSelectedCode: selectedCodeList2
                };
                if (isUpdate) {
                    qmm018.a.service.averagePayItemUpdate(command).done(function(data) {
                        dfd.resolve();
                    }).fail(function(res) {
                        dfd.reject();
                    });
                } else {
                    qmm018.a.service.averagePayItemInsert(command).done(function(data) {
                        dfd.resolve();
                    }).fail(function(res) {
                        dfd.reject();
                    });
                }
            }
            return dfd.promise();
        }
        openSubWindow(n) {
            var self = this;
            if (!n) {
                // set salary data
                nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList1());
                nts.uk.ui.windows.setShared('categoryAtr', 0);
            } else {
                // set attend data
                nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList2());
                nts.uk.ui.windows.setShared('categoryAtr', 2);
            }
            nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", { title: "対象項目の選択", dialogClass: "no-close" }).onClosed(function() {
                let selectedList: Array<ItemModel> = nts.uk.ui.windows.getShared('selectedItemList'); // Get selected form B screen, n = 0: ItemSalary, n = 2: ItemAttend
                let unSelectedList: Array<ItemModel> = nts.uk.ui.windows.getShared('unSelectedItemList'); // Get unselected form B screen, n = 0: ItemSalary, n = 2: ItemAttend
                if (!n) {
                    // set data to salary item list 
                    if (selectedList.length) {
                        if (!_.isEqual(selectedList, self.selectedItemList1())) {
                            self.selectedItemList1.removeAll();
                            ko.utils.arrayForEach(selectedList, function(item) { self.selectedItemList1.push(item) });
                            if (unSelectedList.length) {
                                self.unSelectedItemList1.removeAll();
                                ko.utils.arrayForEach(unSelectedList, function(item) { self.unSelectedItemList1.push(item) });
                            } else { self.unSelectedItemList1([]); }
                        }
                    } else { self.selectedItemList1([]); }
                } else {
                    // set data to attend item list 
                    if (selectedList.length) {
                        if (!_.isEqual(selectedList, self.selectedItemList2())) {
                            self.selectedItemList2.removeAll();
                            ko.utils.arrayForEach(selectedList, function(item) { self.selectedItemList2.push(item) });
                            if (unSelectedList.length) {
                                self.unSelectedItemList2.removeAll();
                                ko.utils.arrayForEach(unSelectedList, function(item) { self.unSelectedItemList2.push(item) });
                            } else { self.unSelectedItemList2([]); }
                        }
                    } else { self.selectedItemList2([]); }
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
        constructor(roundTimingSet: any, attendDayGettingSet: any, roundDigitSet: any, exceptionPayRate: any) {
            var self = this;
            self.roundTimingSet = ko.observable(roundTimingSet);
            self.attendDayGettingSet = ko.observable(attendDayGettingSet);
            self.roundDigitSet = ko.observable(roundDigitSet);
            self.exceptionPayRate = ko.observable(exceptionPayRate);
            self.oldExceptionPayRate = ko.observable(exceptionPayRate);
            self.roundTimingSet.subscribe(function(value) { self.roundTimingSet(value ? 1 : 0); });
            self.exceptionPayRate.subscribe(function(value) {
                if ($("#inp-2").ntsError("hasError")) { self.oldExceptionPayRate(exceptionPayRate); }
                else { exceptionPayRate = value; self.oldExceptionPayRate(value); }
            });
        }
    }
}