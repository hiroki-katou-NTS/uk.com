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
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {
        notUse = cmf002.share.model.NOT_USE_ATR.NOT_USE;
        use = cmf002.share.model.NOT_USE_ATR.USE;
        numberDataFormatSetting: KnockoutObservable<model.NumberDataFormatSetting> = ko.observable(
            new model.NumberDataFormatSetting({
                formatSelection: model.FORMAT_SELECTION.DECIMAL,
                decimalDigit: 0,
                decimalPointClassification: model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT,
                decimalFraction: model.ROUNDING_METHOD.TRUNCATION,
                outputMinusAsZero: this.notUse,
                fixedValueOperation: this.notUse,
                fixedValueOperationSymbol: OPERATION_SYMBOL.PLUS,
                fixedCalculationValue: 0.00,
                fixedLengthOutput: this.notUse,
                fixedLengthIntegerDigit: 0,
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
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        selectModeScreen: KnockoutObservable<number> = ko.observable(dataformatSettingMode.INIT);
        parameter: any;

        constructor() {
            this.parameter = getShared('CMF002_C_PARAMS');
            if (this.parameter) {
                this.selectModeScreen(this.parameter.selectModeScreen);
            }
            if (this.numberDataFormatSetting().outputMinusAsZero() == this.notUse) {
                this.outputMinusAsZeroChecked(false);
            } else {
                this.outputMinusAsZeroChecked(true);
            }
        }

        start(): JQueryPromise<any> {
            block.invisible();
            let self = this;
            let dfd = $.Deferred();

            if (this.selectModeScreen() == dataformatSettingMode.INDIVIDUAL && self.parameter.numberDataFormatSetting) {
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
            return (this.numberDataFormatSetting().fixedValue() == this.notUse);
        }
        enableFixedValue() {
            return true;
        }
        enableFormatSelection() {
            return this.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.DECIMAL && this.enableGlobal();
        }
        enableDecimalFraction() {
            return (this.numberDataFormatSetting().formatSelection() == model.FORMAT_SELECTION.NO_DECIMAL && this.enableGlobal());
        }
        enableFixedValueOperation() {
            return this.numberDataFormatSetting().fixedValueOperation() == this.use && this.enableGlobal();
        }
        enableFixedLengthOutput() {
            return this.numberDataFormatSetting().fixedLengthOutput() == this.use && this.enableGlobal();
        }
        enableNullValueReplace() {
            return (this.numberDataFormatSetting().nullValueReplace() == this.use && this.enableGlobal());
        }
        enableFixedValueEditor() {
            return (this.numberDataFormatSetting().fixedValue() == this.use);
        }

        selectNumberDataFormatSetting() {
            if (!hasError()) {
                // Case initial
                if (this.selectModeScreen() == dataformatSettingMode.INIT) {
                    service.addNumberFormatSetting(ko.toJS(this.numberDataFormatSetting)).done(result => {
                        nts.uk.ui.windows.close();
                    }).fail(function(error) {
                        alertError(error);
                    });
                    // Case individual
                } else {
                    setShared('CMF002_I_PARAMS', { numberDataFormatSetting: ko.toJS(this.numberDataFormatSetting()) });
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