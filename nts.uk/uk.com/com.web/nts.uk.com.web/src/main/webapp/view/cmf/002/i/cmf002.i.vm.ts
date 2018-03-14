module nts.uk.com.view.cmf002.i.viewmodel {
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
        numericDataFormatSetting: KnockoutObservable<model.NumericDataFormatSetting>;
        formatSelectionItem: KnockoutObservableArray<model.ItemModel>;
        fixedValueOperationItem: KnockoutObservableArray<model.ItemModel>;
        fixedLengthOutputItem: KnockoutObservableArray<model.ItemModel>;
        nullValueReplaceItem: KnockoutObservableArray<model.ItemModel>;
        fixedValueItem: KnockoutObservableArray<model.ItemModel>;
        decimalPointClassificationItem: KnockoutObservableArray<model.ItemModel>;
        decimalFractionItem: KnockoutObservableArray<model.ItemModel>;
        fixedValueOperationSymbolItem: KnockoutObservableArray<model.ItemModel>;
        fixedLengthEditingMethodItem: KnockoutObservableArray<model.ItemModel>;
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        inputMode: boolean;
        constructor() {
            var self = this;
            self.inputMode = true;
            self.initComponent();
            self.validate();
        }
        initComponent() {
            var self = this;
            self.numericDataFormatSetting = ko.observable(new model.NumericDataFormatSetting(0, null, null, null, 0, 0, null, null, 0, null, null, 0, null, 0, ""));
            self.formatSelectionItem = ko.observableArray([
                new model.ItemModel(model.FORMAT_SELECTION.DECIMAL, getText('CMF002_139')),
                new model.ItemModel(model.FORMAT_SELECTION.NO_DECIMAL, getText('CMF002_140'))
            ]);
            self.fixedValueOperationItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);
            self.fixedLengthOutputItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);
            self.nullValueReplaceItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);
            self.fixedValueItem = ko.observableArray([
                new model.ItemModel(1, getText('CMF002_149')),
                new model.ItemModel(0, getText('CMF002_150'))
            ]);
            self.decimalPointClassificationItem = ko.observableArray([
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.NO_OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_NO_OUTPUT_DECIMAL_POINT')),
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_OUTPUT_DECIMAL_POINT'))
            ]);
            self.decimalFractionItem = ko.observableArray([
                new model.ItemModel(model.ROUNDING_METHOD.TRUNCATION, getText('Enum_Rounding_Truncation')),
                new model.ItemModel(model.ROUNDING_METHOD.ROUND_UP, getText('Enum_Rounding_Round_Up')),
                new model.ItemModel(model.ROUNDING_METHOD.DOWN_4_UP_5, getText('Enum_Rounding_Down_4_Up_5'))
            ]);
            self.fixedValueOperationSymbolItem = ko.observableArray([
                new model.ItemModel(0, '+'),
                new model.ItemModel(1, '-')
            ]);
            self.fixedLengthEditingMethodItem = ko.observableArray([
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('Enum_FixedLengthEditingMethod_ZERO_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('Enum_FixedLengthEditingMethod_ZERO_AFTER')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('Enum_FixedLengthEditingMethod_SPACE_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('Enum_FixedLengthEditingMethod_SPACE_AFTER'))
            ]);
        }
        validate(){
            var self = this;
            self.numericDataFormatSetting().formatSelection.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#I2_2_2').ntsError('clear');
                }else{
                    $('#I2_2_2').ntsError('check');
                }
            });
            self.numericDataFormatSetting().fixedValueOperation.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#I4_3').ntsError('clear');
                }else{
                    $('#I4_3').ntsError('check');
                }
            });
            self.numericDataFormatSetting().fixedLengthOutput.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#I5_2_2').ntsError('clear');
                }else{
                    $('#I5_2_2').ntsError('check');
                }
            });
            self.numericDataFormatSetting().nullValueReplace.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#I6_2').ntsError('clear');
                }else{
                    $('#I6_2').ntsError('check');
                }
            });
            self.numericDataFormatSetting().fixedValue.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#I7_2').ntsError('clear');
                    if (self.numericDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL){
                        $('#I2_2_2').ntsError('check');
                    }
                    if (self.numericDataFormatSetting().fixedValueOperation() == model.NOT_USE_ATR.USE){
                        $('#I4_3').ntsError('check');
                    }
                    if (self.numericDataFormatSetting().fixedLengthOutput() == model.NOT_USE_ATR.USE){
                        $('#I5_2_2').ntsError('check');
                    }
                    if (self.numericDataFormatSetting().nullValueReplace() == model.NOT_USE_ATR.USE){
                        $('#I6_2').ntsError('check');
                    }
                }else{
                    $('#I7_2').ntsError('check');
                    $('#I2_2_2').ntsError('clear');
                    $('#I4_3').ntsError('clear')
                    $('#I5_2_2').ntsError('clear');
                    $('#I6_2').ntsError('clear');
                }
            });
        }
        start(): JQueryPromise<any> {
            //block.invisible();
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
//            service.getNumericDataFormatSetting().done(function(numFormat) {
//                if (numFormat) {
//                    let numericDataFormatSetting = new model.NumericDataFormatSetting(numFormat.formatSelection, 
//                    numFormat.decimalDigit, numFormat.decimalPointClassification, numFormat.decimalFraction, numFormat.outputMinusAsZero, 
//                    numFormat.fixedValueOperation, numFormat.fixedValueOperationSymbol, numFormat.fixedCalculationValue, numFormat.fixedLengthOutput, 
//                    numFormat.fixedLengthIntegerDigit, numFormat.fixedLengthEditingMethod, numFormat.nullValueReplace, numFormat.valueOfNullValueReplace, 
//                    numFormat.fixedValue, numFormat.valueOfFixedValue);
//                    self.numericDataFormatSetting(numericDataFormatSetting);
//                }
//                block.clear();
//                dfd.resolve();
//            }).fail(function(error) {
//                alertError(error);
//                block.clear();
//                dfd.reject();
//            });
            return dfd.promise();
        }
        enableFormatSelectionCls() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableOutputMinusAsZero() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueOperationCls() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedLengthOutputCls() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableNullValueReplaceCls() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueCls() {
            var self = this;
            return (self.inputMode);
        }
        enableFormatSelection() {
            var self = this;
            return (self.numericDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL && self.inputMode && self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        disableFormatSelection() {
            var self = this;
            return (self.numericDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.NO_DECIMAL && self.inputMode && self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableFixedValueOperation() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValueOperation() == model.NOT_USE_ATR.USE && self.inputMode && self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableFixedLengthOutput() {
            var self = this;
            return (self.numericDataFormatSetting().fixedLengthOutput() == model.NOT_USE_ATR.USE && self.inputMode && self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableNullValueReplace() {
            var self = this;
            return (self.numericDataFormatSetting().nullValueReplace() == model.NOT_USE_ATR.USE && self.inputMode && self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableFixedValue() {
            var self = this;
            return (self.numericDataFormatSetting().fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }
        saveNumericSetting() {
            var self = this;
            if (self.outputMinusAsZeroChecked()) {
                self.numericDataFormatSetting().outputMinusAsZero(1);
            } else {
                self.numericDataFormatSetting().outputMinusAsZero(0);
            }
            let params = "";
            setShared("CMF002iOutput", params);
        }
        cancelNumericSetting() {

        }
    }
}