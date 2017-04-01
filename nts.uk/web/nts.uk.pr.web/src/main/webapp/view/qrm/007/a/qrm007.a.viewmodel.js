var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.retirementPayItemList = ko.observableArray([]);
                    self.currentCode = ko.observable("");
                    self.currentItem = ko.observable(new RetirementPayItem("", 0, "", "", "", "", "", ""));
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.findRetirementPayItemList(false)
                        .done(function () {
                        $(document).delegate("#LST_001", "iggridselectionrowselectionchanging", function (evt, ui) {
                            if (self.dirty.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。 ").
                                    ifYes(function () {
                                    $('#INP_001').ntsError('clear');
                                    $('#INP_002').ntsError('clear');
                                    self.currentCode(ui.row.id);
                                    self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                                    self.dirty.reset();
                                }).ifNo(function () {
                                    self.currentCode(ui.selectedRows[0].id);
                                });
                            }
                            else {
                                $('#INP_001').ntsError('clear');
                                $('#INP_002').ntsError('clear');
                                self.currentCode(ui.row.id);
                                self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                                self.dirty.reset();
                            }
                        });
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                /**
                 * find retirement payment item by company code
                 * @param notFirstTime : check current find is first time or not
                 */
                ScreenModel.prototype.findRetirementPayItemList = function (notFirstTime) {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.retirePayItemSelect()
                        .done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItem(dataItem.companyCode, dataItem.category, dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.englishName, dataItem.fullName, dataItem.memo)));
                            });
                            if (!notFirstTime) {
                                self.currentCode(_.first(self.retirementPayItemList()).itemCode);
                            }
                            self.currentItem(RetirementPayItem.converToObject(_.find(self.retirementPayItemList(), function (o) { return o.itemCode == self.currentCode(); })));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        }
                        dfd.resolve();
                    })
                        .fail(function (res) {
                        self.retirementPayItemList.removeAll();
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                /**
                 * update retirement payment item
                 */
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.dirty.isDirty()) {
                        var command = ko.mapping.toJS(self.currentItem());
                        qrm007.a.service.retirePayItemUpdate(command)
                            .done(function (data) {
                            self.findRetirementPayItemList(true);
                            dfd.resolve();
                        }).fail(function (res) {
                            dfd.reject(res);
                        });
                    }
                    return dfd.promise();
                };
                /**
                 * event update selected retirement payment item
                 */
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    self.updateRetirementPayItemList();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItem = (function () {
                function RetirementPayItem(companyCode, category, itemCode, itemName, printName, englishName, fullName, memo) {
                    var self = this;
                    self.companyCode = ko.observable(companyCode);
                    self.category = ko.observable(category);
                    self.itemCode = ko.observable(itemCode);
                    self.itemName = ko.observable(itemName);
                    self.printName = ko.observable(printName);
                    self.englishName = ko.observable(englishName);
                    self.fullName = ko.observable(fullName);
                    self.memo = ko.observable(memo);
                }
                RetirementPayItem.converToObject = function (object) {
                    return new RetirementPayItem(object.companyCode, object.category, object.itemCode, object.itemName, object.printName, object.englishName, object.fullName, object.memo);
                };
                return RetirementPayItem;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
//# sourceMappingURL=qrm007.a.viewmodel.js.map