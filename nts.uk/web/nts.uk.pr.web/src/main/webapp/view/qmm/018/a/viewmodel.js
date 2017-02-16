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
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedItemList1 = ko.observableArray([]);
                    self.selectedItemList2 = ko.observableArray([]);
                    self.texteditor1 = {
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
                    };
                    self.texteditor2 = {
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
                    };
                    self.averagePay().roundTimingSet.subscribe(function (value) { self.averagePay().roundTimingSet(value ? 1 : 0); });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.a.service.getPaymentDateProcessingList().done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.averagePay());
                    qmm018.a.service.saveData(command).done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.averagePay());
                    qmm018.a.service.updateData(command).done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.removeData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.averagePay());
                    qmm018.a.service.removeData(command).done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openSubWindow = function (n) {
                    var self = this;
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
                function AveragePay(roundDigitSet, attendDayGettingSet, roundTimingSet, exceptionPayRate) {
                    this.roundDigitSet = ko.observable(roundDigitSet);
                    this.attendDayGettingSet = ko.observable(attendDayGettingSet);
                    this.roundTimingSet = ko.observable(roundTimingSet);
                    this.exceptionPayRate = ko.observable(exceptionPayRate);
                }
                return AveragePay;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
