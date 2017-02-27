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
                    self.oldCode = self.currentCode();
                    self.currentItem = ko.observable(new RetirementPayItemModel("", "", "", ""));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findRetirementPayItemList(false).
                        done(function () {
                        dfd.resolve();
                        self.currentCode.subscribe(function (newValue) {
                            if (self.oldCode != newValue) {
                                self.checkDirty(newValue);
                            }
                        });
                    }).
                        fail(function () { });
                    return dfd.promise();
                };
                ScreenModel.prototype.findRetirementPayItemList = function (notFirstTime) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().
                        done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            if (!notFirstTime) {
                                self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                                self.oldCode = self.currentCode();
                            }
                            self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        }
                        dfd.resolve();
                    }).
                        fail(function (res) { self.retirementPayItemList.removeAll(); dfd.resolve(); });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.currentItem());
                    qrm007.a.service.updateRetirementPayItem(command).
                        done(function (data) { self.findRetirementPayItemList(true); dfd.resolve(); }).
                        fail(function (res) { dfd.resolve(); });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (!self.dirty.isDirty()) {
                        self.updateRetirementPayItemList();
                        dfd.resolve();
                    }
                    else {
                        nts.uk.ui.dialog.confirm("Do you want to update Item ?").
                            ifYes(function () { self.updateRetirementPayItemList(); dfd.resolve(); }).
                            ifNo(function () { dfd.resolve(); });
                    }
                    return dfd.promise();
                };
                ScreenModel.prototype.checkDirty = function (newValue) {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        nts.uk.ui.dialog.confirm("Do you want to change Item ?").
                            ifYes(function () {
                            self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                            self.oldCode = newValue;
                        }).
                            ifNo(function () {
                            self.currentCode(self.oldCode);
                        });
                    }
                    else {
                        self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                        self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        self.oldCode = newValue;
                    }
                };
                ;
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItemModel = (function () {
                function RetirementPayItemModel(code, name, printName, memo) {
                    var self = this;
                    self.itemCode = ko.observable(code);
                    self.itemName = ko.observable(name);
                    self.printName = ko.observable(printName);
                    self.memo = ko.observable(memo);
                }
                RetirementPayItemModel.converToObject = function (object) {
                    return new RetirementPayItemModel(object.itemCode, object.itemName, object.printName, object.memo);
                };
                return RetirementPayItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
