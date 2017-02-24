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
                    self.currentCode = ko.observable(0);
                    self.currentItem = ko.observable(new RetirementPayItemModel("", "", "", ""));
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem().printName);
                    self.currentItem().printName.subscribe(function (value) { console.log(value); });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findRetirementPayItemList(false).done(function () {
                        self.currentCode.subscribe(function (newValue) {
                            self.checkDirty();
                        });
                    }).fail(function () { });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.findRetirementPayItemList = function (firstTime) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            if (!firstTime) {
                                self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            }
                            self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                        }
                        dfd.resolve();
                        self.currentCode.subscribe(function (newValue) {
                            self.checkDirty();
                        });
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
                        qrm007.a.service.updateRetirementPayItem(command).done(function (data) {
                            self.findRetirementPayItemList(true);
                        }).fail(function (res) {
                        });
                    }
                    else {
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.checkDirty = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        alert("Data is changed.");
                    }
                    else {
                        self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                    }
                };
                ;
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
                    self.printName.subscribe(function (value) { (value == printName) ? self.onchange(false) : self.onchange(true); });
                }
                RetirementPayItemModel.converToObject = function (object) {
                    return new RetirementPayItemModel(object.itemCode, object.itemName, object.printName, object.memo);
                };
                return RetirementPayItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
