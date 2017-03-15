var qmm012;
(function (qmm012) {
    var e;
    (function (e) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //E_004
                    this.checked_E_004 = ko.observable(false);
                    //E_005
                    this.checked_E_005 = ko.observable(false);
                    //E_006
                    this.checked_E_006 = ko.observable(false);
                    //E_007
                    this.checked_E_007 = ko.observable(false);
                    //E_008
                    this.checked_E_008 = ko.observable(false);
                    this.CurrentItemMaster = ko.observable(null);
                    this.CurrentItemAttend = ko.observable(null);
                    this.CurrentAvePayAtr = ko.observable(0);
                    this.CurrentItemAtr = ko.observable(0);
                    this.CurrentErrRangeLowAtr = ko.observable(0);
                    this.CurrentErrRangeLow = ko.observable(0);
                    this.CurrentErrRangeHighAtr = ko.observable(0);
                    this.CurrentErrRangeHigh = ko.observable(0);
                    this.CurrentAlRangeLowAtr = ko.observable(0);
                    this.CurrentAlRangeLow = ko.observable(0);
                    this.CurrentAlRangeHighAtr = ko.observable(0);
                    this.CurrentAlRangeHigh = ko.observable(0);
                    this.CurrentWorkDaysScopeAtr = ko.observable(1);
                    this.CurrentMemo = ko.observable("");
                    this.CurrentZeroDisplaySet = ko.observable(0);
                    var self = this;
                    //E_001 To 003
                    //E_001To003
                    self.roundingRules_E_001 = ko.observableArray([
                        { code: 0, name: '時間' },
                        { code: 1, name: '回数' }
                    ]);
                    self.roundingRules_E_002 = ko.observableArray([
                        { code: 0, name: '対象' },
                        { code: 1, name: '対象外' }
                    ]);
                    self.roundingRules_E_003 = ko.observableArray([
                        { code: 1, name: 'ゼロを表示する' },
                        { code: 0, name: 'ゼロを表示しない' }
                    ]);
                    self.enable = ko.observable(false);
                    //E_001
                    self.currencyeditor_E_001 = {
                        value: self.CurrentErrRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            decimallength: 2,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //E_002
                    self.currencyeditor_E_002 = {
                        value: self.CurrentAlRangeHigh,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            decimallength: 2,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //E_003
                    self.currencyeditor_E_003 = {
                        value: self.CurrentErrRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            decimallength: 2,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    //E_004
                    self.currencyeditor_E_004 = {
                        value: self.CurrentAlRangeLow,
                        constraint: '',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.CurrencyEditorOption({
                            grouplength: 3,
                            decimallength: 2,
                            currencyformat: "JPY",
                            currencyposition: 'right'
                        }))
                    };
                    self.CurrentItemMaster.subscribe(function (ItemMaster) {
                        e.service.findItemAttend(ItemMaster.itemCode).done(function (ItemAttend) {
                            self.CurrentItemAttend(ItemAttend);
                            self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
                            self.checked_E_004(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
                        }).fail(function (res) {
                            // Alert message
                            alert(res);
                        });
                    });
                    self.CurrentItemAttend.subscribe(function (ItemAttend) {
                        self.CurrentAvePayAtr(ItemAttend ? ItemAttend.avePayAtr : 0);
                        self.CurrentItemAtr(ItemAttend ? ItemAttend.itemAtr : 0);
                        self.CurrentErrRangeLow(ItemAttend ? ItemAttend.errRangeLow : 0);
                        self.CurrentErrRangeHigh(ItemAttend ? ItemAttend.errRangeHigh : 0);
                        self.CurrentAlRangeLow(ItemAttend ? ItemAttend.alRangeLow : 0);
                        self.CurrentAlRangeHigh(ItemAttend ? ItemAttend.alRangeHigh : 0);
                        self.CurrentWorkDaysScopeAtr(ItemAttend ? ItemAttend.workDaysScopeAtr : 0);
                        self.CurrentMemo(ItemAttend ? ItemAttend.memo : "");
                        self.checked_E_005(ItemAttend ? ItemAttend.errRangeHighAtr == 0 ? false : true : false);
                        self.checked_E_006(ItemAttend ? ItemAttend.errRangeLowAtr == 0 ? false : true : false);
                        self.checked_E_007(ItemAttend ? ItemAttend.alRangeHighAtr == 0 ? false : true : false);
                        self.checked_E_008(ItemAttend ? ItemAttend.alRangeLowAtr == 0 ? false : true : false);
                    });
                    self.checked_E_004.subscribe(function (NewValue) {
                        self.CurrentErrRangeHighAtr(NewValue == true ? 0 : 1);
                    });
                    self.checked_E_005.subscribe(function (NewValue) {
                        self.CurrentErrRangeHighAtr(NewValue == false ? 0 : 1);
                    });
                    self.checked_E_006.subscribe(function (NewValue) {
                        self.CurrentErrRangeLowAtr(NewValue == false ? 0 : 1);
                    });
                    self.checked_E_007.subscribe(function (NewValue) {
                        self.CurrentAlRangeHighAtr(NewValue == false ? 0 : 1);
                    });
                    self.checked_E_008.subscribe(function (NewValue) {
                        self.CurrentAlRangeLowAtr(NewValue == false ? 0 : 1);
                    });
                }
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
    })(e = qmm012.e || (qmm012.e = {}));
})(qmm012 || (qmm012 = {}));
