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

    export class ScreenModel {
        characterDataFormatSetting: KnockoutObservable<CharacterDataFormatSetting> = ko.observable(new CharacterDataFormatSetting({
            effectDigitLength: null,
            startDigit: null,
            endDigit: null,
            cdEditting: null,
            cdEditDigit: null,
            cdEdittingMethod: null,
            spaceEditting: null,
            cdConvertCd: "",
            nullValueReplace: null,
            valueOfNullValueReplace: "",
            fixedValue: null,
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
            let parrams = getShared('CMF002H_Params');
            self.modeScreen(parrams.mode);
        }
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            if (self.modeScreen() == 0) {
                service.getCharacterDataFormatSetting().done(result => {
                    if (result) {
                        self.characterDataFormatSetting(new CharacterDataFormatSetting(result));
                    }
                });
            }
             if (self.modeScreen() == 1) {
                 let parrams = getShared('CMF002JC_Params');
                        self.characterDataFormatSetting(parrams.command);
            }
            dfd.resolve();
            return dfd.promise();
        }
        saveCharacterSetting() {
            let self = this;
            if (self.characterDataFormatSetting().startDigit() == "" || self.characterDataFormatSetting().endDigit() == "") {
                alertError({ messageId: 'Msg_658' });
            }
            if (self.characterDataFormatSetting().startDigit() < self.characterDataFormatSetting().endDigit()) {
                alertError({ messageId: 'Msg_830' });
            }
            if (self.characterDataFormatSetting().cdEditDigit() == null) {
                alertError({ messageId: 'Msg_658' });
            }
            let command = ko.toJS(self.characterDataFormatSetting);
            setShared('CMF002JC_Params', command);
            service.setCharacterDataFormatSetting(command).done(function() {
                nts.uk.ui.windows.close();
            });
        }
        //
        enableEffectDigitLength() {
            var self = this;
            return (self.characterDataFormatSetting().effectDigitLength() == model.NOT_USE_ATR.USE);
        }
        enableCodeEditing() {
            var self = this;
            return (self.characterDataFormatSetting().cdEditting() == model.NOT_USE_ATR.USE);
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
    export interface ICharacterDataFormatSetting {
        effectDigitLength: number;
        startDigit: number;
        endDigit: number;
        cdEditting: number;
        cdEditDigit: number;
        cdEdittingMethod: number;
        spaceEditting: number;
        cdConvertCd: string;
        nullValueReplace: number;
        valueOfNullValueReplace: string;
        fixedValue: number;
        valueOfFixedValue: string;
    }

    export class CharacterDataFormatSetting {
        effectDigitLength: KnockoutObservable<number>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        cdEditting: KnockoutObservable<number>;
        cdEditDigit: KnockoutObservable<number>;
        cdEdittingMethod: KnockoutObservable<number>;
        spaceEditting: KnockoutObservable<number>;
        cdConvertCd: KnockoutObservable<string>;
        nullValueReplace: KnockoutObservable<number>;
        valueOfNullValueReplace: KnockoutObservable<string>;
        fixedValue: KnockoutObservable<number>;
        valueOfFixedValue: KnockoutObservable<string>;
        constructor(params: ICharacterDataFormatSetting) {
            this.effectDigitLength = ko.observable(params.effectDigitLength);
            this.startDigit = ko.observable(params.startDigit);
            this.endDigit = ko.observable(params.endDigit);
            this.cdEditting = ko.observable(params.cdEditting);
            this.cdEditDigit = ko.observable(params.cdEditDigit);
            this.cdEdittingMethod = ko.observable(params.cdEdittingMethod);
            this.spaceEditting = ko.observable(params.spaceEditting);
            this.cdConvertCd = ko.observable(params.cdConvertCd);
            this.nullValueReplace = ko.observable(params.nullValueReplace);
            this.valueOfNullValueReplace = ko.observable(params.valueOfNullValueReplace);
            this.fixedValue = ko.observable(params.fixedValue);
            this.valueOfFixedValue = ko.observable(params.valueOfFixedValue);
        }
    }
}




