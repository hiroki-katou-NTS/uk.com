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
       
        numDataFormatSetting: KnockoutObservable<model.NumericDataFormatSetting> = ko.observable(new model.NumericDataFormatSetting(0, null, null , 0, null,
         0, 0, "1", 0, ""));
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
        
        /* value*/
        inputMode: boolean = true
        lineNumber: number = -1
        constructor() {
            var self = this;
            self.decimalPointClsItem = ko.observableArray([
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.NO_OUTPUT_DECIMAL_POINT, 'NO_OUTPUT_DECIMAL_POINT'),
                new model.ItemModel(model.DECIMAL_POINT_CLASSIFICATION.OUTPUT_DECIMAL_POINT, 'OUTPUT_DECIMAL_POINT')
            ]);
            self.decimalFractionItem = ko.observableArray([
                new model.ItemModel(model.ROUNDING_METHOD.TRUNCATION, 'TRUNCATION'),
                new model.ItemModel(model.ROUNDING_METHOD.ROUND_UP, 'ROUND_UP'),
                new model.ItemModel(model.ROUNDING_METHOD.DOWN_4_UP_5, 'DOWN_4_UP_5')
            ]);
            let params = getShared("CMF001mParams");
            if(!nts.uk.util.isNullOrUndefined(params)){
                let inputMode = params.inputMode;
                let lineNumber = params.lineNumber;
                let numFormat = params.formatSetting;
                self.inputMode = inputMode;
                self.lineNumber = lineNumber;
                if(!(_.isNull(numFormat))) 
                    self.numDataFormatSetting(new model.NumericDataFormatSetting(numFormat.effectiveDigitLength, numFormat.startDigit,
                    numFormat.endDigit, numFormat.decimalDivision, numFormat.decimalDigitNumber, numFormat.decimalPointClassification,
                    numFormat.decimalFraction, numFormat.codeConvertCode, numFormat.fixedValue, numFormat.valueOfFixed)); 
            }
            
        }
        open001_K(){
            var self = this;
            modal("/view/cmf/001/k/index.xhtml").onClosed(()=>{
                
                //get shared
                alert("CLOSED");
            });
        }
        saveNumericSetting(){
            var self = this;
            if(self.inputMode){
                setShared("CMF001mOutput", {lineNumber: self.lineNumber, formatSetting: ko.toJS(self.numDataFormatSetting) });
                nts.uk.ui.windows.close();
            }else{
                
            }
            
        }
        cancelNumericSetting(){
            nts.uk.ui.windows.close();
        }
    }
    
}