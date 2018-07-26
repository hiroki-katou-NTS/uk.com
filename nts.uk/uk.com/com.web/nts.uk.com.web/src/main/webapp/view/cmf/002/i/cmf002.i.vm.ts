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
    import dataformatSettingMode = cmf002.share.model.DATA_FORMAT_SETTING_SCREEN_MODE;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        PLUS = cmf002.share.model.SYMBOL_OPRERATION.PLUS;
        MINUS = cmf002.share.model.SYMBOL_OPRERATION.MINUS;
        notUse = cmf002.share.model.NOT_USE_ATR.NOT_USE;
        use = cmf002.share.model.NOT_USE_ATR.USE;
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting> = ko.observable(
            new model.NumberDataFormatSetting({
                formatSelection: model.FORMAT_SELECTION.DECIMAL,
                decimalDigit: null,
                decimalPointClassification: model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT,
                decimalFraction: model.ROUNDING_METHOD.TRUNCATION,
                outputMinusAsZero: this.notUse,
                fixedValueOperation: this.notUse,
                fixedValueOperationSymbol: this.PLUS,
                fixedCalculationValue: null,
                fixedLengthOutput: this.notUse,
                fixedLengthIntegerDigit: null,
                fixedLengthEditingMethod: model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE,
                nullValueReplace: this.notUse,
                valueOfNullValueReplace: null,
                fixedValue: this.notUse,
                valueOfFixedValue: null
            }));;
        formatSelectionItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getFormatSelectionItems());
        fixedValueOperationItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        fixedLengthOutputItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        nullValueReplaceItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        fixedValueItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNotUseAtr());
        decimalPointClassificationItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getDecimalPointClassificationItems());
        decimalFractionItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getDecimalFractionItem());
        fixedValueOperationSymbolItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getFixedValueOperationSymbolItem());
        fixedLengthEditingMethodItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(this.getFixedLengthEditingMethodItem());;
        selectModeScreen: KnockoutObservable<number> = ko.observable(dataformatSettingMode.INIT);
        formatSetting: any;

        constructor() {
            let self = this;
            let parameter = getShared('CMF002_C_PARAMS');
            if (parameter) {
                self.formatSetting = parameter.formatSetting;
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();

            if (self.selectModeScreen() == dataformatSettingMode.INDIVIDUAL && self.formatSetting) {
                self.numberDataFormatSetting(new model.NumberDataFormatSetting(self.formatSetting));
                dfd.resolve();
                block.clear();
                return dfd.promise();
            }

            service.getNumberFormatSetting().done(function(data: any) {
                if (data != null) {
                    self.numberDataFormatSetting(new model.NumberDataFormatSetting(data));
                }
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                dfd.reject();
            });

            block.clear();
            return dfd.promise();
        }

        enableGlobal() {
            let self = this;
            return self.numberDataFormatSetting().fixedValue() == self.notUse;
        }
        enableFormatSelection() {
            let self = this;
            let enable = self.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL && self.enableGlobal();
            if (!enable) {
                $('#I2_2_2').ntsError('clear');
            }
            return enable;
        }
        enableDecimalFraction() {
            let self = this;
            return self.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.NO_DECIMAL && self.enableGlobal();
        }
        enableFixedValueOperation() {
            let self = this;
            let enable = self.numberDataFormatSetting().fixedValueOperation() == self.use && self.enableGlobal();
            if (!enable) {
                $('#I4_3').ntsError('clear');
            }
            return enable;
        }
        enableFixedLengthOutput() {
            let self = this;
            let enable = self.numberDataFormatSetting().fixedLengthOutput() == self.use && self.enableGlobal();
            if (!enable) {
                $('#I5_2_2').ntsError('clear');
            }
            return enable;
        }
        enableNullValueReplace() {
            let self = this;
            return self.numberDataFormatSetting().nullValueReplace() == self.use && self.enableGlobal();
        }
        enableFixedValueEditor() {
            let self = this;
            return self.numberDataFormatSetting().fixedValue() == self.use;
        }

        selectNumberDataFormatSetting() {
            let self = this;
            errors.clearAll();
            $('#I2_2_2').ntsError(self.enableFormatSelection() ? 'check' : '');
            $('#I4_3').ntsError(self.enableFixedValueOperation() ? 'check' : '');
            $('#I5_2_2').ntsError(self.enableFixedLengthOutput() ? 'check' : '');

            let numberDataFormatSettingSubmit = self.numberDataFormatSetting();
            if (numberDataFormatSettingSubmit.fixedValue() == this.use) {
                numberDataFormatSettingSubmit.formatSelection(model.FORMAT_SELECTION.DECIMAL);
                numberDataFormatSettingSubmit.decimalDigit(null);
                numberDataFormatSettingSubmit.decimalPointClassification(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT);
                numberDataFormatSettingSubmit.decimalFraction(model.ROUNDING_METHOD.TRUNCATION);
                numberDataFormatSettingSubmit.outputMinusAsZero(this.notUse);
                numberDataFormatSettingSubmit.fixedValueOperation(this.notUse);
                numberDataFormatSettingSubmit.fixedValueOperationSymbol(this.PLUS);
                numberDataFormatSettingSubmit.fixedCalculationValue(null);
                numberDataFormatSettingSubmit.fixedLengthOutput(this.notUse);
                numberDataFormatSettingSubmit.fixedLengthIntegerDigit(null);
                numberDataFormatSettingSubmit.fixedLengthEditingMethod(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE);
                numberDataFormatSettingSubmit.nullValueReplace(this.notUse);
                numberDataFormatSettingSubmit.valueOfNullValueReplace(null);
            } else {
                numberDataFormatSettingSubmit.valueOfFixedValue(null);
            }

            if (numberDataFormatSettingSubmit.formatSelection() != model.FORMAT_SELECTION.DECIMAL) {
                numberDataFormatSettingSubmit.decimalDigit(null);
                numberDataFormatSettingSubmit.decimalPointClassification(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT);
            } else {
                numberDataFormatSettingSubmit.decimalFraction(model.ROUNDING_METHOD.TRUNCATION);
            }

            if (numberDataFormatSettingSubmit.fixedValueOperation() == this.notUse) {
                numberDataFormatSettingSubmit.fixedValueOperationSymbol(this.PLUS);
                numberDataFormatSettingSubmit.fixedCalculationValue(null);
            }

            if (numberDataFormatSettingSubmit.fixedLengthOutput() == this.notUse) {
                numberDataFormatSettingSubmit.fixedLengthIntegerDigit(null);
                numberDataFormatSettingSubmit.fixedLengthEditingMethod(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE);
            }

            if (numberDataFormatSettingSubmit.nullValueReplace() == this.notUse) {
                numberDataFormatSettingSubmit.valueOfNullValueReplace(null);
            }

            if (!errors.hasError()) {
                let outputMinusAsZero = self.numberDataFormatSetting().outputMinusAsZero();
                self.numberDataFormatSetting().outputMinusAsZero(outputMinusAsZero ? 1 : 0);
                // Case initial
                if (self.selectModeScreen() == dataformatSettingMode.INIT) {
                    service.addNumberFormatSetting(ko.toJS(numberDataFormatSettingSubmit)).done(result => {
                        nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        alertError(error);
                    });
                    // Case individual
                } else {
                    setShared('CMF002_C_PARAMS', { numberDataFormatSetting: ko.toJS(numberDataFormatSettingSubmit) });
                    nts.uk.ui.windows.close();
                }
            }
        }
        cancelNumberDataFormatSetting() {
            nts.uk.ui.windows.close();
        }

        getFormatSelectionItems(): Array<model.ItemModel> {
            return [
                //小数あり
                new model.ItemModel(model.FORMAT_SELECTION.DECIMAL, getText('CMF002_139')),
                //小数なし
                new model.ItemModel(model.FORMAT_SELECTION.NO_DECIMAL, getText('CMF002_140'))
            ];
        }

        getDecimalPointClassificationItems(): Array<model.ItemModel> {
            return [
                //小数点を出力する
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT, getText('CMF002_382')),
                //小数点を出力しない
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.NO_OUTPUT_DECIMAL_POINT, getText('CMF002_383'))
            ];
        }

        getDecimalFractionItem(): Array<model.ItemModel> {
            return [
                //切り捨て
                new model.ItemModel(model.ROUNDING_METHOD.TRUNCATION, getText('CMF002_384')),
                //切り上げ
                new model.ItemModel(model.ROUNDING_METHOD.ROUND_UP, getText('CMF002_385')),
                //四捨五入
                new model.ItemModel(model.ROUNDING_METHOD.DOWN_4_UP_5, getText('CMF002_386'))
            ];
        }

        getFixedValueOperationSymbolItem(): Array<model.ItemModel> {
            return [
                // +
                new model.ItemModel(this.PLUS, getText('CMF002_389')),
                // -
                new model.ItemModel(this.MINUS, getText('CMF002_390'))
            ];
        }

        getFixedLengthEditingMethodItem(): Array<model.ItemModel> {
            return [
                //前ゼロ
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('CMF002_391')),
                //後ゼロ
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('CMF002_392')),
                //前スペース
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('CMF002_393')),
                //後スペース
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('CMF002_394'))
            ];
        }

        //test xóa khi hoàn thành
        gotoScreenI_initial() {
            let self = this;
            nts.uk.ui.windows.sub.modal("/view/cmf/002/i/index.xhtml");
        };
    }
}