module nts.uk.com.view.cmf002.l.viewmodel {
    import block = nts.uk.ui.block;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {
        initTimeDataFormatSetting: any = {
            nullValueSubs: 0,
            outputMinusAsZero: 0,
            fixedValue: 0,
            valueOfFixedValue: "",
            fixedLengthOutput: 0,
            fixedLongIntegerDigit: 0,
            fixedLengthEditingMothod: 0,
            delimiterSetting: 0,
            selectHourMinute: 0,
            minuteFractionDigit: 0,
            decimalSelection: 0,
            fixedValueOperationSymbol: 0,
            fixedValueOperation: 0,
            fixedCalculationValue: 0,
            valueOfNullValueSubs: "",
            minuteFractionDigitProcessCls: 0
        }
        timeDataFormatSetting: KnockoutObservable<model.TimeDataFormatSetting> = ko.observable(new model.TimeDataFormatSetting(this.initTimeDataFormatSetting));

        //initComponent
        inputMode: boolean;
        selectedValue: KnockoutObservable<any>;
        //L2_1
        timeSelectedList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTimeSelected());
        //L4_1
        decimalSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDecimalSelect());
        //L3_3
        itemListRounding: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRounding());
        //L5_1
        outputMinusAsZeroChecked: KnockoutObservable<boolean> = ko.observable(false);
        //L6_1
        separatorSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSeparator());
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

        enableRequired: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.inputMode = true;
        }
        
        sendData() {
            let self = this;
            self.enableRequired(true);
            
            if (self.timeDataFormatSetting().minuteFractionDigit() == "") {
                $('#L3_1').ntsError('set', { messageId: "Msg_658" });
            }

            if (self.timeDataFormatSetting().fixedValueOperation() == 1) {
                self.enableRequired(true);
                if (self.timeDataFormatSetting().fixedCalculationValue() == "") {
                    $('#L7_3').ntsError('set', { messageId: "Msg_658" });
                }
            }

            if (self.timeDataFormatSetting().fixedLengthOutput() == 1) {
                self.enableRequired(true);
                if (self.timeDataFormatSetting().fixedLongIntegerDigit() == "" || self.timeDataFormatSetting().fixedLongIntegerDigit() < 1) {
                    $('#L8_2_2').ntsError('set', { messageId: "Msg_658" });
                }
            }

            if (!hasError()) {
                let data = ko.toJS(self.timeDataFormatSetting);
                data.outputMinusAsZero = data.outputMinusAsZero ? 1 : 0;
                if (self.selectModeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INIT) {
                    service.sendPerformSettingByTime(data).done(result => {
                        close();
                    });
                } else {
                    setShared('CMF002_L_PARAMS', data);
                    close();
                }
            }
        }
        
        //※L1　～　※L6
        enableFormatSelectionCls() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }

        //※L2　
        enableFixedValueOperationCls() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueOperation() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValueOperation() == model.NOT_USE_ATR.USE && self.inputMode && self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L3
        enableFixedLengthOutputCls() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedLengthOutput() {
            let self = this;
            return (self.timeDataFormatSetting().fixedLengthOutput() == model.NOT_USE_ATR.USE && self.inputMode && self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L4
        enableNullValueReplaceCls() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableNullValueReplace() {
            let self = this;
            return (self.timeDataFormatSetting().nullValueSubs() == model.NOT_USE_ATR.USE && self.inputMode && self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L5
        enableSelectTimeCls() {
            let self = this;
            return (self.timeDataFormatSetting().selectHourMinute() == model.getTimeSelected()[0].code && self.inputMode && self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※L6
        decimalSelectionCls() {
            let self = this;
            return (self.timeDataFormatSetting().selectHourMinute() == model.getTimeSelected()[0].code && self.timeDataFormatSetting().decimalSelection() == model.getTimeSelected()[0].code && self.inputMode && self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }

        enableFixedValueCls() {
            let self = this;
            return (self.inputMode);
        }
        enableFixedValue() {
            let self = this;
            return (self.timeDataFormatSetting().fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            let self = this;
            let dfd = $.Deferred();
            //Check Mode Screen 
            let objectShare: any = getShared("CMF002_L_PARAMS");
            if (self.selectModeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL && objectShare) {
                // get data shared
                self.timeDataFormatSetting(new model.timeDataFormatSetting(objectShare));
                dfd.resolve();
                return dfd.promise();
            }
            self.startFindData();
            dfd.resolve();
            return dfd.promise();
        }
        
        startFindData() {
            let self = this;
            service.findPerformSettingByTime().done(result => {
                if (result) {
                    self.timeDataFormatSetting(new model.TimeDataFormatSetting(result));
                    return;
                }
                self.timeDataFormatSetting(new model.TimeDataFormatSetting(self.initTimeDataFormatSetting));
            });
        }

        cancelCharacterSetting() {
           close();
        }
    }
}