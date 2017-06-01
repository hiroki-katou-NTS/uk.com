var qmm012;
(function (qmm012) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.CurrentItemSalary = ko.observable(null);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentLimitMny = ko.observable(1);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentMemo = ko.observable("");
                    this.CurrentTaxAtr = ko.observable(0);
                    this.CurrentSocialInsAtr = ko.observable(1);
                    this.CurrentLaborInsAtr = ko.observable(1);
                    this.CurrentFixPayAtr = ko.observable(1);
                    this.CurrentApplyForAllEmpFlg = ko.observable(0);
                    this.CurrentApplyForMonthlyPayEmp = ko.observable(0);
                    this.CurrentApplyForDaymonthlyPayEmp = ko.observable(0);
                    this.CurrentApplyForDaylyPayEmp = ko.observable(0);
                    this.CurrentApplyForHourlyPayEmp = ko.observable(0);
                    this.CurrentAvePayAtr = ko.observable(0);
                    this.CurrentLimitMnyAtr = ko.observable(0);
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    this.CurrentZeroDisplaySet = ko.observable(1);
                    this.C_Sel_NoItemNames_Selected = ko.observable(false);
                    this.C_Sel_ErrorUpper_Selected = ko.observable(false);
                    this.C_Sel_AlarmHigh_Selected = ko.observable(false);
                    this.C_Sel_ErrorLower_Selected = ko.observable(false);
                    this.C_Sel_AlarmLower_Selected = ko.observable(false);
                    this.CurrentLimitCode = ko.observable("");
                    this.C_Lbl_LimitAmountItem_Value = ko.observable("");
                    this.currentCommuteNoTaxLimitDto = ko.observable(null);
                    this.currentItemPeriod = ko.observable(null);
                    this.C_Lbl_SettingClassification_Text = ko.observable("設定なし");
                    this.currentItemBDs = ko.observableArray([]);
                    this.C_Lbl_BreakdownClassification_Text = ko.observable("設定なし");
                    this.C_Btn_PeriodSetting_enable = ko.observable(true);
                    this.C_Btn_BreakdownSetting_enable = ko.observable(true);
                    this.alwaysFixed_Enable = ko.observable(true);
                    this.noDisplayNames_Enable = ko.observable(false);
                    var self = this;
                    self.ComboBoxItemList_C_Sel_Taxation = ko.observableArray([
                        new C_Sel_Taxation_ComboboxItemModel(0, '課税'),
                        new C_Sel_Taxation_ComboboxItemModel(1, '非課税(限度あり）'),
                        new C_Sel_Taxation_ComboboxItemModel(2, '非課税(限度なし）'),
                        new C_Sel_Taxation_ComboboxItemModel(3, '通勤費(手入力)'),
                        new C_Sel_Taxation_ComboboxItemModel(4, '通勤費(定期券利用)')
                    ]);
                    //end combobox data
                    //start Switch Data
                    //005 006 007 008 009 010
                    self.Roundingrules_ObjectNotCovered = ko.observableArray([
                        { code: 1, name: '対象' },
                        { code: 0, name: '対象外' }
                    ]);
                    self.Selectedrulecode_DistinguishBetween = ko.observable(1);
                    self.Selectedrulecode_AlwaysFixed = ko.observable(1);
                    self.Selectedrulecode_MonthFixed = ko.observable(1);
                    self.Selectedrulecode_DayToMonthFixed = ko.observable(1);
                    self.Selectedrulecode_DayFixed = ko.observable(1);
                    self.Selectedrulecode_DonorFixed = ko.observable(1);
                    self.Selectedrulecode_AverageWage = ko.observable(1);
                    //011
                    self.Roundingrules_ZeroDisplayIndicator = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.Selected_ZeroDisplayIndicator = ko.observable(1);
                    //017
                    self.Roundingrules_LimitAmount = ko.observableArray([
                        { code: 0, name: '固定額' },
                        { code: 1, name: '非課税限度額' },
                        { code: 2, name: '個人の交通機' },
                        { code: 3, name: '個人の交通用' },
                    ]);
                    self.Selected_LimitAmount = ko.observable(1);
                    //endSwitch Data
                    //start radiogroup data
                    //004
                    self.Radioitemlist_FixPayAtr = ko.observableArray([
                        new BoxModel(1, '全員一律で指定する'),
                        new BoxModel(0, '給与契約形態ごとに指定する')
                    ]);
                    //end radiogroup data
                    //C_001
                    self.currencyeditor_LimitAmount = {
                        value: self.CurrentLimitMny,
                        constraint: 'LimitMny',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //C_002
                    self.currencyeditor_ErrorUpper = {
                        value: self.CurrentErrRangeHigh,
                        constraint: 'ErrRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.C_Sel_ErrorUpper_Selected
                    };
                    //C_003
                    self.currencyeditor_AlarmUpper = {
                        value: self.CurrentAlRangeHigh,
                        constraint: 'AlRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.C_Sel_AlarmHigh_Selected
                    };
                    //C_004
                    self.currencyeditor_ErrorLower = {
                        value: self.CurrentErrRangeLow,
                        constraint: 'ErrRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.C_Sel_ErrorLower_Selected
                    };
                    //C_005
                    self.currencyeditor_AlarmLower = {
                        value: self.CurrentAlRangeLow,
                        constraint: 'AlRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.C_Sel_AlarmLower_Selected
                    };
                    //end currencyeditor
                    //end textarea
                    self.CurrentTaxAtr.subscribe(function (newValue) {
                        $('#C_Div_001').hide();
                        $('#C_Div_005').hide();
                        switch (newValue) {
                            case 1:
                            case 3:
                                $('#C_Div_004').show();
                                $('#C_Div_002').show();
                                self.CurrentLimitMnyAtr(0);
                                $('#C_Div_001').show();
                                break;
                            case 4:
                                $('#C_Div_005').show();
                                break;
                        }
                    });
                    self.C_Sel_NoItemNames_Selected.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue == true ? 0 : 1);
                    });
                    self.CurrentItemSalary.subscribe(function (NewValue) {
                        self.CurrentLimitMny(NewValue ? NewValue.limitMny : 1);
                        self.CurrentErrRangeHigh(NewValue ? NewValue.errRangeHigh : 0);
                        self.CurrentAlRangeHigh(NewValue ? NewValue.alRangeHigh : 0);
                        self.CurrentErrRangeLow(NewValue ? NewValue.errRangeLow : 0);
                        self.CurrentAlRangeLow(NewValue ? NewValue.alRangeLow : 0);
                        self.CurrentMemo(NewValue ? NewValue.memo : "");
                        self.CurrentTaxAtr(NewValue ? NewValue.taxAtr : 0);
                        self.CurrentSocialInsAtr(NewValue ? NewValue.socialInsAtr : 0);
                        self.CurrentLaborInsAtr(NewValue ? NewValue.laborInsAtr : 0);
                        self.CurrentFixPayAtr(NewValue ? NewValue.fixPayAtr : 1);
                        self.CurrentApplyForAllEmpFlg(NewValue ? NewValue.applyForAllEmpFlg : 0);
                        self.CurrentApplyForMonthlyPayEmp(NewValue ? NewValue.applyForMonthlyPayEmp : 0);
                        self.CurrentApplyForDaymonthlyPayEmp(NewValue ? NewValue.applyForDaymonthlyPayEmp : 0);
                        self.CurrentApplyForDaylyPayEmp(NewValue ? NewValue.applyForDaylyPayEmp : 0);
                        self.CurrentApplyForHourlyPayEmp(NewValue ? NewValue.applyForHourlyPayEmp : 0);
                        self.CurrentAvePayAtr(NewValue ? NewValue.avePayAtr : 0);
                        self.CurrentLimitMnyAtr(NewValue ? NewValue.limitMnyAtr : 0);
                        self.CurrentLimitCode(NewValue ? NewValue.limitMnyRefItemCode : "");
                        self.C_Sel_ErrorUpper_Selected(NewValue ? NewValue.errRangeHighAtr == 0 ? false : true : false);
                        self.C_Sel_AlarmHigh_Selected(NewValue ? NewValue.alRangeHighAtr == 0 ? false : true : false);
                        self.C_Sel_ErrorLower_Selected(NewValue ? NewValue.errRangeLowAtr == 0 ? false : true : false);
                        self.C_Sel_AlarmLower_Selected(NewValue ? NewValue.alRangeLowAtr == 0 ? false : true : false);
                    });
                    self.CurrentLimitMnyAtr.subscribe(function (newValue) {
                        $("#C_Inp_LimitAmount").ntsError('clear');
                        $('#C_Div_002').hide();
                        $('#C_Div_003').hide();
                        $('#C_Div_004').show();
                        switch (newValue) {
                            case 0:
                                $('#C_Div_002').show();
                                break;
                            case 1:
                                $('#C_Div_003').show();
                                self.CurrentLimitMny(1);
                                break;
                            case 2:
                            case 3:
                                $('#C_Div_004').hide();
                                self.CurrentLimitMny(1);
                                break;
                        }
                    });
                    self.CurrentFixPayAtr.subscribe(function (newValue) {
                        if (newValue == 0)
                            self.alwaysFixed_Enable(false);
                        else
                            self.alwaysFixed_Enable(true);
                    });
                    self.currentCommuteNoTaxLimitDto.subscribe(function (NewValue) {
                        self.C_Lbl_LimitAmountItem_Value(NewValue ? NewValue.commuNoTaxLimitCode + "  " + NewValue.commuNoTaxLimitName : "");
                    });
                    self.CurrentLimitCode.subscribe(function (NewValue) {
                        if (NewValue) {
                            //call service getCommuteNoTaxLimit
                            c.service.getCommuteNoTaxLimit(NewValue).done(function (CommuteNoTaxLimit) {
                                self.currentCommuteNoTaxLimitDto(CommuteNoTaxLimit);
                            }).fail(function (res) {
                                // Alert message
                                nts.uk.ui.dialog.alert(res);
                            });
                        }
                        else {
                            self.currentCommuteNoTaxLimitDto(undefined);
                        }
                    });
                    self.currentItemPeriod.subscribe(function (newValue) {
                        self.C_Lbl_SettingClassification_Text(newValue ? newValue.periodAtr == 1 ? '設定あり' : '設定なし' : '設定なし');
                    });
                    self.currentItemBDs.subscribe(function (newValue) {
                        self.C_Lbl_BreakdownClassification_Text(newValue.length ? '設定あり' : '設定なし');
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
                    self.CurrentZeroDisplaySet(itemMaster ? itemMaster.zeroDisplaySet : 1);
                    self.C_Sel_NoItemNames_Selected(itemMaster ? itemMaster.itemDisplayAtr == 0 ? true : false : false);
                    //load subitem of item master 
                    if (itemMaster.itemCode) {
                        self.loadItemSalary(itemMaster).done(function () {
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
                    return dfd.promise();
                };
                ScreenModel.prototype.clearContent = function () {
                    var self = this;
                    self.CurrentLimitMny(1);
                    self.CurrentErrRangeHigh(0);
                    self.CurrentAlRangeHigh(0);
                    self.CurrentErrRangeLow(0);
                    self.CurrentAlRangeLow(0);
                    self.CurrentMemo("");
                    self.CurrentTaxAtr(0);
                    self.CurrentSocialInsAtr(1);
                    self.CurrentLaborInsAtr(1);
                    self.CurrentFixPayAtr(1);
                    self.CurrentZeroDisplaySet(1);
                    self.CurrentApplyForAllEmpFlg(0);
                    self.CurrentApplyForMonthlyPayEmp(0);
                    self.CurrentApplyForDaymonthlyPayEmp(0);
                    self.CurrentApplyForDaylyPayEmp(0);
                    self.CurrentApplyForHourlyPayEmp(0);
                    self.CurrentAvePayAtr(0);
                    self.CurrentLimitMnyAtr(0);
                    self.CurrentLimitCode("");
                    self.C_Sel_ErrorUpper_Selected(false);
                    self.C_Sel_AlarmHigh_Selected(false);
                    self.C_Sel_ErrorLower_Selected(false);
                    self.C_Sel_AlarmLower_Selected(false);
                };
                ScreenModel.prototype.loadItemSalary = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.findItemSalary(itemMaster.itemCode).done(function (ItemSalary) {
                        self.CurrentItemSalary(ItemSalary);
                        dfd.resolve(ItemSalary);
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
                ScreenModel.prototype.GetCurrentItemSalary = function () {
                    //get ItemSalary customer input in form
                    var self = this;
                    var itemSalary = new c.service.model.ItemSalary(self.CurrentTaxAtr(), self.CurrentSocialInsAtr(), self.CurrentLaborInsAtr(), self.CurrentFixPayAtr(), self.CurrentApplyForAllEmpFlg(), self.CurrentApplyForMonthlyPayEmp(), self.CurrentApplyForDaymonthlyPayEmp(), self.CurrentApplyForDaylyPayEmp(), self.CurrentApplyForHourlyPayEmp(), self.CurrentAvePayAtr(), self.C_Sel_ErrorLower_Selected() ? 1 : 0, self.CurrentErrRangeLow(), self.C_Sel_ErrorUpper_Selected() ? 1 : 0, self.CurrentErrRangeHigh(), self.C_Sel_AlarmLower_Selected() ? 1 : 0, self.CurrentAlRangeLow(), self.C_Sel_AlarmHigh_Selected() ? 1 : 0, self.CurrentAlRangeHigh(), self.CurrentMemo(), self.CurrentLimitMnyAtr(), self.currentCommuteNoTaxLimitDto() ? self.currentCommuteNoTaxLimitDto().commuNoTaxLimitCode : '', self.CurrentLimitMny());
                    return itemSalary;
                };
                ScreenModel.prototype.openKDialog = function () {
                    var self = this;
                    //set selected code to session
                    nts.uk.ui.windows.setShared('commuNoTaxLimitCode', self.currentCommuteNoTaxLimitDto() ? self.currentCommuteNoTaxLimitDto().commuNoTaxLimitCode : '');
                    nts.uk.ui.windows.sub.modal('../k/index.xhtml', { height: 530, width: 350, dialogClass: "no-close", title: "非課税限度額の設定" }).onClosed(function () {
                        if (nts.uk.ui.windows.getShared('CommuteNoTaxLimitDto'))
                            self.currentCommuteNoTaxLimitDto(nts.uk.ui.windows.getShared('CommuteNoTaxLimitDto'));
                    });
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
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    this.id = id;
                    this.name = name;
                }
                return BoxModel;
            }());
            var C_Sel_Taxation_ComboboxItemModel = (function () {
                function C_Sel_Taxation_ComboboxItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return C_Sel_Taxation_ComboboxItemModel;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm012.c || (qmm012.c = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=qmm012.c.vm.js.map