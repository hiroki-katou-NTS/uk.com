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
        characterDataFormatSetting: KnockoutObservable<model.CharacterDataFormatSetting> = ko.observable(new model.CharacterDataFormatSetting(0, 1, 1, 0, 0, 0, "1", 0, "1"));
        
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
        inputMode: boolean;
        constructor() {
            var self = this;
            self.inputMode = true;
            
        }
        open001_K(){
            
        }
    }
}