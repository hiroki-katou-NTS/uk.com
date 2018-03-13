module nts.uk.com.view.cmf001.h.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import model = cmf001.share.model;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting>;

        effectDigitItem: KnockoutObservableArray<model.ItemModel>;
        codeEditingItem: KnockoutObservableArray<model.ItemModel>;
        effectFixedValItem: KnockoutObservableArray<model.ItemModel>;
        fixedLengMethod: KnockoutObservableArray<model.ItemModel>;
        codeConvertCode: KnockoutObservable<model.AcceptanceCodeConvert>;
        inputMode: boolean;
        lineNumber: number;
        constructor() {
            var self = this;
            self.inputMode = false;
            self.initComponents(); 
            let params = getShared("CMF001hParams");
            let inputMode = params.inputMode;
            let lineNumber = params.lineNumber;
            let charSet = params.formatSetting;
            self.inputMode = inputMode;
            self.lineNumber = lineNumber;
            if (!nts.uk.util.isNullOrUndefined(charSet)) {
                self.initial(charSet);
            }
        }
        initComponents() {
            var self = this;
            self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(0, null, null, 0, null, null, null, null, null));

            self.effectDigitItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_268')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_269'))
            ]);
            self.codeEditingItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_277')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_278'))
            ]);
            self.effectFixedValItem = ko.observableArray([
                new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_294')),
                new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_295'))
            ]);
            self.fixedLengMethod = ko.observableArray([
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('Enum_FixedLengthEditingMethod_ZERO_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('Enum_FixedLengthEditingMethod_ZERO_AFTER')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('Enum_FixedLengthEditingMethod_SPACE_BEFORE')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('Enum_FixedLengthEditingMethod_SPACE_AFTER'))
            ]);
            self.codeConvertCode = ko.observable(new model.AcceptanceCodeConvert("", "", 0));
            self.inputMode = true;
            self.lineNumber = -1;
        }
        // データが取得できない場合
        initial(charSet) {
            var self = this;
            let convertCodeShared = charSet.codeConvertCode;
            let convertCode = null;
            if (nts.uk.util.isNullOrUndefined(convertCodeShared)) {
                convertCode = new model.AcceptanceCodeConvert("", "", 0);
            } else {
                convertCode = new model.AcceptanceCodeConvert(convertCodeShared.convertCode, convertCodeShared.convertName, 0);
            }
            self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(charSet.effectiveDigitLength, charSet.startDigit,
                charSet.endDigit, charSet.codeEditing, charSet.codeEditDigit, charSet.codeEditingMethod,
                charSet.codeConvertCode, charSet.fixedValue, charSet.valueOfFixed));
        }
        start(): JQueryPromise<any> {
            block.invisible();
            var self = this;
            var dfd = $.Deferred();
            service.getAcceptCodeConvert(self.characterDataFormatSetting().codeConvertCode()).done(function(codeConvert) {
                if (codeConvert) {
                    self.characterDataFormatSetting().codeConvertCode(codeConvert.convertCode);
                    self.codeConvertCode(new model.AcceptanceCodeConvert(codeConvert.convertCd, codeConvert.convertName, codeConvert.acceptWithoutSetting));
                }
                block.clear();
                dfd.resolve();
            }).fail(function(error) {
                alertError(error);
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }
        enableEffectDigitLength(){
            var self = this;
            return (self.characterDataFormatSetting().effectiveDigitLength() == model.NOT_USE_ATR.USE && self.inputMode && !self.characterDataFormatSetting().fixedValue());
        }
        enableCodeEditing (){
            var self = this;
            return (self.characterDataFormatSetting().codeEditing() == model.NOT_USE_ATR.USE && self.inputMode && !self.characterDataFormatSetting().fixedValue());
        }
        enableConvertCode(){
             var self = this;
            return (self.inputMode && !self.characterDataFormatSetting().fixedValue());   
        }
        enableFixedValue(){
            var self = this;
            return (self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }
        // コード変換の選択を行う
        open001_K(data) {
            var self = this;
            let selectedConvertCode = self.characterDataFormatSetting().codeConvertCode();
            setShared("CMF001kParams", { selectedConvertCode: ko.toJS(selectedConvertCode) });
            modal("/view/cmf/001/k/index.xhtml").onClosed(() => {
                // コード変換選択を行う
                let params = getShared("CMF001kOutput");
                if (!nts.uk.util.isNullOrUndefined(params)) {
                    let codeConvertCodeSelected = params.selectedConvertCodeShared;
                    self.codeConvertCode(codeConvertCodeSelected);
                    self.characterDataFormatSetting().codeConvertCode(codeConvertCodeSelected.dispConvertCode);
                }
            });
        }
        // 数値編集の設定をして終了する
        saveCharacterSetting() {
            var self = this;
            if (self.checkValidInput()) {
                setShared("CMF001FormatOutput", { lineNumber: self.lineNumber, formatSetting: ko.toJS(self.characterDataFormatSetting) });
                nts.uk.ui.windows.close();
            }
        }
        checkValidInput() {
            var self = this;
            let checkValidInput: boolean = true;
            if (self.characterDataFormatSetting().effectiveDigitLength() == 1) {
                let startDigit = self.characterDataFormatSetting().startDigit();
                let endDigit = self.characterDataFormatSetting().endDigit();
                if (startDigit == 0 || endDigit == 0) {
                    checkValidInput = false;
                } else {
                    if (startDigit > endDigit) {
                        checkValidInput = false;
                        alertError({ messageId: "Msg_1108" });
                        return false;
                    }
                }
            }
            if (self.characterDataFormatSetting().codeEditing() == 1) {
                if (self.characterDataFormatSetting().codeEditDigit() == 0) {
                    checkValidInput = false;
                }
            }
            if (self.characterDataFormatSetting().fixedValue() == 1) {
                if (_.isEmpty(self.characterDataFormatSetting().fixedVal())) {
                    checkValidInput = false;
                }
            }
            if (!checkValidInput){
                alertError({ messageId: "Msg_2" });
            }
            return checkValidInput;
        }
        // キャンセルして終了する
        cancelCharacterSetting() {
            nts.uk.ui.windows.close();
        }
    }
}