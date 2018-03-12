module nts.uk.com.view.cmf001.g.viewmodel {
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
        numDataFormatSetting: KnockoutObservable<model.NumericDataFormatSetting>
        = ko.observable(new model.NumericDataFormatSetting(0, null, null, 0, null, null, null, "", 0, ""));
        
        effectDigitItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_223')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_224'))
        ]);
        effectMinorityItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_232')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_233'))
        ]);
        effectFixedValItem: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF001_254')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF001_255'))
        ]);
        decimalPointClsItem: KnockoutObservableArray<model.ItemModel>;
        decimalFractionItem: KnockoutObservableArray<model.ItemModel>;
        codeConvertCode: KnockoutObservable<model.AcceptanceCodeConvert> = ko.observable(new model.AcceptanceCodeConvert("","",0));
        inputMode: boolean = true;
        lineNumber: number = -1;
        constructor() {
            var self = this;
            self.decimalPointClsItem = ko.observableArray([
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.NO_OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_NO_OUTPUT_DECIMAL_POINT')),
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT, getText('Enum_DecimalPointClassification_OUTPUT_DECIMAL_POINT'))
            ]);
            self.decimalFractionItem = ko.observableArray([
                new model.ItemModel(model.ROUNDING_METHOD.TRUNCATION, getText('Enum_Rounding_Truncation')),
                new model.ItemModel(model.ROUNDING_METHOD.ROUND_UP, getText('Enum_Rounding_Round_Up')),
                new model.ItemModel(model.ROUNDING_METHOD.DOWN_4_UP_5, getText('Enum_Rounding_Down_4_Up_5'))
            ]);
            
            let params = getShared("CMF001gParams");
            let inputMode = params.inputMode;
            let lineNumber = params.lineNumber;
            let numFormat = params.formatSetting;
            self.inputMode = inputMode;
            self.lineNumber = lineNumber;
            if (!(nts.uk.util.isNullOrUndefined(numFormat))) {
                self.initial(numFormat);
            }
        }
        // データが取得できない場合
        initial(numFormat){
            var self = this;
            let convertCodeShared = numFormat.codeConvertCode;
            let convertCode = null;
            if (nts.uk.util.isNullOrUndefined(convertCodeShared)) {
                convertCode = new model.AcceptanceCodeConvert("", "", 0);
            } else {
                convertCode = new model.AcceptanceCodeConvert(convertCodeShared.convertCode, convertCodeShared.convertName, 0);
            }
            self.numDataFormatSetting(new model.NumericDataFormatSetting(numFormat.effectiveDigitLength, numFormat.startDigit,
                numFormat.endDigit, numFormat.decimalDivision, numFormat.decimalDigitNumber, numFormat.decimalPointClassification,
                numFormat.decimalFraction, numFormat.codeConvertCode, numFormat.fixedValue, numFormat.valueOfFixed));
        }
        start(): JQueryPromise<any> {
            block.invisible();
            var self = this;
            var dfd = $.Deferred();
            let convertCodeSelected = self.numDataFormatSetting().codeConvertCode();
            service.getAcceptCodeConvert(convertCodeSelected).done(function(codeConvert) {
                if (codeConvert) {
                    self.numDataFormatSetting().codeConvertCode(codeConvert.convertCode);
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
        // コード変換の選択を行う
        open001_K(data) {
            var self = this;
            let codeConvertCode = self.codeConvertCode();
            setShared("CMF001kParams", { selectedConvertCode: ko.toJS(codeConvertCode)});
            modal("/view/cmf/001/k/index.xhtml").onClosed(() => {
                let params = getShared("CMF001kOutput");
                if(!nts.uk.util.isNullOrUndefined(params)){
                    let codeConvertCodeSelected = params.selectedConvertCodeShared;
                    self.codeConvertCode(codeConvertCodeSelected);
                    self.numDataFormatSetting().codeConvertCode(codeConvertCodeSelected.dispConvertCode);
                }
            });
        }
        // 数値編集の設定をして終了する
        saveNumericSetting() {
            var self = this;
            if (self.checkValidInput()) {
                setShared("CMF001FormatOutput", { lineNumber: self.lineNumber, formatSetting: ko.toJS(self.numDataFormatSetting) });
                nts.uk.ui.windows.close();
            }
        }
        /**
        * 開始桁、終了桁に入力はあるか判別, 小数桁数に入力があるか判別, 固定値に入力があるか判別
        */
        checkValidInput (){
            var self = this;
            let checkValidInput: boolean = true;
            if (self.numDataFormatSetting().effectiveDigitLength() ==1){
                let startDigit = self.numDataFormatSetting().startDigit();
                let endDigit = self.numDataFormatSetting().endDigit();
                if (startDigit ==0 || endDigit ==0){
                    checkValidInput = false;    
                }else{
                    if (startDigit > endDigit ){
                        checkValidInput = false;  
                        alertError({ messageId: "Msg_1108", messageParams: [getText('CMF001_270'), getText('CMF001_273')] });
                        return false;
                    } 
                }
            }
            if (self.numDataFormatSetting().decimalDivision() ==1){
                if (self.numDataFormatSetting().decimalDigitNumber() == 0){
                    checkValidInput = false;
                }
            }
            if (self.numDataFormatSetting().fixedValue() ==1){
                if (_.isEmpty(self.numDataFormatSetting().valueOfFixedValue())){
                    checkValidInput = false;
                }
            }
            if (!checkValidInput){
                alertError({ messageId: "Msg_2"});
            }
            return checkValidInput;
        }
        
        // キャンセルして終了する
        cancelNumericSetting() {
            nts.uk.ui.windows.close();
        }
    }
}