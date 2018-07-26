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
        notUse = cmf002.share.model.NOT_USE_ATR.NOT_USE;
        use = cmf002.share.model.NOT_USE_ATR.USE;
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting> = ko.observable(
            new model.NumberDataFormatSetting({
                formatSelection: model.FORMAT_SELECTION.DECIMAL,
                decimalDigit: "",
                decimalPointClassification: model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT,
                decimalFraction: model.ROUNDING_METHOD.TRUNCATION,
                outputMinusAsZero: this.notUse,
                fixedValueOperation: this.notUse,
                fixedValueOperationSymbol: OPERATION_SYMBOL.PLUS,
                fixedCalculationValue: null,
                fixedLengthOutput: this.notUse,
                fixedLengthIntegerDigit: "",
                fixedLengthEditingMethod: model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE,
                nullValueReplace: this.notUse,
                valueOfNullValueReplace: "",
                fixedValue: this.notUse,
                valueOfFixedValue: ""
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
        parameter: any;

        constructor() {
            let self = this;
            self.parameter = getShared('CMF002_C_PARAMS');
            if (self.parameter) {
                self.selectModeScreen(self.parameter.selectModeScreen);
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();

            if (self.selectModeScreen() == dataformatSettingMode.INDIVIDUAL && self.parameter.numberDataFormatSetting) {
                let data = self.parameter.numberDataFormatSetting;
                self.numberDataFormatSetting(new model.NumberDataFormatSetting(data));
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
            let enable = self.numberDataFormatSetting().fixedValue() == self.notUse;
            if (!enable) {
                this.numberDataFormatSetting().outputMinusAsZero(this.notUse);
                this.numberDataFormatSetting().formatSelection(model.FORMAT_SELECTION.DECIMAL);
                this.numberDataFormatSetting().fixedValueOperation(this.notUse);
                this.numberDataFormatSetting().fixedLengthOutput(this.notUse);
                this.numberDataFormatSetting().nullValueReplace(this.notUse);
            }
            return enable;
        }
        enableFormatSelection() {
            let self = this;
            let enable = self.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL && self.enableGlobal();
            if (!enable) {
                $('#I2_2_2').ntsError('clear');
                this.numberDataFormatSetting().decimalDigit(null);
                this.numberDataFormatSetting().decimalPointClassification(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT);
            }
            return enable;
        }
        enableDecimalFraction() {
            let self = this;
            let enable = self.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.NO_DECIMAL && self.enableGlobal();
            if (!enable) {
                this.numberDataFormatSetting().decimalFraction(model.ROUNDING_METHOD.TRUNCATION);
            }
            return enable;
        }
        enableFixedValueOperation() {
            let self = this;
            let enable = self.numberDataFormatSetting().fixedValueOperation() == self.use && self.enableGlobal();
            if (!enable) {
                $('#I4_3').ntsError('clear');
                this.numberDataFormatSetting().fixedCalculationValue(null);
                this.numberDataFormatSetting().fixedValueOperationSymbol(OPERATION_SYMBOL.PLUS);
            }
            return enable;
        }
        enableFixedLengthOutput() {
            let self = this;
            let enable = self.numberDataFormatSetting().fixedLengthOutput() == self.use && self.enableGlobal();
            if (!enable) {
                $('#I5_2_2').ntsError('clear');
                this.numberDataFormatSetting().fixedLengthIntegerDigit(null);
                this.numberDataFormatSetting().fixedLengthEditingMethod(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE);
            }
            return enable;
        }
        enableNullValueReplace() {
            let self = this;
            let enable = self.numberDataFormatSetting().nullValueReplace() == self.use && self.enableGlobal();
            if (!enable) {
                this.numberDataFormatSetting().valueOfNullValueReplace("");
            }
            return enable;
        }
        enableFixedValueEditor() {
            let self = this;
            let enable = self.numberDataFormatSetting().fixedValue() == self.use;
            if (!enable) {
                this.numberDataFormatSetting().valueOfFixedValue("");
            }
            return enable;
        }

        selectNumberDataFormatSetting() {
            let self = this;
            errors.clearAll();
            $('#I2_2_2').ntsError(self.enableFormatSelection() ? 'check' : '');
            $('#I4_3').ntsError(self.enableFixedValueOperation() ? 'check' : '');
            $('#I5_2_2').ntsError(self.enableFixedLengthOutput() ? 'check' : '');
            if (!errors.hasError()) {
                let outputMinusAsZero = self.numberDataFormatSetting().outputMinusAsZero();
                self.numberDataFormatSetting().outputMinusAsZero(outputMinusAsZero ? 1 : 0);
                // Case initial
                if (self.selectModeScreen() == dataformatSettingMode.INIT) {
                    service.addNumberFormatSetting(ko.toJS(self.numberDataFormatSetting)).done(result => {
                        nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        alertError(error);
                    });
                    // Case individual
                } else {
                    setShared('CMF002_I_PARAMS', { numberDataFormatSetting: ko.toJS(self.numberDataFormatSetting()) });
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
                new model.ItemModel(OPERATION_SYMBOL.PLUS, getText('CMF002_389')),
                // -
                new model.ItemModel(OPERATION_SYMBOL.MINUS, getText('CMF002_390'))
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

    export enum OPERATION_SYMBOL {
        // +
        PLUS = 0,
        // -
        MINUS = 1,
    }
}