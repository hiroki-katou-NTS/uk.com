module nts.uk.com.view.cmf002.m.viewmodel {
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
        decimalDigit: KnockoutObservable<number>;
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

        nextDaySelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNextDay());
        nextDayOutputMethod: KnockoutObservable<number> = ko.observable(0);
        preDaySelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getPreDay());
        previousDayOutputMethod: KnockoutObservable<number> = ko.observable(0);
        timeSelectedList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTimeSelected());
        timeSeletion: KnockoutObservable<number> = ko.observable(0);
        decimalSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDecimalSelect());
        decimalSelection: KnockoutObservable<number> = ko.observable(0);
        minuteFractionDigit: KnockoutObservable<number> = ko.observable(0);
        itemListRounding: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRounding());
        minuteFractionDigitProcessCla: KnockoutObservable<number> = ko.observable(0);
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        separatorSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSeparator());
        delimiterSetting: KnockoutObservable<number> = ko.observable(0);
        fixedValueOperationItem: KnockoutObservableArray<model.ItemModel>;
        formatSelectionItem: KnockoutObservableArray<model.ItemModel>;
        fixedValueOperationSymbolItem: KnockoutObservableArray<model.ItemModel>;
        fixedLengthOutputItem: KnockoutObservableArray<model.ItemModel>;
        fixedLengthEditingMethodItem: KnockoutObservableArray<model.ItemModel>;
        nullValueReplaceItem: KnockoutObservableArray<model.ItemModel>;
        fixedValueItem: KnockoutObservableArray<model.ItemModel>;

        //Defaut Mode Screen
        // 0 = Individual
        // 1 = initial
        selectModeScreen: KnockoutObservable<number> = ko.observable(1);

        enableSettingSubmit: KnockoutObservable<boolean> = ko.observable(true);
        enableRequired: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            var self = this;
            self.inputMode = true;
            self.initComponent();
        }
        initComponent() {
            var self = this;
            //self.numericDataFormatSetting = ko.observable(new model.NumericDataFormatSetting(0, null, null, null, 0, 0, null, null, 0, null, null, 0, null, 0, ""));
            self.fixedValueOperationItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);
            self.fixedValueItem = ko.observableArray([
                new model.ItemModel(1, getText('CMF002_149')),
                new model.ItemModel(0, getText('CMF002_150'))
            ]);
            self.fixedValueOperationSymbolItem = ko.observableArray([
                new model.ItemModel(0, '+'),
                new model.ItemModel(1, '-')
            ]);

            self.fixedLengthOutputItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);

            self.fixedLengthEditingMethodItem = ko.observableArray([
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('Enum_FixedLengthEditingMethod_ZERO_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('Enum_FixedLengthEditingMethod_ZERO_AFTER')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('Enum_FixedLengthEditingMethod_SPACE_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('Enum_FixedLengthEditingMethod_SPACE_AFTER'))
            ]);

            self.nullValueReplaceItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);

            self.fixedValueItem = ko.observableArray([
                new model.ItemModel(1, getText('CMF002_149')),
                new model.ItemModel(0, getText('CMF002_150'))
            ]);
        }

        sendData() {
            var self = this;
            self.enableRequired(true);
            if (self.minuteFractionDigit() == "") {
                $('#M3_1').ntsError('set', { messageId: "Msg_658" });
            }

            if (self.fixedLengthOutput() == 1) {
                self.enableRequired(true);
                if (self.fixedLongIntegerDigit() == "" || self.fixedLongIntegerDigit() < 1) {
                    $('#M9_2_2').ntsError('set', { messageId: "Msg_658" });
                }
            }

            let data = {
                nullValueSubs: self.nullValueSubs(),
                valueOfNullValueSubs: self.valueOfNullValueSubs(),
                outputMinusAsZero: self.outputMinusAsZero(),
                fixedValue: self.fixedValue(),
                valueOfFixedValue: self.valueOfFixedValue(),
                timeSeletion: self.timeSeletion(),
                fixedLengthOutput: self.fixedLengthOutput(),
                fixedLongIntegerDigit: self.fixedLongIntegerDigit(),
                fixedLengthEditingMothod: self.fixedLengthEditingMothod(),
                delimiterSetting: self.delimiterSetting(),
                previousDayOutputMethod: self.previousDayOutputMethod(),
                nextDayOutputMethod: self.nextDayOutputMethod(),
                minuteFractionDigit: self.minuteFractionDigit(),
                decimalSelection: self.decimalSelection(),
                minuteFractionDigitProcessCla: self.minuteFractionDigitProcessCla()
            };
            service.sendPerformSettingByInTime(data).done(result => {

            }).fail(function(error) {

            });
        }
        //※M1　～　※M6
        enableFormatSelectionCls() {
            var self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }

        //※M2　
        enableFixedValueOperationCls() {
            var self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueOperation() {
            var self = this;
            return (self.fixedValueOperation() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M3
        enableFixedLengthOutputCls() {
            var self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedLengthOutput() {
            var self = this;
            return (self.fixedLengthOutput() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M4
        enableNullValueReplaceCls() {
            var self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableNullValueReplace() {
            var self = this;
            return (self.nullValueSubs() == model.NOT_USE_ATR.USE && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M5
        enableSelectTimeCls() {
            var self = this;
            return (self.timeSeletion() == model.getTimeSelected()[0].code && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M6
        decimalSelectionCls() {
            var self = this;
            return (self.timeSeletion() == model.getTimeSelected()[0].code && self.decimalSelection() == model.getTimeSelected()[0].code && self.inputMode && self.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }


        enableFixedValueCls() {
            var self = this;
            return (self.inputMode);
        }
        enableFixedValue() {
            var self = this;
            return (self.fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            //Check Mode Screen 
            if (self.selectModeScreen() == 0) {
                self.enableSettingSubmit(false);
            }
            service.findPerformSettingByInTime().done(result => {
                let getData = result;
                if (getData != null) {
                    self.nullValueSubs(getData.nullValueSubs);
                    self.valueOfNullValueSubs(getData.valueOfNullValueSubs);
                    self.outputMinusAsZero(getData.outputMinusAsZero);
                    self.fixedValue(getData.fixedValue);
                    self.valueOfFixedValue(getData.valueOfFixedValue);
                    self.timeSeletion(getData.timeSeletion);
                    self.fixedLengthOutput(getData.fixedLengthOutput);
                    self.fixedLongIntegerDigit(getData.fixedLongIntegerDigit);
                    self.fixedLengthEditingMothod(getData.fixedLengthEditingMothod);
                    self.delimiterSetting(getData.delimiterSetting);
                    self.previousDayOutputMethod(getData.previousDayOutputMethod);
                    self.nextDayOutputMethod(getData.nextDayOutputMethod);
                    self.minuteFractionDigit(getData.minuteFractionDigit);
                    self.decimalSelection(getData.decimalSelection);
                    self.minuteFractionDigitProcessCla(getData.minuteFractionDigitProcessCla);
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