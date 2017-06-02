var qmm012;
(function (qmm012) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //E_004
                    this.Checked_NoDisplay = ko.observable(false);
                    //E_005
                    this.Checked_ErrorUpper = ko.observable(false);
                    //E_006
                    this.Checked_AlarmHigh = ko.observable(false);
                    //E_007
                    this.Checked_ErrorLower = ko.observable(false);
                    //E_008
                    this.Checked_AlarmLower = ko.observable(false);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentItemAttend = ko.observable(null);
                    this.CurrentAvePayAtr = ko.observable(0);
                    this.CurrentItemAtr = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.CurrentWorkDaysScopeAtr = ko.observable(1);
                    this.CurrentMemo = ko.observable("");
                    this.CurrentZeroDisplaySet = ko.observable(1);
                    this.CurrentItemDisplayAtr = ko.observable(1);
                    this.noDisplayNames_Enable = ko.observable(false);
                    var self = this;
                    //E_001 To 003
                    //E_001To003
                    self.Roundingrules_TimeNumberClassification = ko.observableArray([
                        { code: 0, name: '時間' },
                        { code: 1, name: '回数' }
                    ]);
                    self.Roundingrules_WorkingDaysPerYear = ko.observableArray([
                        { code: 1, name: '対象' },
                        { code: 0, name: '対象外' }
                    ]);
                    self.Roundingrules_ZeroDisplay = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.enable = ko.observable(false);
                    //E_001
                    self.Currencyeditor_ErrorUpper = {
                        value: self.CurrentErrRangeHigh,
                        constraint: 'ErrRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_ErrorUpper
                    };
                    //E_002
                    self.Currencyeditor_AlarmUpper = {
                        value: self.CurrentAlRangeHigh,
                        constraint: 'AlRangeHigh',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_AlarmHigh
                    };
                    //E_003
                    self.Currencyeditor_ErrorLower = {
                        value: self.CurrentErrRangeLow,
                        constraint: 'ErrRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_ErrorLower
                    };
                    //E_004
                    self.Currencyeditor_AlarmLower = {
                        value: self.CurrentAlRangeLow,
                        constraint: 'AlRangeLow',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        })),
                        enable: self.Checked_AlarmLower
                    };
                    self.CurrentItemAttend.subscribe(function (ItemAttend) {
                        self.CurrentAvePayAtr(ItemAttend ? ItemAttend.avePayAtr : 0);
                        self.CurrentItemAtr(ItemAttend ? ItemAttend.itemAtr : 0);
                        self.CurrentErrRangeLow(ItemAttend ? ItemAttend.errRangeLow : 0);
                        self.CurrentErrRangeHigh(ItemAttend ? ItemAttend.errRangeHigh : 0);
                        self.CurrentAlRangeLow(ItemAttend ? ItemAttend.alRangeLow : 0);
                        self.CurrentAlRangeHigh(ItemAttend ? ItemAttend.alRangeHigh : 0);
                        self.CurrentWorkDaysScopeAtr(ItemAttend ? ItemAttend.workDaysScopeAtr : 1);
                        self.CurrentMemo(ItemAttend ? ItemAttend.memo : "");
                        self.Checked_ErrorUpper(ItemAttend ? ItemAttend.errRangeHighAtr == 0 ? false : true : false);
                        self.Checked_AlarmHigh(ItemAttend ? ItemAttend.errRangeLowAtr == 0 ? false : true : false);
                        self.Checked_ErrorLower(ItemAttend ? ItemAttend.alRangeHighAtr == 0 ? false : true : false);
                        self.Checked_AlarmLower(ItemAttend ? ItemAttend.alRangeLowAtr == 0 ? false : true : false);
                    });
                    self.Checked_NoDisplay.subscribe(function (NewValue) {
                        self.CurrentItemDisplayAtr(NewValue ? 0 : 1);
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
                        self.loadItemAttend(itemMaster).done(function () {
                            dfd.resolve();
                        });
                    }
                    else {
                        self.clearContent();
                        dfd.resolve();
                    }
                    self.CurrentZeroDisplaySet(itemMaster ? itemMaster.zeroDisplaySet : 1);
                    self.Checked_NoDisplay(itemMaster ? itemMaster.itemDisplayAtr == 0 ? true : false : false);
                    return dfd.promise();
                };
                ScreenModel.prototype.clearContent = function () {
                    var self = this;
                    self.CurrentAvePayAtr(0);
                    self.CurrentItemAtr(0);
                    self.CurrentErrRangeLow(0);
                    self.CurrentErrRangeHigh(0);
                    self.CurrentAlRangeLow(0);
                    self.CurrentZeroDisplaySet(1);
                    self.CurrentAlRangeHigh(0);
                    self.CurrentWorkDaysScopeAtr(1);
                    self.CurrentMemo("");
                    self.Checked_ErrorUpper(false);
                    self.Checked_AlarmHigh(false);
                    self.Checked_ErrorLower(false);
                    self.Checked_AlarmLower(false);
                };
                ScreenModel.prototype.loadItemAttend = function (itemMaster) {
                    var self = this;
                    var dfd = $.Deferred();
                    e.service.findItemAttend(itemMaster.itemCode).done(function (ItemAttend) {
                        self.CurrentItemAttend(ItemAttend);
                        dfd.resolve(ItemAttend);
                    }).fail(function (res) {
                        // Alert message
                        nts.uk.ui.dialog.alert(res);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getCurrentItemAttend = function () {
                    //return Item Attend customer input on form
                    var self = this;
                    var itemAttend = new e.service.model.ItemAttend(self.CurrentAvePayAtr(), self.CurrentItemAtr(), self.Checked_AlarmHigh() ? 1 : 0, self.CurrentErrRangeLow(), self.Checked_ErrorUpper() ? 1 : 0, self.CurrentErrRangeHigh(), self.Checked_AlarmLower() ? 1 : 0, self.CurrentAlRangeLow(), self.Checked_ErrorLower() ? 1 : 0, self.CurrentAlRangeHigh(), self.CurrentWorkDaysScopeAtr(), self.CurrentMemo());
                    return itemAttend;
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm012.e || (qmm012.e = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=qmm012.e.vm.js.map