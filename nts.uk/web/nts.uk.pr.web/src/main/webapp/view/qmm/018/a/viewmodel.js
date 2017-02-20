var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.averagePay = ko.observable(new AveragePay(1, 1, 0, 1));
                    self.selectedItemList1 = ko.observableArray([]);
                    self.selectedItemList2 = ko.observableArray([]);
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
                    qmm018.a.service.getAveragePay().done(function (data) {
                        if (data) {
                            qmm018.a.service.getItem(1).done(function (data) {
                                dfd.resolve();
                            }).fail(function (res) {
                            });
                            self.averagePay(new AveragePay(data.roundTimingSet, data.attendDayGettingSet, data.roundDigitSet, data.exceptionPayRate));
                            self.isUpdate = true;
                        }
                        else {
                            self.averagePay(new AveragePay(0, 0, 0, 0));
                            self.isUpdate = false;
                            $("#inp-1").ntsError('set', 'ER007');
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function (isUpdate) {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.averagePay());
                    if (isUpdate) {
                        qmm018.a.service.updateAveragePay(command).done(function (data) {
                            dfd.resolve();
                        }).fail(function (res) {
                        });
                    }
                    else {
                        qmm018.a.service.registerAveragePay(command).done(function (data) {
                            dfd.resolve();
                        }).fail(function (res) {
                        });
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.openSubWindow = function (n) {
                    var self = this;
                    if (!n) {
                        nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList1);
                        nts.uk.ui.windows.setShared('categoryAtr', 1);
                    }
                    else {
                        nts.uk.ui.windows.setShared('selectedItemList', self.selectedItemList2);
                        nts.uk.ui.windows.setShared('categoryAtr', 2);
                    }
                    nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml", { title: "労働日数項目一覧", dialogClass: "no-close" }).onClosed(function () {
                        var selectedList = nts.uk.ui.windows.getShared('selectedItemList');
                        if (!n) {
                            self.selectedItemList1.removeAll();
                            if (selectedList().length) {
                                ko.utils.arrayForEach(selectedList(), function (item) { self.selectedItemList1.push(item); });
                            }
                        }
                        else {
                            self.selectedItemList2.removeAll();
                            if (selectedList().length) {
                                ko.utils.arrayForEach(selectedList(), function (item) { self.selectedItemList2.push(item); });
                            }
                            if (!self.selectedItemList2().length)
                                $("#inp-1").ntsError('set', 'ER007');
                            else
                                $("#inp-1").ntsError('clear');
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
                        if ($("#inp-2").ntsError("hasError")) {
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
