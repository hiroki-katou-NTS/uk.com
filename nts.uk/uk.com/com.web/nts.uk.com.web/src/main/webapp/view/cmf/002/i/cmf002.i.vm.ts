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
    import notUse = cmf002.share.model.NOT_USE_ATR.NOT_USE;
    import use = cmf002.share.model.NOT_USE_ATR.USE;
    import dataformatSettingMode = cmf002.share.model.DATA_FORMAT_SETTING_SCREEN_MODE;

    export class ScreenModel {
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting> = ko.observable(
            new model.NumberDataFormatSetting({
                formatSelection: model.FORMAT_SELECTION.DECIMAL,
                decimalDigit: null,
                decimalPointClassification: model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT,
                decimalFraction: model.ROUNDING_METHOD.TRUNCATION,
                outputMinusAsZero: notUse,
                fixedValueOperation: notUse,
                fixedValueOperationSymbol: OPERATION_SYMBOL.PLUS,
                fixedCalculationValue: null,
                fixedLengthOutput: notUse,
                fixedLengthIntegerDigit: null,
                fixedLengthEditingMethod: model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE,
                nullValueReplace: notUse,
                valueOfNullValueReplace: null,
                fixedValue: notUse,
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
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        selectModeScreen: KnockoutObservable<number> = ko.observable(dataformatSettingMode.INIT);
        parameter: any;

        constructor() {
            this.parameter = getShared('CMF002_I_PARAMS');
            if (this.parameter) {
                this.selectModeScreen(this.parameter.selectModeScreen);
            }
        }
        initComponent() {
            var self = this;
        }
        validate() {
            var self = this;
            self.numberDataFormatSetting().formatSelection.subscribe(function(selectedValue: any) {
                if (selectedValue == 0) {
                    $('#I2_2_2').ntsError('clear');
                } else {
                    $('#I2_2_2').ntsError('check');
                }
            });
            self.numberDataFormatSetting().fixedValueOperation.subscribe(function(selectedValue: any) {
                if (selectedValue == 0) {
                    $('#I4_3').ntsError('clear');
                } else {
                    $('#I4_3').ntsError('check');
                }
            });
            self.numberDataFormatSetting().fixedLengthOutput.subscribe(function(selectedValue: any) {
                if (selectedValue == 0) {
                    $('#I5_2_2').ntsError('clear');
                } else {
                    $('#I5_2_2').ntsError('check');
                }
            });
            self.numberDataFormatSetting().nullValueReplace.subscribe(function(selectedValue: any) {
                if (selectedValue == 0) {
                    $('#I6_2').ntsError('clear');
                } else {
                    $('#I6_2').ntsError('check');
                }
            });
            self.numberDataFormatSetting().fixedValue.subscribe(function(selectedValue: any) {
                if (selectedValue == 0) {
                    $('#I7_2').ntsError('clear');
                    if (self.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL) {
                        $('#I2_2_2').ntsError('check');
                    }
                    if (self.numberDataFormatSetting().fixedValueOperation() == model.NOT_USE_ATR.USE) {
                        $('#I4_3').ntsError('check');
                    }
                    if (self.numberDataFormatSetting().fixedLengthOutput() == model.NOT_USE_ATR.USE) {
                        $('#I5_2_2').ntsError('check');
                    }
                    if (self.numberDataFormatSetting().nullValueReplace() == model.NOT_USE_ATR.USE) {
                        $('#I6_2').ntsError('check');
                    }
                } else {
                    $('#I7_2').ntsError('check');
                    $('#I2_2_2').ntsError('clear');
                    $('#I4_3').ntsError('clear')
                    $('#I5_2_2').ntsError('clear');
                    $('#I6_2').ntsError('clear');
                }
            });
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
            return (this.numberDataFormatSetting().fixedValue() == notUse);
        }
        enableFixedValue() {
            return true;
        }
        enableFormatSelection() {
            return (this.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL && this.enableGlobal());
        }
        enableDecimalFraction() {
            return (this.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.NO_DECIMAL && this.enableGlobal());
        }
        enableFixedValueOperation() {
            return (this.numberDataFormatSetting().fixedValueOperation() == use && this.enableGlobal());
        }
        enableFixedLengthOutput() {
            return (this.numberDataFormatSetting().fixedLengthOutput() == use && this.enableGlobal());
        }
        enableNullValueReplace() {
            return (this.numberDataFormatSetting().nullValueReplace() == use && this.enableGlobal());
        }
        enableFixedValueEditor() {
            return (this.numberDataFormatSetting().fixedValue() == use);
        }
        selectNumberDataFormatSetting() {
            // Case initial
            if (this.selectModeScreen() == dataformatSettingMode.INIT) {
                service.addNumberFormatSetting(ko.toJS(this.numberDataFormatSetting)).done(result => {
                    nts.uk.ui.windows.close();
                }).fail(function(error) {
                    alertError(error);
                });
                // Case individual
            } else {
                setShared('CMF002_I_PARAMS', { dateDataFormatSetting: ko.toJS(this.numberDataFormatSetting()) });
                nts.uk.ui.windows.close();
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