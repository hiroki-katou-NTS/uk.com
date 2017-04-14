var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.averagePay = ko.observable(new AveragePay(null, null, null, null, null, null));
                    self.texteditor3 = ko.observable({
                        value: ko.computed(function () {
                            var s;
                            ko.utils.arrayForEach(self.averagePay().selectedSalaryItems(), function (item) { if (!s) {
                                s = item.itemAbName;
                            }
                            else {
                                s += " + " + item.itemAbName;
                            } });
                            return s;
                        })
                    });
                    self.texteditor1 = ko.observable({
                        value: ko.computed(function () {
                            var s;
                            ko.utils.arrayForEach(self.averagePay().selectedAttendItems(), function (item) { if (!s) {
                                s = item.itemAbName;
                            }
                            else {
                                s += " + " + item.itemAbName;
                            } });
                            return s;
                        })
                    });
                    self.isUpdate = false;
                    self.dirty = new nts.uk.ui.DirtyChecker(self.averagePay);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.a.service.averagePayItemSelect().done(function (data) {
                        if (data) {
                            self.averagePay(new AveragePay(data.roundTimingSet, data.attendDayGettingSet, data.roundDigitSet, data.exceptionPayRate, data.itemsSalary, data.itemsAttend));
                            self.isUpdate = true;
                            self.dirty.reset();
                        }
                        else {
                            self.averagePay(new AveragePay(0, 0, 0, null, null, null));
                            self.isUpdate = false;
                            self.dirty.reset();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var error = false;
                    if (!self.averagePay().selectedSalaryItems().length) {
                        $("#inp-3").ntsError('set', shr.viewmodelbase.Error.ER007);
                        error = true;
                    }
                    if (self.averagePay().attendDayGettingSet() && !self.averagePay().selectedAttendItems().length) {
                        $("#inp-1").ntsError('set', shr.viewmodelbase.Error.ER007);
                        error = true;
                    }
                    if (self.averagePay().exceptionPayRate() == null) {
                        $("#inp-2").ntsError('set', shr.viewmodelbase.Error.ER001);
                        error = true;
                    }
                    if (!error && self.dirty.isDirty()) {
                        var command_1 = {
                            roundTimingSet: self.averagePay().roundTimingSet(),
                            attendDayGettingSet: self.averagePay().attendDayGettingSet(),
                            roundDigitSet: self.averagePay().roundDigitSet(),
                            exceptionPayRate: self.averagePay().exceptionPayRate(),
                            selectedSalaryItems: _.map(self.averagePay().selectedSalaryItems(), function (o) { return o.itemCode; }),
                            selectedAttendItems: _.map(self.averagePay().selectedAttendItems(), function (o) { return o.itemCode; })
                        };
                        if (isUpdate) {
                            qmm018.a.service.averagePayItemUpdate(command_1).done(function (data) {
                                self.dirty.reset();
                            }).fail(function (res) {
                                self.processErrorResponse(res, command_1);
                            });
                        }
                        else {
                            qmm018.a.service.averagePayItemInsert(command_1).done(function (data) {
                                self.dirty.reset();
                            }).fail(function (res) {
                                self.processErrorResponse(res, command_1);
                            });
                        }
                    }
                };
                ScreenModel.prototype.openSubWindow = function (n) {
                    var self = this;
                    if (!n) {
                        nts.uk.ui.windows.setShared('selectedItemList', self.averagePay().selectedSalaryItems());
                        nts.uk.ui.windows.setShared('categoryAtr', shr.viewmodelbase.CategoryAtr.PAYMENT);
                    }
                    else {
                        nts.uk.ui.windows.setShared('selectedItemList', self.averagePay().selectedAttendItems());
                        nts.uk.ui.windows.setShared('categoryAtr', shr.viewmodelbase.CategoryAtr.PERSONAL_TIME);
                    }
                    nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", { title: "対象項目の選択", dialogClass: "no-close" }).onClosed(function () {
                        var selectedList = nts.uk.ui.windows.getShared('selectedItemList');
                        if (!n) {
                            self.loadData(selectedList, self.averagePay().selectedSalaryItems, _.isEqual(selectedList, self.averagePay().selectedSalaryItems()));
                        }
                        else {
                            self.loadData(selectedList, self.averagePay().selectedAttendItems, _.isEqual(selectedList, self.averagePay().selectedAttendItems()));
                        }
                    });
                };
                ScreenModel.prototype.loadData = function (dataSource, dataDestination, isDataEqual) {
                    if (dataSource.length) {
                        if (!isDataEqual) {
                            dataDestination.removeAll();
                            dataSource.forEach(function (item) { dataDestination.push(new shr.viewmodelbase.ItemModel(item.itemCode, item.itemAbName)); });
                        }
                    }
                    else {
                        dataDestination([]);
                    }
                };
                ScreenModel.prototype.processErrorResponse = function (res, command) {
                    if (res.messageId == "ER001") {
                        $("#inp-2").ntsError('set', shr.viewmodelbase.Error.ER001);
                    }
                    if (res.messageId == "ER007") {
                        if (command.selectedSalaryItems.length == 0) {
                            $("#inp-3").ntsError('set', shr.viewmodelbase.Error.ER007);
                        }
                        if (command.attendDayGettingSet && command.selectedAttendItems.length == 0) {
                            $("#inp-1").ntsError('set', shr.viewmodelbase.Error.ER007);
                        }
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var AveragePay = (function () {
                function AveragePay(roundTimingSet, attendDayGettingSet, roundDigitSet, exceptionPayRate, selectedSalaryItems, selectedAttendItems) {
                    var self = this;
                    self.roundTimingSet = ko.observable(roundTimingSet);
                    self.attendDayGettingSet = ko.observable(attendDayGettingSet);
                    self.roundDigitSet = ko.observable(roundDigitSet);
                    self.exceptionPayRate = ko.observable(exceptionPayRate);
                    self.oldExceptionPayRate = ko.observable(exceptionPayRate);
                    self.selectedSalaryItems = ko.observableArray(selectedSalaryItems);
                    self.selectedAttendItems = ko.observableArray(selectedAttendItems);
                    self.roundTimingSet.subscribe(function (value) { self.roundTimingSet(value ? 1 : 0); });
                    self.attendDayGettingSet.subscribe(function (value) {
                        if (!value) {
                            self.roundDigitSet(0);
                            $("#inp-1").ntsError('clear');
                            self.selectedAttendItems([]);
                        }
                    });
                    self.exceptionPayRate.subscribe(function (value) {
                        if ($("#inp-2").ntsError('hasError')) {
                            self.oldExceptionPayRate(exceptionPayRate);
                        }
                        else {
                            exceptionPayRate = value;
                            self.oldExceptionPayRate(value);
                        }
                    });
                    self.selectedSalaryItems.subscribe(function (value) {
                        if (!value.length)
                            $("#inp-3").ntsError('set', shr.viewmodelbase.Error.ER007);
                        else
                            $("#inp-3").ntsError('clear');
                    });
                    self.selectedAttendItems.subscribe(function (value) {
                        if (!self.attendDayGettingSet())
                            $("#inp-1").ntsError('clear');
                        else {
                            if (!value.length)
                                $("#inp-1").ntsError('set', shr.viewmodelbase.Error.ER007);
                            else
                                $("#inp-1").ntsError('clear');
                        }
                    });
                }
                return AveragePay;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.a.viewmodel.js.map