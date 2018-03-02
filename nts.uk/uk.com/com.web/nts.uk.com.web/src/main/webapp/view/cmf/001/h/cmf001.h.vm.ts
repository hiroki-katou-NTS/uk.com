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
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting> = ko.observable(new model.CharacterDataFormatSetting(0, null, null, 0, null, 0, "1", 0, ""));
        
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
            let params = getShared("CMF001mParams"); 
            if(!nts.uk.util.isNullOrUndefined(params)){
                let inputMode = params.inputMode;
                let lineNumber = params.lineNumber;
                let charSet = params.formatSetting;
                self.inputMode = inputMode;
                self.lineNumber = lineNumber
                if(!(_.isNull(charSet))){
                    self.characterDataFormatSetting = ko.observable(new model.CharacterDataFormatSetting(charSet.effectiveDigitLength, charSet.startDigit,
                    charSet.endDigit, charSet.codeEditing, charSet.codeEditDigit, charSet.codeEditingMethod,
                    charSet.codeConvertCode, charSet.fixedValue, charSet.valueOfFixed));   
                }
            }
            
        }
        open001_K(){
            var self = this;
            modal("/view/cmf/001/k/index.xhtml").onClosed(()=>{
                
                //get shared
                alert("CLOSED");
            });
        }
        saveCharacterSetting(){
            var self = this;
            if(self.inputMode){
                setShared("CMF001mOutput", {lineNumber: self.lineNumber, formatSetting: ko.toJS(self.characterDataFormatSetting) });
                nts.uk.ui.windows.close();
            }else{
                
            }
        }
        cancelCharacterSetting(){
            nts.uk.ui.windows.close();   
        }
    }
}