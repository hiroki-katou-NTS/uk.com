module nts.uk.com.view.cmf002.n.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        
        isUse: KnockoutObservable<boolean>;
        selectedValue1: KnockoutObservable<any>;
        selectedValue2: KnockoutObservable<any>;
        items: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(model.NOT_USE_ATR.USE, getText('CMF002_149')),
            new model.ItemModel(model.NOT_USE_ATR.NOT_USE, getText('CMF002_150'))
            ]);
        getValue: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.selectedValue1 = ko.observable(true);
            self.selectedValue2 = ko.observable(false);
            self.getValue = ko.observable('');
            
        }
        
         /**
         * Close dialog.
         */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }
        
        saveSetting(){
        }
    }
}