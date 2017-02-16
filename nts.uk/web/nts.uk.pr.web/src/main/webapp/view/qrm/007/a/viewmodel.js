var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel(null, null, null, null)]);
                    self.currentCode = ko.observable();
                    self.currentItem = ko.observable(new RetirementPayItemModel(null, null, null, null));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                        }
                        dfd.resolve();
                        self.currentCode.subscribe(function (newValue) {
                            $('.data-required').ntsError('clear');
                            self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (item) { return item.itemCode == newValue; })));
                        });
                    }).fail(function (res) {
                        self.retirementPayItemList.removeAll();
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.findRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        self.retirementPayItemList.removeAll();
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.currentItem().onchange()) {
                        var command = ko.mapping.toJS(self.currentItem());
                        console.log(self.currentItem());
                        qrm007.a.service.updateRetirementPayItem(command).done(function (data) {
                            qrm007.a.service.getRetirementPayItemList().done(function (data) {
                                self.retirementPayItemList.removeAll();
                                if (data.length) {
                                    data.forEach(function (dataItem) {
                                        self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                                    });
                                }
                                self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (item) { return item.itemCode == self.currentCode(); })));
                                dfd.resolve();
                            }).fail(function (res) {
                                self.retirementPayItemList.removeAll();
                                dfd.resolve();
                            });
                        }).fail(function (res) {
                            dfd.resolve();
                        });
                    }
                    else {
                        nts.uk.ui.dialog.alert("＊が入力されていません。");
                    }
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItemModel = (function () {
                function RetirementPayItemModel(code, name, printName, memo) {
                    var self = this;
                    self.onchange = ko.observable(false);
                    self.itemCode = ko.observable(code);
                    self.itemName = ko.observable(name);
                    self.printName = ko.observable(printName);
                    self.memo = ko.observable(memo);
                    self.printName.subscribe(function (value) {
                        console.log(value + "              " + printName);
                        (value == printName) ? self.onchange(false) : self.onchange(true);
                        console.log(self.onchange());
                    });
                }
                RetirementPayItemModel.converToObject = function (object) {
                    return new RetirementPayItemModel(object.itemCode, object.itemName, object.printName, object.memo);
                };
                return RetirementPayItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
