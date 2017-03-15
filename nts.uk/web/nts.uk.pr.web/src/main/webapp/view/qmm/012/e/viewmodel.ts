module qmm012.e.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean>;
        currencyeditor_E_001: any;
        currencyeditor_E_002: any;
        currencyeditor_E_003: any;
        currencyeditor_E_004: any;
        //E_004
        checked_E_004: KnockoutObservable<boolean> = ko.observable(false);
        //E_005
        checked_E_005: KnockoutObservable<boolean> = ko.observable(false);
        //E_006
        checked_E_006: KnockoutObservable<boolean> = ko.observable(false);
        //E_007
        checked_E_007: KnockoutObservable<boolean> = ko.observable(false);
        //E_008
        checked_E_008: KnockoutObservable<boolean> = ko.observable(false);
        //E_001To003
        roundingRules_E_001: KnockoutObservableArray<any>;
        roundingRules_E_002: KnockoutObservableArray<any>;
        roundingRules_E_003: KnockoutObservableArray<any>;
        selectedRuleCode_E_003: any;
        CurrentItemMaster: KnockoutObservable<qmm012.b.service.model.ItemMasterModel> = ko.observable(null);
        CurrentItemAttend: KnockoutObservable<service.model.ItemAttend> = ko.observable(null);
        CurrentAvePayAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentItemAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLowAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHighAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentErrRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLowAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeLow: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHighAtr: KnockoutObservable<number> = ko.observable(0);
        CurrentAlRangeHigh: KnockoutObservable<number> = ko.observable(0);
        CurrentWorkDaysScopeAtr: KnockoutObservable<number> = ko.observable(1);
        CurrentMemo: KnockoutObservable<string> = ko.observable("");
        CurrentZeroDisplaySet: KnockoutObservable<number> = ko.observable(0);


        constructor() {
            let self = this;
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


            self.CurrentItemMaster.subscribe(function(ItemMaster: qmm012.b.service.model.ItemMasterModel) {
                service.findItemAttend(ItemMaster.itemCode).done(function(ItemAttend: service.model.ItemAttend) {
                    self.CurrentItemAttend(ItemAttend);
                    self.CurrentZeroDisplaySet(ItemMaster ? ItemMaster.zeroDisplaySet : 1);
                    self.checked_E_004(ItemMaster ? ItemMaster.itemDisplayAtr == 0 ? true : false : false);
                }).fail(function(res) {
                    // Alert message
                    alert(res);
                });

            });
            self.CurrentItemAttend.subscribe(function(ItemAttend: service.model.ItemAttend) {
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
            self.checked_E_004.subscribe(function(NewValue) {
                self.CurrentErrRangeHighAtr(NewValue == true ? 0 : 1);
            });
            self.checked_E_005.subscribe(function(NewValue) {
                self.CurrentErrRangeHighAtr(NewValue == false ? 0 : 1);
            });
            self.checked_E_006.subscribe(function(NewValue) {
                self.CurrentErrRangeLowAtr(NewValue == false ? 0 : 1);
            });
            self.checked_E_007.subscribe(function(NewValue) {
                self.CurrentAlRangeHighAtr(NewValue == false ? 0 : 1);
            });
            self.checked_E_008.subscribe(function(NewValue) {
                self.CurrentAlRangeLowAtr(NewValue == false ? 0 : 1);
            });
        }

    }


}