module nts.uk.com.view.cmf002.j.viewmodel {

    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf002.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import hasError = nts.uk.ui.errors.hasError;
    import error = nts.uk.ui.errors;

    export class ScreenModel {
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting> = ko.observable(new model.CharacterDataFormatSetting({
            effectDigitLength: 0,
            startDigit: null,
            endDigit: null,
            cdEditting: 0,
            cdEditDigit: null,
            cdEdittingMethod: null,
            spaceEditting: null,
            cdConvertCd: "",
            nullValueReplace: 0,
            valueOfNullValueReplace: "",
            fixedValue: 0,
            valueOfFixedValue: ""

        }));
        effectDigitLengthItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.FORMAT_SELECTION.DECIMAL, getText('CMF002_165')),
            new model.ItemModel(model.FORMAT_SELECTION.NO_DECIMAL, getText('CMF002_166'))
        ]);
        codeEditingItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ]);
        nullValueReplaceItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ]);
        fixedValueItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
        ]);
        codeEditingMethodItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getFixedLengthEditingMethod());
        spaceEditingItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.NO_OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_NO_OUTPUT_DECIMAL_POINT')),
            new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_OUTPUT_DECIMAL_POINT'))
        ]);

        modeScreen: KnockoutObservable<number> = ko.observable(0);
        isEnable: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            var self = this;

        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let params = getShared('CMF002_J_PARAMS');
            self.modeScreen(params.screenMode);
            if (self.modeScreen() == model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL && params.formatSetting) {
                // get data shared
                self.characterDataFormatSetting(new model.CharacterDataFormatSetting(params.formatSetting));
            } else {
                service.getCharacterDataFormatSetting().done(result => {
                    if (result) {
                        self.characterDataFormatSetting(new model.CharacterDataFormatSetting(result));
                    }
                });
            }
            dfd.resolve();
            return dfd.promise();
        }

        saveCharacterSetting() {
            error.clearAll();
            let self = this;
            if (self.characterDataFormatSetting().startDigit() == "" && (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE)) {
                $("#J2_2_1").ntsError('check');
            }
            if (self.characterDataFormatSetting().endDigit() == "" && (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE)) {
                $("#J2_2_3").ntsError('check');
            }
            if (self.characterDataFormatSetting().cdEditDigit() == "" && (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE)) {
                $("#J3_2_1").ntsError('check');
            }
            let command = ko.toJS(self.characterDataFormatSetting);
            if (self.characterDataFormatSetting().effectDigitLength() != model.NOT_USE_ATR.USE) {
                $('#J2_2_1').ntsError('clear');
                $('#J2_2_3').ntsError('clear');
                command.startDigit = "";
                command.endDigit = "";
            }
            if (self.characterDataFormatSetting().cdEditting() != model.NOT_USE_ATR.USE) {
                $('#J3_2_1').ntsError('clear');
                command.cdEditDigit = "";
            }
            if (!hasError()) {

                if (self.characterDataFormatSetting().startDigit() < self.characterDataFormatSetting().endDigit()) {
                    $("#J2_2_1").ntsError('set', {messageId:"Msg_830"});
                    $('#J2_2_3').ntsError('set', {messageId:"Msg_830"});
                } else {
                    if (self.modeScreen() != model.DATA_FORMAT_SETTING_SCREEN_MODE.INDIVIDUAL) {
                        // get data shared
                        service.setCharacterDataFormatSetting(command).done(function() {
                        });
                    }
                    setShared('CMF002_C_PARAMS', { formatSetting: command });
                    nts.uk.ui.windows.close();
                }
            }
        }
        //
        enableEffectDigitLength() {
            var self = this;
            return (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE);
        }
        enableEffectDigitRequired() {
            var self = this;
            if (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE) {
                return true;
            } else {
                $('#J2_2_1').ntsError('clear');
                $('#J2_2_3').ntsError('clear');
                return false;
            }
        }
        enableCodeEditing() {
            var self = this;
            if (self.characterDataFormatSetting().cdEditting() == model.NOT_USE_ATR.USE) {
                return true;
            } else {
                $('#J3_2_1').ntsError('clear');
                return false;
            }
        }
        enableNullValueReplace() {
            var self = this;
            return (self.characterDataFormatSetting().nullValueReplace() == model.NOT_USE_ATR.USE);
        }
        enableFixedValue() {
            var self = this;
            return (self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.USE);
        }
        open002_V2() {
            setShared('CMF002JV2_Params', {
                mode: self.characterDataFormatSetting().codeConvertCode()
            });
            nts.uk.ui.windows.sub.modal("/view/cmf/002/v2/index.xhtml");
        }

        cancelCharacterSetting() {
            nts.uk.ui.windows.close();
        }
    }
}




