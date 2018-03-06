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

        itemsDecimalSelection: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsHourMinuteSelection: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsDelimiterSetting: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsRoundingProcessing: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

        itemsRoundProcessingClassification: KnockoutObservableArray<model.ItemModel> = ko.observableArray([]);

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
            self.itemsDecimalSelection([
                new model.ItemModel(self.decimal60, getText('CMF001_342')),
                new model.ItemModel(self.decimal10, getText('CMF001_343'))
            ]);

            //J4
            self.itemsHourMinuteSelection([
                new model.ItemModel(self.timeHM, getText('CMF001_352')),
                new model.ItemModel(self.timeM, getText('CMF001_353'))
            ]);

            //J5
            self.itemsDelimiterSetting([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, '1234567890123456789012345')
            ]);

            //J6
            self.itemsRoundingProcessing([
                new model.ItemModel(self.atrUse, getText('CMF001_358')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_359'))
            ]);

            self.itemsRoundProcessingClassification([
                new model.ItemModel(0, getText('CMF001_355')),
                new model.ItemModel(1, getText('1234567890123456789012345'))
            ]);

            //J7
            self.itemsFixedValue([
                new model.ItemModel(self.atrUse, getText('CMF001_363')),
                new model.ItemModel(self.atrNotUse, getText('CMF001_364'))
            ]);

            let params = getShared("CMF001jParams");
            if (!nts.uk.util.isNullOrUndefined(params)) {
                let inputMode = params.inputMode;
                let lineNumber = params.lineNumber;
                let dateSet = params.formatSetting;
                self.inputMode = inputMode;
                self.lineNumber = lineNumber;
                if (!nts.uk.util.isNullOrUndefined(dateSet)) {
                    self.setting = ko.observable(new model.InstantTimeDataFormatSetting(
                        dateSet.effectiveDigitLength,
                        dateSet.startDigit,
                        dateSet.endDigit,
                        dateSet.decimalSelection,
                        dateSet.hourMinuteSelection,
                        dateSet.delimiterSetting,
                        dateSet.roundingProcessing,
                        dateSet.roundProcessingClassification,
                        dateSet.fixedValue,
                        dateSet.valueOfFixed));
                }
            }
        }

        checkActive1() {
            let self = this;
            if (self.setting().effectiveDigitLength() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive2() {
            let self = this;
            if (self.setting().roundingProcessing() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive3() {
            let self = this;
            if (self.setting().fixedValue() == self.atrUse) {
                return true;
            }
            return false;
        }

        checkActive4() {
            let self = this;
            if (self.setting().decimalSelection() == self.decimal60) {
                return true;
            }
            return false;
        }

        checkActive5() {
            let self = this;
            if (self.setting().decimalSelection() == self.decimal60) {
                return false;
            }
            return true;
        }

        checkActive6() {
            let self = this;
            if (self.setting().fixedValue() == self.atrUse) {
                return false;
            }
            return true;
        }

        saveNumericSetting() {
            let self = this;
            if (self.inputMode) {
                setShared("CMF001jOutput", { lineNumber: self.lineNumber, formatSetting: ko.toJS(self.setting) });
            }
            nts.uk.ui.windows.close();
        }
        cancelNumericSetting() {
            let self = this;
            if (self.inputMode) {
                setShared("CMF001jCancel", true);
            }
            nts.uk.ui.windows.close();
        }
    }
}