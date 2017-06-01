var qmm012;
(function (qmm012) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //Checkbox
                    //D_003
                    this.Checked_NoDisplayNames = ko.observable(true);
                    //D_003
                    this.Checked_ErrorUpper = ko.observable(false);
                    //D_003
                    this.Checked_AlarmHigh = ko.observable(false);
                    //D_003
                    this.Checked_ErrorLower = ko.observable(false);
                    //D_003
                    this.Checked_AlarmLower = ko.observable(false);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentItemDeduct = ko.observable(null);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentDeductAtr = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentMemo = ko.observable("");
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    this.CurrentZeroDisplaySet = ko.observable(1);
                    this.currentItemPeriod = ko.observable(null);
                    this.D_Lbl_SettingClassification_Text = ko.observable('設定なし');
                    this.currentItemBDs = ko.observableArray([]);
                    this.D_Lbl_BreakdownItem_Text = ko.observable("設定なし");
                    this.D_Btn_PeriodSetting_enable = ko.observable(true);
                    this.D_Btn_BreakdownSetting_enable = ko.observable(true);
                    this.noDisplayNames_Enable = ko.observable(false);
                    var self = this;
                    self.isEditable = ko.observable(true);
                    self.isEnable = ko.observable(true);
                    self.enable = ko.observable(true);
                    self.D_Sel_DeductionItem = ko.observableArray([
                        new ComboboxItemModel(0, '任意控除項目'),
                        new ComboboxItemModel(1, '社会保険項目'),
                        new ComboboxItemModel(2, '所得税項目'),
                        new ComboboxItemModel(3, '住民税項目')
                    ]);
                    //end combobox data
                    //D_002
                    self.roundingRules_ZeroDisplayIndicator = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    //currencyeditor
                    //001
                    self.currencyeditor_ErrorUpper = {
                        value: self.CurrentErrRangeHigh,
                        constraint: 'ErrRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_ErrorUpper
                    };
                    //002
                    self.currencyeditor_AlarmUpper = {
                        value: self.CurrentAlRangeHigh,
                        constraint: 'AlRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_AlarmHigh
                    };
                    //003
                    self.currencyeditor_ErrorLower = {
                        value: self.CurrentErrRangeLow,
                        constraint: 'ErrRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_ErrorLower
                    };
                    //004
                    self.currencyeditor_AlarmLower = {
                        value: self.CurrentAlRangeLow,
                        constraint: 'AlRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_AlarmLower
                    };
                    self.CurrentItemDeduct.subscribe(function (ItemDeduct) {
                        self.CurrentAlRangeHigh(ItemDeduct ? ItemDeduct.alRangeHigh : 0);
                        self.CurrentAlRangeLow(ItemDeduct ? ItemDeduct.alRangeLow : 0);
                        self.CurrentDeductAtr(ItemDeduct ? ItemDeduct.deductAtr : 0);
                        self.CurrentErrRangeHigh(ItemDeduct ? ItemDeduct.errRangeHigh : 0);
                        self.CurrentErrRangeLow(ItemDeduct ? ItemDeduct.errRangeLow : 0);
                        self.CurrentMemo(ItemDeduct ? ItemDeduct.memo : "");
                        self.Checked_ErrorUpper(ItemDeduct ? ItemDeduct.errRangeHighAtr == 0 ? false : true : false);
                        self.Checked_AlarmHigh(ItemDeduct ? ItemDeduct.alRangeHighAtr == 0 ? false : true : false);
                        self.Checked_ErrorLower(ItemDeduct ? ItemDeduct.errRangeLowAtr == 0 ? false : true : false);
                        self.Checked_AlarmLower(ItemDeduct ? ItemDeduct.alRangeLowAtr == 0 ? false : true : false);
                    });
                    self.Checked_NoDisplayNames.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
                    });
                    self.currentItemPeriod.subscribe(function (newValue) {
                        self.D_Lbl_SettingClassification_Text(newValue ? newValue.periodAtr == 1 ? '設定あり' : '設定なし' : '設定なし');
                    });
                    self.currentItemBDs.subscribe(function (newValue) {
                        self.D_Lbl_BreakdownItem_Text(newValue.length ? '設定あり' : '設定なし');
                    });
                    self.CurrentZeroDisplaySet.subscribe(function (newValue) {
                        if (newValue == 0) {
                            self.noDisplayNames_Enable(true);
                        }
                        else {
                            self.noDisplayNames_Enable(false);
                        }
                    });
                }
                ScreenModel.prototype.loadData = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    self.CurrentItemMaster(itemMaster);
                    if (itemMaster.itemCode) {
                        self.loadItemDeduct(itemMaster).done(function () {
                            self.loadItemPeriod(itemMaster).done(function () {
                                self.loadItemBDs(itemMaster).done(function () {
                                    dfd.resolve();
                                });
                            });
                        });
                    }
                    else {
                        self.clearContent();
                        dfd.resolve();
                    }
                    self.Checked_NoDisplayNames(itemMaster ? itemMaster.itemDisplayAtr == 0 ? true : false : false);
                    self.CurrentZeroDisplaySet(itemMaster ? itemMaster.zeroDisplaySet : 1);
                    return dfd.promise();
                };
                ScreenModel.prototype.clearContent = function () {
                    var self = this;
                    self.CurrentAlRangeHigh(0);
                    self.CurrentAlRangeLow(0);
                    self.CurrentDeductAtr(0);
                    self.CurrentZeroDisplaySet(1);
                    self.CurrentErrRangeHigh(0);
                    self.CurrentErrRangeLow(0);
                    self.CurrentMemo("");
                    self.Checked_ErrorUpper(false);
                    self.Checked_AlarmHigh(false);
                    self.Checked_ErrorLower(false);
                    self.Checked_AlarmLower(false);
                    self.D_Lbl_SettingClassification_Text('設定なし');
                    self.D_Lbl_BreakdownItem_Text('設定なし');
                };
                ScreenModel.prototype.loadItemDeduct = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    d.service.findItemDeduct(itemMaster.itemCode).done(function (ItemDeduct) {
                        self.CurrentItemDeduct(ItemDeduct);
                        dfd.resolve(ItemDeduct);
                    }).fail(function (res) {
                        // Alert message
                        nts.uk.ui.dialog.alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadItemPeriod = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm012.h.service.findItemPeriod(itemMaster).done(function (ItemPeriod) {
                        self.currentItemPeriod(ItemPeriod);
                        dfd.resolve(ItemPeriod);
                    }).fail(function (res) {
                        // Alert message
                        nts.uk.ui.dialog.alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.loadItemBDs = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm012.i.service.findAllItemBD(itemMaster).done(function (ItemBDs) {
                        self.currentItemBDs(ItemBDs);
                        dfd.resolve(ItemBDs);
                    }).fail(function (res) {
                        // Alert message
                        nts.uk.ui.dialog.alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openHDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
                    nts.uk.ui.windows.setShared('itemPeriod', self.currentItemPeriod());
                    nts.uk.ui.windows.sub.modal('../h/index.xhtml', { height: 570, width: 735, dialogClass: "no-close", title: "項目名の登録" }).onClosed(function () {
                        self.currentItemPeriod(nts.uk.ui.windows.getShared('itemPeriod'));
                    });
                };
                ScreenModel.prototype.openIDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('itemMaster', self.CurrentItemMaster());
                    nts.uk.ui.windows.setShared('itemBDs', self.currentItemBDs());
                    nts.uk.ui.windows.sub.modal('../i/index.xhtml', { height: 620, width: 1060, dialogClass: "no-close", title: "項目名の登録" }).onClosed(function () {
                        self.currentItemBDs(nts.uk.ui.windows.getShared('itemBDs'));
                    });
                };
                ScreenModel.prototype.GetCurrentItemDeduct = function () {
                    var self = this;
                    var ItemDeduct = new d.service.model.ItemDeduct(self.CurrentDeductAtr(), self.Checked_ErrorLower() ? 1 : 0, self.CurrentErrRangeLow(), self.Checked_ErrorUpper() ? 1 : 0, self.CurrentErrRangeHigh(), self.Checked_AlarmLower() ? 1 : 0, self.CurrentAlRangeLow(), self.Checked_AlarmHigh() ? 1 : 0, self.CurrentAlRangeHigh(), self.CurrentMemo());
                    return ItemDeduct;
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ComboboxItemModel = (function () {
                function ComboboxItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ComboboxItemModel;
            }());
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = qmm012.d || (qmm012.d = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=qmm012.d.vm.js.map