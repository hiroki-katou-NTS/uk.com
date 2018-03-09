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
        decimal60: number = 0;
        decimal10: number = 1;
        timeHM: number = 0;
        timeM: number = 1;

        constructor() {
            var self = this;

            //J2
            self.itemsEffectiveDigitLength([
                new model.ItemModel(self.atrUse, getText('CMF001_322')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_323'))
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
            self.setting = ko.observable(new model.InstantTimeDataFormatSetting(
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

        private checkActive1(): boolean {
            let self = this;
            if (self.setting().effectiveDigitLength() == self.atrUse) {
                return true;
            }
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
                return true;
            }
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

        private checkRequired1(): boolean {
            let self = this;
            if (self.setting().effectiveDigitLength() == self.atrUse) {
                return true;
            }
            return false;
        }

        private checkRequired2(): boolean {
            let self = this;
            if (self.setting().roundProc() == self.atrUse) {
                return true;
            }
            return false;
        }

        private checkRequired3(): boolean {
            let self = this;
            if (self.setting().fixedValue() == self.atrUse) {
                return true;
            }
            return false;
        }

        private saveSetting(): void {
            let self = this;
            if (self.inputMode) {
                setShared("CMF001FormatOutput", { lineNumber: self.lineNumber, formatSetting: ko.toJS(self.setting) });
            }
            nts.uk.ui.windows.close();
        }
        private cancelSetting(): void {
            nts.uk.ui.windows.close();
        }
    }
}