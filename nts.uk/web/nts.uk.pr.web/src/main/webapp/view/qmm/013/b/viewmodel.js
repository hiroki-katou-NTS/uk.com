var qmm013;
(function (qmm013) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.currentItem = ko.observable(new PersonalUnitPrice(null, null, null, null, null, null, null, null, null, null, null, null, null));
                    self.currentCode = ko.observable();
                    self.displayAll = ko.observable(true);
                    self.selectedId = ko.observable(0);
                    self.isCreated = ko.observable(true);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100, key: 'code' },
                        { headerText: '名称', prop: 'name', width: 150, key: 'name' },
                        { headerText: '廃止', prop: 'abolition', width: 50, key: 'abolition' }
                    ]);
                    self.itemList = ko.observableArray([
                        new BoxModel(0, '全員一律で指定する'),
                        new BoxModel(1, '給与約束形態ごとに指定する')
                    ]);
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '0', name: '対象外' }
                    ]);
                    self.SEL_004 = ko.observableArray([
                        { code: '0', name: '時間単価' },
                        { code: '1', name: '金額' },
                        { code: '2', name: 'その他' },
                    ]);
                    self.currentCode.subscribe(function (newCode) {
                        self.selectedUnitPrice(newCode);
                        self.isCreated(false);
                    });
                    self.displayAll.subscribe(function (newValue) {
                        if (!self.checkDirty()) {
                            self.getPersonalUnitPriceList().done(function () {
                                self.selectedFirstUnitPrice();
                            });
                        }
                        else {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                                self.getPersonalUnitPriceList().done(function () {
                                    self.selectedFirstUnitPrice();
                                });
                            });
                        }
                    });
                    self.isCompany = ko.computed(function () {
                        return !(self.currentItem().paymentSettingType() == 0);
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    self.getPersonalUnitPriceList().done(function () {
                        self.selectedFirstUnitPrice();
                    });
                };
                ScreenModel.prototype.getPersonalUnitPriceList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    b.service.getPersonalUnitPriceList(self.displayAll()).done(function (data) {
                        var items = _.map(data, function (item) {
                            var abolition = item.displayAtr == 0 ? '<i class="icon icon-close"></i>' : "";
                            return new ItemModel(item.personalUnitPriceCode, item.personalUnitPriceName, abolition);
                        });
                        self.items(items);
                        dfd.resolve();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.selectedUnitPrice = function (code) {
                    var self = this;
                    if (!code) {
                        return;
                    }
                    b.service.getPersonalUnitPrice(code).done(function (res) {
                        self.fillForm(res);
                    }).fail(function (res) {
                        alert(res.message);
                    });
                };
                ScreenModel.prototype.fillForm = function (data) {
                    var self = this;
                    self.currentItem(new PersonalUnitPrice(data.personalUnitPriceCode, data.personalUnitPriceName, data.personalUnitPriceShortName, data.displayAtr == 0, data.uniteCode, data.paymentSettingType, data.fixPaymentAtr, data.fixPaymentMonthly, data.fixPaymentDayMonth, data.fixPaymentDaily, data.fixPaymentHoursly, data.unitPriceAtr, data.memo));
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                };
                ScreenModel.prototype.checkDirty = function () {
                    var self = this;
                    if (self.dirty.isDirty()) {
                        return true;
                    }
                    else {
                        return false;
                    }
                };
                ;
                ScreenModel.prototype.btn_001 = function () {
                    var self = this;
                    if (!self.checkDirty()) {
                        self.currentItem(new PersonalUnitPrice(null, null, null, null, null, null, null, null, null, null, null, null, null));
                        self.currentCode("");
                        self.isCreated(true);
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                            self.currentItem(new PersonalUnitPrice(null, null, null, null, null, null, null, null, null, null, null, null, null));
                            self.currentCode("");
                            self.isCreated(true);
                        });
                    }
                };
                ScreenModel.prototype.btn_002 = function () {
                    var self = this;
                    var PersonalUnitPrice = {
                        personalUnitPriceCode: self.currentItem().personalUnitPriceCode(),
                        personalUnitPriceName: self.currentItem().personalUnitPriceName(),
                        personalUnitPriceShortName: self.currentItem().personalUnitPriceShortName(),
                        displayAtr: self.currentItem().displayAtr() ? 0 : 1,
                        uniteCode: "2",
                        paymentSettingType: self.currentItem().paymentSettingType(),
                        fixPaymentAtr: self.currentItem().fixPaymentAtr(),
                        fixPaymentMonthly: self.currentItem().fixPaymentMonthly(),
                        fixPaymentDayMonth: self.currentItem().fixPaymentDayMonth(),
                        fixPaymentDaily: self.currentItem().fixPaymentDaily(),
                        fixPaymentHoursly: self.currentItem().fixPaymentHoursly(),
                        unitPriceAtr: self.currentItem().unitPriceAtr(),
                        memo: self.currentItem().memo()
                    };
                    b.service.addPersonalUnitPrice(self.isCreated(), PersonalUnitPrice).done(function () {
                        self.currentCode(PersonalUnitPrice.personalUnitPriceCode);
                        self.getPersonalUnitPriceList();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                ScreenModel.prototype.btn_004 = function () {
                    var self = this;
                    if (!self.checkDirty()) {
                        var data = {
                            personalUnitPriceCode: self.currentItem().personalUnitPriceCode()
                        };
                        b.service.removePersonalUnitPrice(data).done(function () {
                            // reload list
                            self.getPersonalUnitPriceList();
                        }).fail(function (error) {
                            alert(error.message);
                        });
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                            var data = {
                                personalUnitPriceCode: self.currentItem().personalUnitPriceCode()
                            };
                            b.service.removePersonalUnitPrice(data).done(function () {
                                // reload list
                                self.getPersonalUnitPriceList();
                            }).fail(function (error) {
                                alert(error.message);
                            });
                        });
                    }
                };
                ScreenModel.prototype.selectedFirstUnitPrice = function () {
                    var self = this;
                    if (self.items().length > 0) {
                        self.currentCode(self.items()[0].code);
                    }
                    else {
                        self.btn_001();
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, abolition) {
                    this.code = code;
                    this.name = name;
                    this.abolition = abolition;
                }
                return ItemModel;
            }());
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            var PersonalUnitPrice = (function () {
                function PersonalUnitPrice(personalUnitPriceCode, personalUnitPriceName, personalUnitPriceShortName, displayAtr, uniteCode, paymentSettingType, fixPaymentAtr, fixPaymentMonthly, fixPaymentDayMonth, fixPaymentDaily, fixPaymentHoursly, unitPriceAtr, memo) {
                    this.personalUnitPriceCode = ko.observable(personalUnitPriceCode);
                    this.personalUnitPriceName = ko.observable(personalUnitPriceName);
                    this.personalUnitPriceShortName = ko.observable(personalUnitPriceShortName);
                    this.displayAtr = ko.observable(displayAtr);
                    this.uniteCode = ko.observable(uniteCode);
                    this.paymentSettingType = ko.observable(paymentSettingType);
                    this.fixPaymentAtr = ko.observable(fixPaymentAtr);
                    this.fixPaymentMonthly = ko.observable(fixPaymentMonthly);
                    this.fixPaymentDayMonth = ko.observable(fixPaymentDayMonth);
                    this.fixPaymentDaily = ko.observable(fixPaymentDaily);
                    this.fixPaymentHoursly = ko.observable(fixPaymentHoursly);
                    this.unitPriceAtr = ko.observable(unitPriceAtr);
                    this.memo = ko.observable(memo);
                }
                return PersonalUnitPrice;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm013.b || (qmm013.b = {}));
})(qmm013 || (qmm013 = {}));
