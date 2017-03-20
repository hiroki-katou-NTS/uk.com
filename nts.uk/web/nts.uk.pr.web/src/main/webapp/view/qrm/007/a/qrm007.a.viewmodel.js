var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.retirementPayItemList = ko.observableArray([new RetirementPayItem(null, null, null, null)]);
                    self.currentCode = ko.observable(0);
                    self.currentItem = ko.observable(new RetirementPayItem("", "", "", ""));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findRetirementPayItemList(false).
                        done(function () {
                        $(document).delegate("#lst-001", "iggridselectionrowselectionchanging", function (evt, ui) {
                            if (self.dirty.isDirty()) {
                                nts.uk.ui.dialog.confirm("Do you want to change Item ?").
                                    ifYes(function () {
                                    $('#inp-1').ntsError('clear');
                                    self.currentCode(ui.row.id);
                                    self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                                }).ifNo(function () {
                                    self.currentCode(ui.selectedRows[0].id);
                                });
                            }
                            else {
                                $('#inp-1').ntsError('clear');
                                self.currentCode(ui.row.id);
                                self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                                self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                            }
                        });
                        dfd.resolve();
                    }).fail(function () { });
                    return dfd.promise();
                };
                ScreenModel.prototype.findRetirementPayItemList = function (notFirstTime) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.qremt_Retire_Pay_Item_SEL_1().
                        done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItem(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            if (!notFirstTime) {
                                self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            }
                            self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        }
                        dfd.resolve();
                    }).
                        fail(function (res) { self.retirementPayItemList.removeAll(); dfd.reject(); });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.currentItem());
                    qrm007.a.service.qremt_Retire_Pay_Item_UPD_1(command).
                        done(function (data) {
                        self.findRetirementPayItemList(true);
                        nts.uk.ui.dialog.alert("Update Success");
                        dfd.resolve();
                    }).fail(function (res) {
                        nts.uk.ui.dialog.alert("Update Fail");
                        dfd.reject();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.updateRetirementPayItemList().done(function () { dfd.resolve(); }).fail(function () { dfd.reject(); });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItem = (function () {
                function RetirementPayItem(code, name, printName, memo) {
                    var self = this;
                    self.itemCode = ko.observable(code);
                    self.itemName = ko.observable(name);
                    self.printName = ko.observable(printName);
                    self.memo = ko.observable(memo);
                }
                RetirementPayItem.converToObject = function (object) {
                    return new RetirementPayItem(object.itemCode, object.itemName, object.printName, object.memo);
                };
                return RetirementPayItem;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
