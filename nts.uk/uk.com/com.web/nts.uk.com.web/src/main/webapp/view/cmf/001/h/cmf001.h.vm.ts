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
            if (inputMode) {
                $('#H2_2').focus();
            } else {
                $('#H6_2').focus();
            }
            if (!nts.uk.util.isNullOrUndefined(charSet)) {
                self.initial(charSet);
            }
            self.validate();
        }
        // エラー時に保存ボタンを無効にする
        validate(){
            var self = this;
            self.characterDataFormatSetting().effectiveDigitLength.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#H2_5').ntsError('clear');
                    $('#H2_8').ntsError('clear');
                }
            });
            self.characterDataFormatSetting().codeEditing.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#H3_5').ntsError('clear');
                }
            });
            self.characterDataFormatSetting().fixedValue.subscribe(function(selectedValue: any) {
                if (selectedValue == 0){
                    $('#H5_5').ntsError('clear');
                }else{
                    $('#H2_5').ntsError('clear');
                    $('#H2_8').ntsError('clear');
                    $('#H3_5').ntsError('clear');
                }
            });
        }
        initComponents() {
            var self = this;
            self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(0, null, null, 0, null, null, null, 0, null));

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
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, getText('CMF001_611')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, getText('CMF001_612')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, getText('CMF001_613')),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, getText('CMF001_614'))
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
            self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(charSet.effectiveDigitLength, charSet.startDigit, charSet.endDigit,
                                                charSet.codeEditing, charSet.codeEditDigit, charSet.codeEditingMethod,
                                                charSet.codeConvertCode, charSet.fixedValue, charSet.fixedVal));
        }
        start(): JQueryPromise<any> {
            block.invisible();
            var self = this;
            var dfd = $.Deferred();
            let convertCodeSelected = self.characterDataFormatSetting().codeConvertCode();
            if (!_.isEmpty(convertCodeSelected)) {
                service.getAcceptCodeConvert(convertCodeSelected).done(function(codeConvert) {
                    if (codeConvert) {
                        self.characterDataFormatSetting().codeConvertCode(codeConvert.convertCd);
                        self.codeConvertCode(new model.AcceptanceCodeConvert(codeConvert.convertCd, codeConvert.convertName, codeConvert.acceptWithoutSetting));
                    }
                    block.clear();
                    dfd.resolve();
                }).fail(function(error) {
                    alertError(error);
                    block.clear();
                    dfd.reject();
                });
            }else{
                block.clear();
                dfd.resolve();
            }
            
            return dfd.promise();
        }
        //項目制御
        enableEffectDigitLengthCls(){
            var self = this;
            return (self.inputMode && self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableCodeEditingCls(){
            var self = this;
            return (self.inputMode && self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableFixedValueCls(){
            var self = this;
            return (self.inputMode);
        }
        enableEffectDigitLength() {
            var self = this;
            return (self.characterDataFormatSetting().effectiveDigitLength() == model.NOT_USE_ATR.USE && self.inputMode && self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableCodeEditing() {
            var self = this;
            return (self.characterDataFormatSetting().codeEditing() == model.NOT_USE_ATR.USE && self.inputMode && self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableConvertCode() {
            var self = this;
            return (self.inputMode && self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.NOT_USE);
        }
        enableFixedValue() {
            var self = this;
            return (self.characterDataFormatSetting().fixedValue() == model.NOT_USE_ATR.USE && self.inputMode);
        }
        // コード変換の選択を行う
        open001_K(data) {
            var self = this;
            let codeConvertCode = self.codeConvertCode();
            setShared("CMF001kParams", { selectedConvertCode: ko.toJS(codeConvertCode)});
            modal("/view/cmf/001/k/index.xhtml").onClosed(() => {
                // コード変換選択を行う
                let params = getShared("CMF001kOutput");
                if (!nts.uk.util.isNullOrUndefined(params)) {
                    let codeConvertCodeSelected = params.selectedConvertCodeShared;
                    self.codeConvertCode(codeConvertCodeSelected);
                    self.characterDataFormatSetting().codeConvertCode(codeConvertCodeSelected.dispConvertCode);
                }
                $('#H4_2').focus();
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
        /**
        * 開始桁、終了桁に入力はあるか判別, 小数桁数に入力があるか判別, 固定値に入力があるか判別
        */
        checkValidInput() {
            var self = this;
            if (self.characterDataFormatSetting().fixedValue() == 0) {
                if (self.characterDataFormatSetting().effectiveDigitLength() == 1) {
                    $('#H2_5').ntsError('check');
                    $('#H2_8').ntsError('check');
                }
                if (self.characterDataFormatSetting().codeEditing() == 1) {
                    $('#H3_5').ntsError('check');
                }
            } else {
                $('#H5_5').ntsError('check');
            }
            if (nts.uk.ui.errors.hasError()) {
                return false;
            }
            self.characterDataFormatSetting().startDigit(Math.floor(self.characterDataFormatSetting().startDigit()));
            self.characterDataFormatSetting().endDigit(Math.floor(self.characterDataFormatSetting().endDigit()));
            
            if (self.characterDataFormatSetting().fixedValue() == 1) {
                if (_.isEmpty(self.characterDataFormatSetting().fixedVal())) {
                    alertError({ messageId: "Msg_2" });
                    return false;
                }
                return true;
            }else{
                if (self.characterDataFormatSetting().effectiveDigitLength() == 1) {
                    let startDigit : number = +self.characterDataFormatSetting().startDigit();
                    let endDigit : number = +self.characterDataFormatSetting().endDigit();
                    if (startDigit == 0 || endDigit == 0) {
                        alertError({ messageId: "Msg_2" });
                        return false;
                    } else {
                        if (startDigit > endDigit) {
                            alertError({ messageId: "Msg_1119", messageParams: [getText('CMF001_270'), getText('CMF001_273')] });
                            return false;
                        }
                    }
                }
                if (self.characterDataFormatSetting().codeEditing() == 1) {
                    if (self.characterDataFormatSetting().codeEditDigit() == 0) {
                        alertError({ messageId: "Msg_2" });
                        return false;
                    }
                } 
            }
            return true;
        }
        // キャンセルして終了する
        cancelCharacterSetting() {
            nts.uk.ui.windows.close();
        }
    }
}