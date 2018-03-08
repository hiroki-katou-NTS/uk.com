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
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting> = 
        ko.observable(new model.CharacterDataFormatSetting(0, null, null, 0, null, null, new model.AcceptanceCodeConvert("", "", 0), 0, ""));
        
        effectDigitItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_268')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_269'))
        ]);
        codeEditingItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_277')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_278'))
        ]);
        effectFixedValItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_294')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_295'))
        ]);
        fixedLengMethod: KnockoutObservableArray<model.ItemModel>;
        inputMode: boolean = true;
        lineNumber: number = -1;
        constructor() {
            var self = this;
            self.inputMode = true;
            self.fixedLengMethod = ko.observableArray([
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_BEFORE, 'ZERO_BEFORE'),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.ZERO_AFTER, 'ZERO_AFTER'),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_BEFORE, 'SPACE_BEFORE'),
                new model.ItemModel(model.FIXED_LENGTH_EDITING_METHOD.SPACE_AFTER, 'SPACE_AFTER')
            ]);
            let params = getShared("CMF001hParams"); 
            if(!nts.uk.util.isNullOrUndefined(params)){
                let inputMode = params.inputMode;
                let lineNumber = params.lineNumber;
                let charSet = params.formatSetting;
                self.inputMode = inputMode;
                self.lineNumber = lineNumber;
                if(!nts.uk.util.isNullOrUndefined(charSet)){
                    self.initial(charSet);
                }
            }
        }
        // データが取得できる場合
        setting(){
//            nts.uk.util.value.reset($("#H2_5"), null);
//            nts.uk.util.value.reset($("#H2_8"), null);
//            nts.uk.util.value.reset($("#H3_5"), null);
//            nts.uk.util.value.reset($("#H5_5"), null);
        }
        
        // データが取得できない場合
        initial(charSet){
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
            convertCode, charSet.fixedValue, charSet.valueOfFixed));   
//            nts.uk.util.value.reset($("#H2_5"), numFormat.startDigit);
//            nts.uk.util.value.reset($("#H2_8"), numFormat.endDigit);
//            nts.uk.util.value.reset($("#H3_5"), numFormat.decimalDigitNumber);
//            nts.uk.util.value.reset($("#H5_5"), numFormat.fixedVal);
        }
        // コード変換の選択を行う
        open001_K(data){
            var self = this;
            let selectedConvertCode = data.codeConvertCode();
            setShared("CMF001kParams", { selectedConvertCode: ko.toJS(selectedConvertCode) });
            modal("/view/cmf/001/k/index.xhtml").onClosed(() => {
                // コード変換選択を行う
                let params = getShared("CMF001kOutput");
                if(!nts.uk.util.isNullOrUndefined(params)){
                    let codeConvertCodeSelected = params.selectedConvertCodeShared;
                    self.characterDataFormatSetting().codeConvertCode(codeConvertCodeSelected);
                }
            });
        }
        // 数値編集の設定をして終了する
        saveCharacterSetting(){
            var self = this;
//            $("#H2_5").trigger("validate");
//            $("#H2_8").trigger("validate");
//            $("#H3_5").trigger("validate");
            if(self.inputMode){
                setShared("CMF001FormatOutput", {lineNumber: self.lineNumber, formatSetting: ko.toJS(self.characterDataFormatSetting) });
            }
            nts.uk.ui.windows.close();
        }
        // キャンセルして終了する
        cancelCharacterSetting(){
            nts.uk.ui.windows.close();   
        }
    }
}