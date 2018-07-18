module nts.uk.com.view.cmf002.m.viewmodel {
    import block = nts.uk.ui.block;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {
        inTimeDataFormatSetting: KnockoutObservable<model.InTimeDataFormatSetting> = new model.InTimeDataFormatSetting({
            nullValueSubs: ko.observable(0),
            outputMinusAsZeroChecked: ko.observable(false),
            fixedValue: ko.observable(0),
            valueOfFixedValue: ko.observable(""),
            timeSeletion: ko.observable(0),
            fixedLengthOutput: ko.observable(0),
            fixedLongIntegerDigit: ko.observable(0),
            fixedLengthEditingMothod: ko.observable(0),
            delimiterSetting: ko.observable(0),
            previousDayOutputMethod: ko.observable(0),
            nextDayOutputMethod: ko.observable(0),
            minuteFractionDigit: ko.observable(0),
            decimalSelection: ko.observable(0),
            minuteFractionDigitProcessCls: ko.observable(0)
        });

        //initComponent
//        formatSelection: KnockoutObservable<number>;
//        decimalDigit: KnockoutObservable<number>;
//        decimalFraction: KnockoutObservable<number>;
//        outputMinusAsZeroChecked: KnockoutObservable<boolean>;
//        outputMinusAsZero: KnockoutObservable<number> = ko.observable(0);
//        fixedValueOperation: KnockoutObservable<number> = ko.observable(0);
//        fixedValueOperationSymbol: KnockoutObservable<number> = ko.observable(0);
//        fixedCalculationValue: KnockoutObservable<number> = ko.observable(0);
//        nullValueSubs: KnockoutObservable<number> = ko.observable(0);
//        valueOfNullValueSubs: KnockoutObservable<number> = ko.observable(0);
        inputMode: boolean;
        selectedValue: KnockoutObservable<any>;
        nextDaySelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNextDay());
        preDaySelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getPreDay());
        timeSelectedList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTimeSelected());
        decimalSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getDecimalSelect());
        itemListRounding: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRounding());
        separatorSelectList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSeparator());
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
        selectModeScreen: KnockoutObservable<number> = ko.observable(0);

        enableRequired: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.inputMode = true;
            self.initComponent();
        }
        initComponent() {
            let self = this;
            //self.numericDataFormatSetting = ko.observable(new model.NumericDataFormatSetting(0, null, null, null, 0, 0, null, null, 0, null, null, 0, null, 0, ""));
            self.fixedValueOperationItem = ko.observableArray(model.getNotUseAtr());
            self.fixedValueItem = ko.observableArray(model.getNotUseAtr());
            self.fixedValueOperationSymbolItem = ko.observableArray([
                new model.ItemModel(0, '+'),
                new model.ItemModel(1, '-')
            ]);

            self.fixedLengthOutputItem = ko.observableArray(model.getNotUseAtr());

            self.fixedLengthEditingMethodItem = ko.observableArray(model.getFixedLengthEditingMethod());

            self.nullValueReplaceItem = ko.observableArray(model.getNotUseAtr());

            self.fixedValueItem = ko.observableArray(model.getNotUseAtr());
        }

        sendData() {
            let self = this;
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

            if (!hasError()) {
                let data = ko.toJS(self.inTimeDataFormatSetting);
                if (self.selectModeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INIT) {
                    service.sendPerformSettingByInTime(data).done(result => {
                        close();
                    });
                } else {
                    setShared('CMF002_M_PARAMS', data);
                    close();
                }
            }
        }
        //※M1　～　※M6
        enableFormatSelectionCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }

        //※M2　
        enableFixedValueOperationCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedValueOperation() {
            let self = this;
            return (self.fixedValueOperation() == model.NOT_USE_ATR.USE && self.inputMode && self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M3
        enableFixedLengthOutputCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableFixedLengthOutput() {
            let self = this;
            return (self.fixedLengthOutput() == model.NOT_USE_ATR.USE && self.inputMode && self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M4
        enableNullValueReplaceCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE && self.inputMode);
        }
        enableNullValueReplace() {
            let self = this;
            return (self.inTimeDataFormatSetting.nullValueSubs() == model.NOT_USE_ATR.USE && self.inputMode && self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M5
        enableSelectTimeCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.timeSeletion() == model.getTimeSelected()[0].code && self.inputMode && self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        //※M6
        decimalSelectionCls() {
            let self = this;
            return (self.inTimeDataFormatSetting.timeSeletion() == model.getTimeSelected()[0].code && self.decimalSelection() == model.getTimeSelected()[0].code && self.inputMode && self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }


        enableFixedValueCls() {
            let self = this;
            return (self.inputMode);
        }
        enableFixedValue() {
            let self = this;
            return (self.inTimeDataFormatSetting.fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }

        start(): JQueryPromise<any> {
            //block.invisible();
            let self = this;
            let dfd = $.Deferred();
            //Check Mode Screen 
            let objectShare: any = getShared("CMF002_M_PARAMS");
            if (self.selectModeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL && objectShare) {
                // get data shared
                self.inTimeDataFormatSetting(new model.InTimeDataFormatSetting(objectShare));
                dfd.resolve();
                return dfd.promise();
            }

            self.startFindData();

            dfd.resolve();
            return dfd.promise();
        }

        startFindData() {
            let self = this;
            service.findPerformSettingByInTime().done(result => {
                if (result) {
                    self.inTimeDataFormatSetting(new model.InTimeDataFormatSetting(result));
                }
            });
        }

        cancelCharacterSetting() {
            close();
        }

    }
}