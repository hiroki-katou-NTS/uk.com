var qmm013;
(function (qmm013) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.confirmDirty = false;
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.currentItem = ko.observable(new PersonalUnitPrice(null, null, null, null, null, null, null, null, null, null, null, null, null));
                    self.currentCode = ko.observable();
                    self.displayAll = ko.observable(true);
                    self.selectedId = ko.observable(0);
                    self.isCreated = ko.observable(true);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.indexRow = ko.observable(0);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', width: 100, key: 'code', formatter: _.escape },
                        { headerText: '名称', width: 150, key: 'name', formatter: _.escape },
                        { headerText: '廃止', width: 50, key: 'abolition', }
                    ]);
                    self.messages = ko.observableArray([
                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\nよろしいですか。" },
                        { messageId: "ER001", message: "＊が入力されていません。" },
                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n＊を確認してください。" },
                        { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" }
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
                        if (!self.checkDirty()) {
                            self.selectedUnitPrice(newCode);
                            self.isCreated(false);
                        }
                        else {
                            if (self.confirmDirty) {
                                self.confirmDirty = false;
                                return;
                            }
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                                self.selectedUnitPrice(newCode);
                                self.isCreated(false);
                            }).ifNo(function () {
                                self.confirmDirty = true;
                                self.currentCode(self.currentItem().personalUnitPriceCode());
                            });
                        }
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
                    var dfd = $.Deferred();
                    self.getPersonalUnitPriceList().done(function () {
                        self.selectedFirstUnitPrice();
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getPersonalUnitPriceList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getPersonalUnitPriceList(self.displayAll()).done(function (data) {
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
                    a.service.getPersonalUnitPrice(code).done(function (res) {
                        self.currentItem(self.selectedFirst(res));
                        self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    }).fail(function (res) {
                        alert(res.message);
                    });
                };
                ScreenModel.prototype.selectedFirst = function (item) {
                    var self = this;
                    return new PersonalUnitPrice(item.personalUnitPriceCode, item.personalUnitPriceName, item.personalUnitPriceShortName, item.displayAtr == 0, item.uniteCode, item.paymentSettingType, item.fixPaymentAtr, item.fixPaymentMonthly, item.fixPaymentDayMonth, item.fixPaymentDaily, item.fixPaymentHoursly, item.unitPriceAtr, item.memo);
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
                /**
                 * 新規(Clear form)
                 */
                ScreenModel.prototype.btn_001 = function () {
                    var self = this;
                    self.clearError();
                    self.confirmDirty = true;
                    if (!self.checkDirty()) {
                        self.currentItem(new PersonalUnitPrice(null, null, null, false, "2", 1, null, false, false, false, false, false, null));
                        self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                        self.currentCode("");
                        self.isCreated(true);
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                            self.currentItem(new PersonalUnitPrice(null, null, null, false, "2", 1, null, false, false, false, false, false, null));
                            self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                            self.currentCode("");
                            self.isCreated(true);
                        });
                    }
                };
                ScreenModel.prototype.Opendialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal("/view/qmm/013/b/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
                };
                /**
                 * 登録(Add button)
                 */
                ScreenModel.prototype.btn_002 = function () {
                    var self = this;
                    self.confirmDirty = true;
                    var PersonalUnitPrice = {
                        personalUnitPriceCode: self.currentItem().personalUnitPriceCode(),
                        personalUnitPriceName: self.currentItem().personalUnitPriceName(),
                        personalUnitPriceShortName: self.currentItem().personalUnitPriceShortName(),
                        displayAtr: self.currentItem().displayAtr() ? 0 : 1,
                        uniteCode: "2",
                        paymentSettingType: self.currentItem().paymentSettingType(),
                        fixPaymentAtr: self.currentItem().fixPaymentAtr(),
                        fixPaymentMonthly: self.currentItem().fixPaymentMonthly() ? 0 : 1,
                        fixPaymentDayMonth: self.currentItem().fixPaymentDayMonth() ? 0 : 1,
                        fixPaymentDaily: self.currentItem().fixPaymentDaily() ? 0 : 1,
                        fixPaymentHoursly: self.currentItem().fixPaymentHoursly() ? 0 : 1,
                        unitPriceAtr: self.currentItem().unitPriceAtr() ? 0 : 1,
                        memo: self.currentItem().memo()
                    };
                    a.service.addPersonalUnitPrice(self.isCreated(), PersonalUnitPrice).done(function () {
                        self.getPersonalUnitPriceList();
                        self.currentCode(PersonalUnitPrice.personalUnitPriceCode);
                        self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    }).fail(function (error) {
                        if (error.messageId == self.messages()[2].messageId) {
                            nts.uk.ui.dialog.alert(self.messages()[2].message);
                        }
                        else if (error.messageId == self.messages()[1].messageId) {
                            $('#INP_002').ntsError('set', self.messages()[1].message);
                            $('#INP_003').ntsError('set', self.messages()[1].message);
                        }
                    });
                };
                /**
                 * 削除(Delete button)
                 */
                ScreenModel.prototype.btn_004 = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        var data = {
                            personalUnitPriceCode: self.currentItem().personalUnitPriceCode()
                        };
                        self.indexRow(_.findIndex(self.items(), function (x) {
                            return x.code === self.currentCode();
                        }));
                        a.service.removePersonalUnitPrice(data).done(function () {
                            // reload list   
                            self.getPersonalUnitPriceList().done(function () {
                                if (self.items().length > self.indexRow()) {
                                    self.currentCode(self.items()[self.indexRow()].code);
                                }
                                else if (self.indexRow() == 0) {
                                    self.btn_001();
                                }
                                else if (self.items().length == self.indexRow() && self.indexRow() != 0) {
                                    self.currentCode(self.items()[self.indexRow() - 1].code);
                                }
                            });
                        }).fail(function (error) {
                            alert(error.message);
                        });
                    });
                };
                ScreenModel.prototype.selectedFirstUnitPrice = function () {
                    var self = this;
                    if (self.items().length > 0) {
                        self.currentCode(self.items()[0].code);
                        self.selectedUnitPrice(self.items()[0].code);
                    }
                    else {
                        self.btn_001();
                    }
                };
                ScreenModel.prototype.clearError = function () {
                    $('#INP_002').ntsError('clear');
                    $('#INP_003').ntsError('clear');
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm013.a || (qmm013.a = {}));
})(qmm013 || (qmm013 = {}));
