module nts.uk.com.view.cmf001.j.viewmodel {
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        inputMode: boolean = true;
        lineNumber: number = -1;

        setting: KnockoutObservable<model.InstantTimeDataFormatSetting> = ko.observable(null);

        itemsEffectiveDigitLength: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsDecimalSelect: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsHourMinSelect: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsDelimiterSet: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsRoundProc: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsRoundProcCls: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsFixedValue: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        atrUse: number = model.NOT_USE_ATR.USE;
        atrNotUse: number = model.NOT_USE_ATR.NOT_USE;
        decimal60: number = model.DECIMAL_SELECTION.HEXA_DECIMAL;
        decimal10: number = model.DECIMAL_SELECTION.DECIMAL;
        timeHM: number = model.HOURLY_SEGMENT.HOUR_MINUTE;
        timeM: number = model.HOURLY_SEGMENT.MINUTE;

        checkRequired1: KnockoutObservable<boolean> = ko.observable(true);
        checkRequired2: KnockoutObservable<boolean> = ko.observable(true);
        checkRequired3: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            var self = this;

            //J2
            self.itemsEffectiveDigitLength([
                new model.ItemModel(self.atrUse, getText('CMF001_333')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_334'))
            ]);

            //J3
            self.itemsDecimalSelect([
                new model.ItemModel(self.decimal60, getText('CMF001_342')),
                new model.ItemModel(self.decimal10, getText('CMF001_343'))
            ]);

            //J4
            self.itemsHourMinSelect([
                new model.ItemModel(self.timeHM, getText('CMF001_352')),
                new model.ItemModel(self.timeM, getText('CMF001_353'))
            ]);

            //J5
            self.itemsDelimiterSet(model.getDelimiterSetting());

            //J6
            self.itemsRoundProc([
                new model.ItemModel(self.atrUse, getText('CMF001_358')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_359'))
            ]);

            self.itemsRoundProcCls(model.getTimeRounding());

            //J7
            self.itemsFixedValue([
                new model.ItemModel(self.atrUse, getText('CMF001_363')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_364'))
            ]);

            let params = getShared("CMF001jParams");
            self.inputMode = params.inputMode;
            self.lineNumber = params.lineNumber;
            if (params.formatSetting) {
                params.formatSetting.roundProc = params.formatSetting.roundProc == null ? 0 : params.formatSetting.roundProc; 
                self.setting(new model.InstantTimeDataFormatSetting(
                    params.formatSetting.effectiveDigitLength,
                    params.formatSetting.startDigit,
                    params.formatSetting.endDigit,
                    params.formatSetting.decimalSelect,
                    params.formatSetting.hourMinSelect,
                    params.formatSetting.delimiterSet,
                    params.formatSetting.roundProc,
                    params.formatSetting.roundProcCls,
                    params.formatSetting.fixedValue,
                    params.formatSetting.valueOfFixedValue));
            }
            else {
                self.setting(new model.InstantTimeDataFormatSetting(0, null, null, 0, 0, 0, 0, 1, 0, null));
            }
            
            self.checkRequired1.subscribe(function(data: any) {
                if (!data) {
                    $('#J2_5').ntsError('clear');
                }
            });

            self.checkRequired2.subscribe(function(data: any) {
                if (!data) {
                    $('#J2_8').ntsError('clear');
                }
            });

            self.checkRequired3.subscribe(function(data: any) {
                if (!data) {
                    $('#J7_5').ntsError('clear');
                }
            });
        }

        private checkActive1(): boolean {
            let self = this;
            if (self.setting().effectiveDigitLength() == self.atrUse) {
                self.checkRequired1(true);
                self.checkRequired2(true);
                return true;
            }
            self.checkRequired1(false);
            self.checkRequired2(false);
            return false;
        }

        private checkActive2(): boolean {
            let self = this;
            if (self.setting().roundProc() == self.atrUse) {
                return true;
            }
            return false;
        }

        private checkActive3(): boolean {
            let self = this;
            if (self.setting().fixedValue() == self.atrUse) {
                self.checkRequired1(false);
                self.checkRequired2(false);
                self.checkRequired3(true);
                return true;
            }
            self.checkRequired1(true);
            self.checkRequired2(true);
            self.checkRequired3(false);
            return false;
        }

        private checkActive4(): boolean {
            let self = this;
            if (self.setting().decimalSelect() == self.decimal60) {
                return true;
            }
            return false;
        }

        private checkActive5(): boolean {
            let self = this;
            if (self.setting().decimalSelect() == self.decimal60) {
                return false;
            }
            return true;
        }

        private checkActive6(): boolean {
            let self = this;
            if (self.setting().fixedValue() == self.atrUse) {
                return false;
            }
            return true;
        }

        private saveSetting(): void {
            let self = this;
            if (!self.hasError()) {
                setShared("CMF001FormatOutput", { lineNumber: self.lineNumber, formatSetting: ko.toJS(self.setting) });
                nts.uk.ui.windows.close();
            }
        }

        private cancelSetting(): void {
            nts.uk.ui.windows.close();
        }

        private hasError(): boolean {
            let self = this;
            if (self.setting().fixedValue() == 0) {
                if (self.setting().effectiveDigitLength() == self.atrUse) {
                    $('#J2_5').ntsError('check');
                    $('#J2_8').ntsError('check');
                }
            } else {
                $('#J7_5').ntsError('check');
            }

            if (nts.uk.ui.errors.hasError()) {
                return true;
            }
            else {
                if (self.setting().fixedValue() == 0 && self.setting().effectiveDigitLength() == self.atrUse) {
                    self.setting().startDigit(Math.floor(self.setting().startDigit()));
                    self.setting().endDigit(Math.floor(self.setting().endDigit()));

                    let startDigit: number = +self.setting().startDigit();
                    let endDigit: number = +self.setting().endDigit();
                    if (startDigit > endDigit && self.inputMode) {
                        alertError({ messageId: "Msg_1119", messageParams: [getText('CMF001_335'), getText('CMF001_338')] });
                        return true;
                    }
                }
            }
            return false;
        }
    }
}