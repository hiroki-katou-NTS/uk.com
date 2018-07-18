module nts.uk.com.view.cmf002.l.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        //initComponent
        formatSelection: KnockoutObservable<number>;
        decimalPointClassification: KnockoutObservable<number>;
        decimalFraction: KnockoutObservable<number>;
        outputMinusAsZeroChecked: KnockoutObservable<boolean>;
        outputMinusAsZero: KnockoutObservable<number> = ko.observable(0);
        fixedValueOperation: KnockoutObservable<number> = ko.observable(0);
        fixedValueOperationSymbol: KnockoutObservable<number> = ko.observable(0);
        fixedCalculationValue: KnockoutObservable<number> = ko.observable(0);
        fixedLengthOutput: KnockoutObservable<number> = ko.observable(0);
        fixedLongIntegerDigit: KnockoutObservable<number> = ko.observable(0);
        fixedLengthEditingMothod: KnockoutObservable<number> = ko.observable(0);
        nullValueSubs: KnockoutObservable<number> = ko.observable(0);
        valueOfNullValueSubs: KnockoutObservable<number> = ko.observable(0);
        fixedValue: KnockoutObservable<number> = ko.observable(0);
        valueOfFixedValue: KnockoutObservable<string> = ko.observable("");
        inputMode: boolean;
        selectedValue: KnockoutObservable<any>;


        //L2_1
        timeSelectedList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTimeSelected());
        selectHourMinute: KnockoutObservable<number> = ko.observable(0);
        //L4_1
        decimalSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDecimalSelect());
        decimalSelection: KnockoutObservable<number> = ko.observable(0);
        //L3_1
        minuteFractionDigit: KnockoutObservable<number> = ko.observable(0);
        //L3_3
        itemListRounding: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRounding());
        minuteFractionDigitProcessCla: KnockoutObservable<number> = ko.observable(0);

        //L5_1
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        //L6_1
        separatorSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSeparator());
        delimiterSetting: KnockoutObservable<number> = ko.observable(0);

        //L7_1
        fixedValueOperationItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        formatSelectionItem: KnockoutObservableArray<model.ItemModel>;
        //L7_2
        fixedValueOperationSymbolItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());

        //L8_1
        fixedLengthOutputItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        //L8_3_1
        fixedLengthEditingMethodItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getFixedLengthEditingMethod());

        //L9_1
        nullValueReplaceItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());

        //L10_1
        fixedValueItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());

        //Defaut Mode Screen
        // 0 = Individual
        // 1 = initial
        selectModeScreen: KnockoutObservable<number> = ko.observable(1);

        enableSettingSubmit: KnockoutObservable<boolean> = ko.observable(true);
        enableRequired: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.inputMode = true;
        }

        sendData() {
            let self = this;
            if (self.minuteFractionDigit() == "") {
                $('#L3_1').ntsError('set', { messageId: "Msg_658" });
            }

            if (self.fixedValueOperation() == 1) {
                self.enableRequired(true);
                if (self.fixedCalculationValue() == "") {
                    $('#L7_3').ntsError('set', { messageId: "Msg_658" });
                }
            }

            if (self.fixedLengthOutput() == 1) {
                self.enableRequired(true);
                if (self.fixedLongIntegerDigit() == "" || self.fixedLongIntegerDigit() < 1) {
                    $('#L8_2_2').ntsError('set', { messageId: "Msg_658" });
                }
            }


            let data = {
                selectHourMinute: self.selectHourMinute(),
                minuteFractionDigit: self.minuteFractionDigit(),
                minuteFractionDigitProcessCla: self.minuteFractionDigitProcessCla(),
                decimalSelection: self.decimalSelection(),
                outputMinusAsZero: self.outputMinusAsZero(),
                delimiterSetting: self.delimiterSetting(),
                fixedValueOperation: self.fixedValueOperation(),
                fixedValueOperationSymbol: self.fixedValueOperationSymbol(),
                fixedCalculationValue: self.fixedCalculationValue(),
                fixedLengthOutput: self.fixedLengthOutput(),
                fixedLongIntegerDigit: self.fixedLongIntegerDigit(),
                fixedLengthEditingMothod: self.fixedLengthEditingMothod(),
                nullValueSubs: self.nullValueSubs(),
                valueOfNullValueSubs: self.valueOfNullValueSubs(),
                fixedValue: self.fixedValue(),
                valueOfFixedValue: self.valueOfFixedValue()
            };
            console.log(data);
            service.sendPerformSettingByTime(data).done(result => {

            }).fail(function(error) {

            });

        }
        //※L1　～　※L6
        enableFormatSelectionCls() {
            let self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }

        //※L2　
        enableFixedValueOperationCls() {
            let self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueOperation() {
            let self = this;
            return (self.fixedValueOperation() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L3
        enableFixedLengthOutputCls() {
            let self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedLengthOutput() {
            let self = this;
            return (self.fixedLengthOutput() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L4
        enableNullValueReplaceCls() {
            let self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableNullValueReplace() {
            let self = this;
            return (self.nullValueSubs() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L5
        enableSelectTimeCls() {
            let self = this;
            return (self.selectHourMinute() == model.getTimeSelected()[0].code && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L6
        decimalSelectionCls() {
            let self = this;
            return (self.selectHourMinute() == model.getTimeSelected()[0].code && self.decimalSelection() == model.getTimeSelected()[0].code && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }

        enableFixedValueCls() {
            let self = this;
            return (self.inputMode);
        }
        enableFixedValue() {
            let self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            let self = this;
            let dfd = $.Deferred();
            //Check Mode Screen 
            if (self.selectModeScreen() == 0) {
                self.enableSettingSubmit(false);
            }
            service.findPerformSettingByTime().done(result => {
                let getData = result;
                if (getData != null) {
                    self.selectHourMinute(getData.selectHourMinute);
                    self.minuteFractionDigit(getData.minuteFractionDigit);
                    self.minuteFractionDigitProcessCla(getData.minuteFractionDigitProcessCla);
                    self.decimalSelection(getData.decimalSelection);
                    self.outputMinusAsZero(getData.outputMinusAsZero);
                    self.delimiterSetting(getData.delimiterSetting);
                    self.fixedValueOperation(getData.fixedValueOperation);
                    self.fixedValueOperationSymbol(getData.fixedValueOperationSymbol);
                    self.fixedCalculationValue(getData.fixedCalculationValue);
                    self.fixedLengthOutput(getData.fixedLengthOutput);
                    self.fixedLongIntegerDigit(getData.fixedLongIntegerDigit);
                    self.fixedLengthEditingMothod(getData.fixedLengthEditingMothod);
                    self.nullValueSubs(getData.nullValueSubs);
                    self.valueOfNullValueSubs(getData.valueOfNullValueSubs);
                    self.fixedValue(getData.fixedValue);
                    self.valueOfFixedValue(getData.valueOfFixedValue);
                    self.outputMinusAsZero() == 1 ? self.outputMinusAsZeroChecked(true) : self.outputMinusAsZeroChecked(false);
                }
            }).fail(function(error) {

            });
            dfd.resolve();
            return dfd.promise();
        }

        cancelCharacterSetting() {
            nts.uk.ui.windows.close();
        }
    }
}