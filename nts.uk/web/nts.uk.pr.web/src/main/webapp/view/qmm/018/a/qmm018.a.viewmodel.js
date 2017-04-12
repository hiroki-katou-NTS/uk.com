var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.averagePay = ko.observable(new AveragePay(null, null, null, null));
                    self.selectedItemList1 = ko.observableArray([]);
                    self.selectedItemList2 = ko.observableArray([]);
                    self.unSelectedItemList1 = ko.observableArray([]);
                    self.unSelectedItemList2 = ko.observableArray([]);
                    self.texteditor1 = ko.observable({
                        value: ko.computed(function () {
                            var s;
                            ko.utils.arrayForEach(self.selectedItemList1(), function (item) { if (!s) {
                                s = item.name;
                            }
                            else {
                                s += " + " + item.name;
                            } });
                            return s;
                        })
                    });
                    self.texteditor2 = ko.observable({
                        value: ko.computed(function () {
                            var s;
                            ko.utils.arrayForEach(self.selectedItemList2(), function (item) { if (!s) {
                                s = item.name;
                            }
                            else {
                                s += " + " + item.name;
                            } });
                            return s;
                        })
                    });
                    self.isUpdate = false;
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.a.service.averagePayItemSelect().done(function (data) {
                        if (data) {
                            qmm018.a.service.averagePayItemSelectBySalary().done(function (dataSalary) {
                                if (dataSalary.length) {
                                    dataSalary.forEach(function (dataSalaryItem) {
                                        self.selectedItemList1.push(new ItemModel(dataSalaryItem.itemCode, dataSalaryItem.itemAbName));
                                    });
                                }
                            }).fail(function (res) {
                            });
                            qmm018.a.service.averagePayItemSelectByAttend().done(function (dataAttend) {
                                if (dataAttend.length) {
                                    dataAttend.forEach(function (dataAttendItem) {
                                        self.selectedItemList2.push(new ItemModel(dataAttendItem.itemCode, dataAttendItem.itemAbName));
                                    });
                                }
                            }).fail(function (res) {
                            });
                            self.averagePay(new AveragePay(data.roundTimingSet, data.attendDayGettingSet, data.roundDigitSet, data.exceptionPayRate));
                            self.isUpdate = true;
                        }
                        else {
                            self.averagePay(new AveragePay(0, 0, 0, null));
                            self.selectedItemList1([]);
                            self.selectedItemList2([]);
                            self.isUpdate = false;
                        }
                        dfd.resolve();
                        self.selectedItemList1.subscribe(function (value) {
                            if (!value.length)
                                $("#inp-3").ntsError('set', Error.ER007);
                            else
                                $("#inp-3").ntsError('clear');
                        });
                        self.selectedItemList2.subscribe(function (value) {
                            if (!value.length)
                                $("#inp-1").ntsError('set', Error.ER007);
                            else
                                $("#inp-1").ntsError('clear');
                        });
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var dfd = $.Deferred();
                    var error = false;
                    var selectedCodeList1 = [];
                    if (self.selectedItemList1().length) {
                        self.selectedItemList1().forEach(function (item) { selectedCodeList1.push(item.code); });
                    }
                    else {
                        $("#inp-3").ntsError('set', Error.ER007);
                        error = true;
                    }
                    var selectedCodeList2 = [];
                    if (self.averagePay().attendDayGettingSet()) {
                        if (self.selectedItemList2().length) {
                            self.selectedItemList2().forEach(function (item) { selectedCodeList2.push(item.code); });
                        }
                        else {
                            $("#inp-1").ntsError('set', Error.ER007);
                            error = true;
                        }
                    }
                    if (self.averagePay().exceptionPayRate() == null) {
                        $("#inp-2").ntsError('set', Error.ER001);
                        error = true;
                    }
                    if (!error) {
                        var command = {
                            attendDayGettingSet: self.averagePay().attendDayGettingSet(),
                            exceptionPayRate: self.averagePay().exceptionPayRate(),
                            roundDigitSet: self.averagePay().roundDigitSet(),
                            roundTimingSet: self.averagePay().roundTimingSet(),
                            salarySelectedCode: selectedCodeList1,
                            attendSelectedCode: selectedCodeList2
                        };
                        if (isUpdate) {
                            qmm018.a.service.averagePayItemUpdate(command).done(function (data) {
                                dfd.resolve();
                            }).fail(function (res) {
                                dfd.reject();
                            });
                        }
                        else {
                            qmm018.a.service.averagePayItemInsert(command).done(function (data) {
                                dfd.resolve();
                            }).fail(function (res) {
                                dfd.reject();
                            });
                        }
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.openSubWindow = function (n) {
                    var self = this;
                    if (!n) {
                        nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList1());
                        nts.uk.ui.windows.setShared('categoryAtr', 0);
                    }
                    else {
                        nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList2());
                        nts.uk.ui.windows.setShared('categoryAtr', 2);
                    }
                    nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", { title: "対象項目の選択", dialogClass: "no-close" }).onClosed(function () {
                        var selectedList = nts.uk.ui.windows.getShared('selectedItemList');
                        var unSelectedList = nts.uk.ui.windows.getShared('unSelectedItemList');
                        if (!n) {
                            if (selectedList.length) {
                                if (!_.isEqual(selectedList, self.selectedItemList1())) {
                                    self.selectedItemList1.removeAll();
                                    ko.utils.arrayForEach(selectedList, function (item) { self.selectedItemList1.push(item); });
                                    if (unSelectedList.length) {
                                        self.unSelectedItemList1.removeAll();
                                        ko.utils.arrayForEach(unSelectedList, function (item) { self.unSelectedItemList1.push(item); });
                                    }
                                    else {
                                        self.unSelectedItemList1([]);
                                    }
                                }
                            }
                            else {
                                self.selectedItemList1([]);
                            }
                        }
                        else {
                            if (selectedList.length) {
                                if (!_.isEqual(selectedList, self.selectedItemList2())) {
                                    self.selectedItemList2.removeAll();
                                    ko.utils.arrayForEach(selectedList, function (item) { self.selectedItemList2.push(item); });
                                    if (unSelectedList.length) {
                                        self.unSelectedItemList2.removeAll();
                                        ko.utils.arrayForEach(unSelectedList, function (item) { self.unSelectedItemList2.push(item); });
                                    }
                                    else {
                                        self.unSelectedItemList2([]);
                                    }
                                }
                            }
                            else {
                                self.selectedItemList2([]);
                            }
                        }
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
            var AveragePay = (function () {
                function AveragePay(roundTimingSet, attendDayGettingSet, roundDigitSet, exceptionPayRate) {
                    var self = this;
                    self.roundTimingSet = ko.observable(roundTimingSet);
                    self.attendDayGettingSet = ko.observable(attendDayGettingSet);
                    self.roundDigitSet = ko.observable(roundDigitSet);
                    self.exceptionPayRate = ko.observable(exceptionPayRate);
                    self.oldExceptionPayRate = ko.observable(exceptionPayRate);
                    self.roundTimingSet.subscribe(function (value) { self.roundTimingSet(value ? 1 : 0); });
                    self.exceptionPayRate.subscribe(function (value) {
                        if ($("#inp-2").ntsError('set', Error.ER001)) {
                            self.oldExceptionPayRate(exceptionPayRate);
                        }
                        else {
                            exceptionPayRate = value;
                            self.oldExceptionPayRate(value);
                        }
                    });
                }
                return AveragePay;
            }());
            var Error;
            (function (Error) {
                Error[Error["ER001"] = "＊が入力されていません。"] = "ER001";
                Error[Error["ER007"] = "＊が選択されていません。"] = "ER007";
                Error[Error["ER010"] = "対象データがありません。"] = "ER010";
            })(Error || (Error = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.a.viewmodel.js.map