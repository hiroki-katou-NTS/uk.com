var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var l;
                    (function (l) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(param) {
                                    var self = this;
                                    self.paramIsUpdate = param.isUpdate;
                                    self.paramDirtyData = param.dirtyData;
                                    self.startYm = param.startYm;
                                    self.initOriginal();
                                    if (self.paramIsUpdate === true) {
                                        self.easyFormulaName(self.paramDirtyData.easyFormulaName);
                                        self.comboBoxFormulaType().selectedCode(self.paramDirtyData.easyFormulaTypeAtr.toString());
                                        self.comboBoxBaseAmount().selectedCode(self.paramDirtyData.baseAmountDevision.toString());
                                        self.baseAmountFixedValue(self.paramDirtyData.baseFixedAmount);
                                        self.baseAmountListItem(self.paramDirtyData.referenceItemCodes);
                                        self.comboBoxBaseValue().selectedCode(self.paramDirtyData.baseValueDevision.toString());
                                        self.baseValueFixedValue(self.paramDirtyData.baseFixedValue);
                                        self.premiumRate(self.paramDirtyData.premiumRate);
                                        self.switchButtonRoundingFiguresD().selectedRuleCode(self.paramDirtyData.roundProcessingDevision.toString());
                                        self.switchButtonRoundingFiguresF().selectedRuleCode(self.paramDirtyData.totalRounding.toString());
                                        self.comboBoxCoefficient().selectedCode(self.paramDirtyData.coefficientDivision.toString());
                                        self.coefficientFixedValue(self.paramDirtyData.coefficientFixedValue);
                                        self.comboBoxAdjustmentAtr().selectedCode(self.paramDirtyData.adjustmentDevision.toString());
                                    }
                                }
                                ScreenModel.prototype.exportTextListItemName = function (lstItem, lstItemCode) {
                                    var textListItemName = '';
                                    _.forEach(lstItemCode, function (itemCode) {
                                        textListItemName += (_.find(lstItem, function (item) { return item.code == itemCode; }).name + ' ');
                                    });
                                    return textListItemName;
                                };
                                ScreenModel.prototype.initOriginal = function () {
                                    var self = this;
                                    self.easyFormulaName = ko.observable(null);
                                    self.comboBoxFormulaType = ko.observable(new ComboBox([
                                        new ItemModel('0', '計算式１'),
                                        new ItemModel('1', '計算式２'),
                                        new ItemModel('2', '計算式3')
                                    ]));
                                    self.comboBoxBaseAmount = ko.observable(new ComboBox([
                                        new ItemModel('0', '固定値'),
                                        new ItemModel('2', '個人単価'),
                                        new ItemModel('3', '支給項目'),
                                        new ItemModel('4', '控除項目')
                                    ]));
                                    self.comboBoxFormulaType().selectedCode.subscribe(function (codeChange) {
                                        if (codeChange === '2') {
                                            self.comboBoxBaseAmount().itemList([
                                                new ItemModel('0', '固定値'),
                                                new ItemModel('1', '会社単価'),
                                                new ItemModel('3', '支給項目'),
                                                new ItemModel('4', '控除項目')
                                            ]);
                                        }
                                        else {
                                            self.comboBoxBaseAmount().itemList([
                                                new ItemModel('0', '固定値'),
                                                new ItemModel('2', '個人単価'),
                                                new ItemModel('3', '支給項目'),
                                                new ItemModel('4', '控除項目')
                                            ]);
                                        }
                                    });
                                    self.baseAmountFixedValueOption = ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                                        grouplength: 3,
                                        decimallength: 2,
                                        currencyformat: "JPY"
                                    }));
                                    self.baseAmountFixedValue = ko.observable(0);
                                    self.baseAmountListItem = ko.observableArray([]);
                                    self.baseAmountSelectionItems = ko.observable('');
                                    self.optionBaseAmountSelectionItems = ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                                        textmode: "text",
                                        placeholder: "",
                                        width: "290px",
                                        textalign: "left"
                                    }));
                                    self.comboBoxBaseValue = ko.observable(new ComboBox([
                                        new ItemModel('0', '固定値'),
                                        new ItemModel('1', '基準日数'),
                                        new ItemModel('2', '要勤務日数'),
                                        new ItemModel('3', '出勤日数'),
                                        new ItemModel('4', '出勤日数+有給使用数'),
                                        new ItemModel('5', '基準日数*基準時間'),
                                        new ItemModel('6', '要勤務日数*基準時間'),
                                        new ItemModel('7', '出勤日数*基準時間'),
                                        new ItemModel('8', '(出勤日数+有給使用数)*基準時間'),
                                        new ItemModel('9', '出勤時間')
                                    ]));
                                    self.baseValueFixedValue = ko.observable(0);
                                    self.premiumRate = ko.observable(0);
                                    self.premiumRateInputOption = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 0,
                                        symbolChar: '%'
                                    }));
                                    self.switchButtonRoundingFiguresD = ko.observable(new SwitchButton([
                                        new ItemModel('0', '四捨五入'),
                                        new ItemModel('1', '切り上げ'),
                                        new ItemModel('2', '切り捨て'),
                                        new ItemModel('3', '端数処理なし')
                                    ]));
                                    self.switchButtonRoundingFiguresF = ko.observable(new SwitchButton([
                                        new ItemModel('0', '四捨五入'),
                                        new ItemModel('1', '切り上げ'),
                                        new ItemModel('2', '切り捨て')
                                    ]));
                                    self.comboBoxCoefficient = ko.observable(new ComboBox([
                                        new ItemModel('0000', '固定値'),
                                        new ItemModel('F200', '基準日数'),
                                        new ItemModel('F000', '出勤日数+有給使用数')
                                    ]));
                                    self.coefficientFixedValue = ko.observable(0);
                                    self.comboBoxAdjustmentAtr = ko.observable(new ComboBox([
                                        new ItemModel('0', '調整しない'),
                                        new ItemModel('1', 'プラス調整'),
                                        new ItemModel('2', 'マイナス調整'),
                                        new ItemModel('3', 'プラスマイナス反転')
                                    ]));
                                    self.companyUnitPriceItems = ko.observableArray([]);
                                    self.personalUnitPriceItems = ko.observableArray([]);
                                    self.paymentItems = ko.observableArray([]);
                                    self.deductionItems = ko.observableArray([]);
                                };
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfdMaster = $.Deferred();
                                    var dfdGetListCompanyUP = $.Deferred();
                                    var dfdGetListPersonalUP = $.Deferred();
                                    var dfdGetPaymentItems = $.Deferred();
                                    var dfdGetDeductionItems = $.Deferred();
                                    var dfdGetTimeItems = $.Deferred();
                                    l.service.getListItemMaster(2)
                                        .done(function (lstItem) {
                                        if (lstItem && lstItem.length > 0) {
                                            _.forEach(lstItem, function (item) {
                                                self.comboBoxCoefficient().itemList.push(new ItemModel(item.itemCode, item.itemName));
                                            });
                                        }
                                        dfdGetTimeItems.resolve();
                                    })
                                        .fail(function () {
                                        alert(res);
                                        dfdGetTimeItems.reject();
                                    });
                                    l.service.getListCompanyUnitPrice(self.startYm)
                                        .done(function (lstCompanyUnitPrice) {
                                        if (lstCompanyUnitPrice && lstCompanyUnitPrice.length > 0) {
                                            _.forEach(lstCompanyUnitPrice, function (item) {
                                                self.companyUnitPriceItems.push(new ItemModel(item.companyUnitPriceCode, item.companyUnitPriceName));
                                            });
                                        }
                                        dfdGetListCompanyUP.resolve();
                                    }).fail(function () {
                                        dfdGetListCompanyUP.reject();
                                    });
                                    l.service.getListPersonalUnitPrice()
                                        .done(function (lstPersonalUnitPrice) {
                                        if (lstPersonalUnitPrice && lstPersonalUnitPrice.length > 0) {
                                            _.forEach(lstPersonalUnitPrice, function (item) {
                                                self.personalUnitPriceItems.push(new ItemModel(item.personalUnitPriceCode, item.personalUnitPriceName));
                                            });
                                        }
                                        dfdGetListPersonalUP.resolve();
                                    }).fail(function () {
                                        dfdGetListPersonalUP.reject();
                                    });
                                    l.service.getListItemMaster(0)
                                        .done(function (lstItem) {
                                        if (lstItem && lstItem.length > 0) {
                                            _.forEach(lstItem, function (item) {
                                                self.paymentItems.push(new ItemModel(item.itemCode, item.itemName));
                                            });
                                        }
                                        dfdGetPaymentItems.resolve();
                                    })
                                        .fail(function () {
                                        alert(res);
                                        dfdGetPaymentItems.reject();
                                    });
                                    l.service.getListItemMaster(1)
                                        .done(function (lstItem) {
                                        if (lstItem && lstItem.length > 0) {
                                            _.forEach(lstItem, function (item) {
                                                self.deductionItems.push(new ItemModel(item.itemCode, item.itemName));
                                            });
                                        }
                                        dfdGetDeductionItems.resolve();
                                    })
                                        .fail(function () {
                                        alert(res);
                                        dfdGetDeductionItems.reject();
                                    });
                                    $.when(dfdGetTimeItems.promise(), dfdGetListCompanyUP.promise(), dfdGetListPersonalUP.promise(), dfdGetPaymentItems.promise(), dfdGetDeductionItems.promise())
                                        .done(function () {
                                        if (self.baseAmountListItem().length > 0) {
                                            var textListItemName = '';
                                            if (self.comboBoxBaseAmount().selectedCode() === '1') {
                                                textListItemName = self.exportTextListItemName(self.companyUnitPriceItems(), self.baseAmountListItem());
                                            }
                                            else if (self.comboBoxBaseAmount().selectedCode() === '2') {
                                                textListItemName = self.exportTextListItemName(self.personalUnitPriceItems(), self.baseAmountListItem());
                                            }
                                            else if (self.comboBoxBaseAmount().selectedCode() === '3') {
                                                textListItemName = self.exportTextListItemName(self.paymentItems(), self.baseAmountListItem());
                                            }
                                            else if (self.comboBoxBaseAmount().selectedCode() === '4') {
                                                textListItemName = self.exportTextListItemName(self.deductionItems(), self.baseAmountListItem());
                                            }
                                        }
                                        else {
                                            self.baseAmountSelectionItems('');
                                        }
                                        ;
                                        self.baseAmountSelectionItems(textListItemName);
                                        self.baseAmountListItem.subscribe(function (lstItem) {
                                            if (lstItem && lstItem.length > 0) {
                                                var textListItemName = '';
                                                if (self.comboBoxBaseAmount().selectedCode() === '1') {
                                                    textListItemName = self.exportTextListItemName(self.companyUnitPriceItems(), lstItem);
                                                }
                                                else if (self.comboBoxBaseAmount().selectedCode() === '2') {
                                                    textListItemName = self.exportTextListItemName(self.personalUnitPriceItems(), lstItem);
                                                }
                                                else if (self.comboBoxBaseAmount().selectedCode() === '3') {
                                                    textListItemName = self.exportTextListItemName(self.paymentItems(), lstItem);
                                                }
                                                else if (self.comboBoxBaseAmount().selectedCode() === '4') {
                                                    textListItemName = self.exportTextListItemName(self.deductionItems(), lstItem);
                                                }
                                                self.baseAmountSelectionItems(textListItemName);
                                            }
                                            else {
                                                self.baseAmountSelectionItems('');
                                            }
                                        });
                                        dfdMaster.resolve();
                                    })
                                        .fail(function () {
                                        dfdMaster.reject();
                                    });
                                    return dfdMaster.promise();
                                };
                                ScreenModel.prototype.openDialogP = function () {
                                    var self = this;
                                    var param = {
                                        subject: '',
                                        itemList: [],
                                        selectedItems: self.baseAmountListItem()
                                    };
                                    if (self.comboBoxBaseAmount().selectedCode() === '1') {
                                        param.subject = '会社単価';
                                        param.itemList = self.companyUnitPriceItems();
                                    }
                                    else if (self.comboBoxBaseAmount().selectedCode() === '2') {
                                        param.subject = '個人単価';
                                        param.itemList = self.personalUnitPriceItems();
                                    }
                                    else if (self.comboBoxBaseAmount().selectedCode() === '3') {
                                        param.subject = '支給項目';
                                        param.itemList = self.paymentItems();
                                    }
                                    else if (self.comboBoxBaseAmount().selectedCode() === '4') {
                                        param.subject = '控除項目';
                                        param.itemList = self.deductionItems();
                                    }
                                    nts.uk.ui.windows.setShared('paramFromScreenL', param);
                                    nts.uk.ui.windows.sub.modal('/view/qmm/017/p/index.xhtml', { title: '項目の選択', width: 350, height: 480 }).onClosed(function () {
                                        var baseAmountListItem = nts.uk.ui.windows.getShared('baseAmountListItem');
                                        self.baseAmountListItem(baseAmountListItem);
                                    });
                                };
                                ScreenModel.prototype.closeAndReturnData = function () {
                                    var self = this;
                                    var easyFormulaDetail = {
                                        easyFormulaCode: self.paramDirtyData.easyFormulaCode,
                                        easyFormulaName: self.easyFormulaName(),
                                        easyFormulaTypeAtr: self.comboBoxFormulaType().selectedCode(),
                                        baseAmountDevision: self.comboBoxBaseAmount().selectedCode(),
                                        baseFixedAmount: self.baseAmountFixedValue(),
                                        referenceItemCodes: self.baseAmountListItem(),
                                        baseValueDevision: self.comboBoxBaseValue().selectedCode(),
                                        baseFixedValue: self.baseValueFixedValue(),
                                        premiumRate: self.premiumRate(),
                                        roundProcessingDevision: self.switchButtonRoundingFiguresD().selectedRuleCode(),
                                        totalRounding: self.switchButtonRoundingFiguresF().selectedRuleCode(),
                                        coefficientDivision: self.comboBoxCoefficient().selectedCode(),
                                        coefficientFixedValue: self.coefficientFixedValue(),
                                        adjustmentDevision: self.comboBoxAdjustmentAtr().selectedCode()
                                    };
                                    if (easyFormulaDetail.baseAmountDevision === '0') {
                                        easyFormulaDetail.referenceItemCodes = [];
                                    }
                                    else {
                                        easyFormulaDetail.baseFixedAmount = 0;
                                    }
                                    if (easyFormulaDetail.baseValueDevision !== '0') {
                                        easyFormulaDetail.baseFixedValue = 0;
                                    }
                                    if (easyFormulaDetail.coefficientDivision !== '0') {
                                        easyFormulaDetail.coefficientFixedValue = 0;
                                    }
                                    nts.uk.ui.windows.setShared('easyFormulaDetail', easyFormulaDetail);
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var ComboBox = (function () {
                                function ComboBox(itemList) {
                                    var self = this;
                                    self.itemList = ko.observableArray(itemList);
                                    self.itemName = ko.observable('');
                                    self.currentCode = ko.observable('0');
                                    self.selectedCode = ko.observable('0');
                                }
                                return ComboBox;
                            }());
                            viewmodel.ComboBox = ComboBox;
                            var SwitchButton = (function () {
                                function SwitchButton(itemList) {
                                    var self = this;
                                    self.roundingRules = ko.observableArray(itemList);
                                    self.selectedRuleCode = ko.observable('0');
                                }
                                return SwitchButton;
                            }());
                            viewmodel.SwitchButton = SwitchButton;
                            var ItemModel = (function () {
                                function ItemModel(code, name) {
                                    this.code = code;
                                    this.name = name;
                                }
                                return ItemModel;
                            }());
                        })(viewmodel = l.viewmodel || (l.viewmodel = {}));
                    })(l = qmm017.l || (qmm017.l = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map
