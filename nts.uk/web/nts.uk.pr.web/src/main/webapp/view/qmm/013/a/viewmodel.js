var qmm013;
(function (qmm013) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.currentName = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
                    self.items = ko.observableArray([]);
                    self.currentItem = ko.observable();
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 100, key: 'code' },
                        { headerText: '名称', prop: 'name', width: 150, key: 'name' },
                        { headerText: '廃止', prop: 'abolition', width: 50, key: 'abolition' }
                    ]);
                    self.messages = ko.observableArray([
                        { messageId: "AL001", message: "変更された内容が登録されていません。\r\nよろしいですか。" },
                        { messageId: "ER001", message: "＊が入力されていません。" },
                        { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n＊を確認してください。" },
                        { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" }
                    ]);
                    self.currentCode = ko.observable();
                    self.currentCode.subscribe(function (newCode) {
                        self.selectedUnitPrice(newCode);
                    });
                    self.itemList = ko.observableArray([
                        new BoxModel(0, '全員一律で指定する'),
                        new BoxModel(1, '給与約束形態ごとに指定する')
                    ]);
                    self.selectedId = ko.observable(0);
                    self.isCompany = ko.computed(function () {
                        return !(self.selectedId() == 0);
                    });
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '0', name: '対象外' }
                    ]);
                    self.SEL_004 = ko.observableArray([
                        { code: '0', name: '時間単価' },
                        { code: '1', name: '金額' },
                        { code: '2', name: 'その他' },
                    ]);
                    self.SEL_005_check = ko.observable(0);
                    self.SEL_006_check = ko.observable(0);
                    self.SEL_007_check = ko.observable(0);
                    self.SEL_008_check = ko.observable(0);
                    self.SEL_009_check = ko.observable(0);
                    self.SEL_004_check = ko.observable(0);
                    self.uniteCode = ko.observable("002");
                    self.displayAtr = ko.observable(false);
                    self.displayAll = ko.observable(true);
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
                    self.INP_002 = {
                        value: ko.observable(null),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "45px",
                            textalign: "center"
                        })),
                        enable: ko.observable(true)
                    };
                    self.INP_003 = {
                        value: ko.observable(null),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "180px",
                            textalign: "left"
                        })),
                        enable: ko.observable(true)
                    };
                    self.inp_004 = {
                        value: ko.observable(null),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "180px",
                            textalign: "left"
                        })),
                        enable: ko.observable(true)
                    };
                    self.INP_005 = {
                        value: ko.observable(null),
                        option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                            resizeable: true,
                            placeholder: "",
                            width: "450px",
                            textalign: "left"
                        })),
                    };
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
                ScreenModel.prototype.selectedFirstUnitPrice = function () {
                    var self = this;
                    if (self.items().length > 0) {
                        self.currentCode(self.items()[0].code);
                    }
                    else {
                        self.INP_002.enable(true);
                        self.btn_001();
                    }
                };
                ScreenModel.prototype.selectedUnitPrice = function (code) {
                    var self = this;
                    if (!code) {
                        return;
                    }
                    a.service.getPersonalUnitPrice(code).done(function (res) {
                        self.fillForm(res);
                    }).fail(function (res) {
                        alert(res.message);
                    });
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
                 * Reset form
                 */
                ScreenModel.prototype.fillForm = function (data) {
                    var self = this;
                    debugger;
                    self.currentItem(new PersonalUnitPrice(data.personalUnitPriceCode, data.personalUnitPriceName, data.personalUnitPriceShortName, data.displayAtr == 0, data.uniteCode, data.paymentSettingType, data.fixPaymentAtr, data.fixPaymentMonthly, data.fixPaymentDayMonth, data.fixPaymentDaily, data.fixPaymentHoursly, data.unitPriceAtr, data.memo));
                    self.dirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.SEL_005_check(data.fixPaymentAtr);
                    self.SEL_006_check(data.fixPaymentMonthly);
                    self.SEL_007_check(data.fixPaymentDayMonth);
                    self.SEL_008_check(data.fixPaymentDaily);
                    self.SEL_009_check(data.fixPaymentHoursly);
                    self.SEL_004_check(data.unitPriceAtr);
                    self.INP_002.value(data.personalUnitPriceCode);
                    self.INP_002.enable(false);
                    self.INP_003.value(data.personalUnitPriceName);
                    self.INP_003.enable(true);
                    self.inp_004.value(self.currentItem().personalUnitPriceShortName());
                    self.INP_005.value(data.memo);
                    self.uniteCode(data.uniteCode);
                    self.displayAtr(data.displayAtr == 0);
                    self.selectedId(data.paymentSettingType);
                };
                /**
                 * Clean form
                 */
                ScreenModel.prototype.btn_001 = function () {
                    var self = this;
                    self.SEL_005_check(0);
                    self.SEL_006_check(0);
                    self.SEL_007_check(0);
                    self.SEL_008_check(0);
                    self.SEL_009_check(0);
                    self.SEL_004_check(0);
                    self.INP_002.value("");
                    self.INP_002.enable(true);
                    self.INP_003.value("");
                    self.INP_003.enable(true);
                    self.inp_004.value("");
                    self.INP_005.value("");
                    self.uniteCode("");
                };
                /**
                 * Update and Add
                 */
                ScreenModel.prototype.btn_002 = function () {
                    var self = this;
                    var PersonalUnitPrice = {
                        personalUnitPriceCode: self.INP_002.value(),
                        personalUnitPriceName: self.INP_003.value(),
                        personalUnitPriceShortName: self.inp_004.value(),
                        displayAtr: self.displayAtr() ? 0 : 1,
                        uniteCode: self.uniteCode(),
                        paymentSettingType: self.selectedId(),
                        fixPaymentAtr: self.SEL_005_check(),
                        fixPaymentMonthly: self.SEL_006_check(),
                        fixPaymentDayMonth: self.SEL_007_check(),
                        fixPaymentDaily: self.SEL_008_check(),
                        fixPaymentHoursly: self.SEL_009_check(),
                        unitPriceAtr: self.SEL_004_check(),
                        memo: self.INP_005.value()
                    };
                    a.service.addPersonalUnitPrice(self.INP_002.enable(), PersonalUnitPrice).done(function () {
                        self.selectedUnitPrice(self.INP_002.value());
                        self.getPersonalUnitPriceList();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                /**
                 * Delete
                 */
                ScreenModel.prototype.btn_004 = function () {
                    var self = this;
                    if (!self.checkDirty()) {
                        var data = {
                            personalUnitPriceCode: self.INP_002.value()
                        };
                        a.service.removePersonalUnitPrice(data).done(function () {
                            // reload list
                            self.getPersonalUnitPriceList();
                        }).fail(function (error) {
                            alert(error.message);
                        });
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。").ifYes(function () {
                            var data = {
                                personalUnitPriceCode: self.INP_002.value()
                            };
                            a.service.removePersonalUnitPrice(data).done(function () {
                                // reload list
                                self.getPersonalUnitPriceList();
                            }).fail(function (error) {
                                alert(error.message);
                            });
                        });
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
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm013.a || (qmm013.a = {}));
})(qmm013 || (qmm013 = {}));
