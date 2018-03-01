module nts.uk.com.view.cmf001.i.viewmodel {
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
        isEditMode: KnockoutObservable<boolean>;
        
        selectedFormat: KnockoutObservable<any>;
        itemsFormat: KnockoutObservableArray<model.ItemModel>;

        selectedFixedValue: KnockoutObservable<any>;
        itemsFixedValue: KnockoutObservableArray<model.ItemModel>;
        fixedValue: KnockoutObservable<any>;
        
        constructor() {
            let self = this;
            
            self.isEditMode = ko.observable(true);
            self.itemsFormat = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_303')),
                new model.ItemModel(1, getText('CMF001_304')),
                new model.ItemModel(2, getText('CMF001_305')),
                new model.ItemModel(3, getText('CMF001_306')),
                new model.ItemModel(4, getText('CMF001_307')),
                new model.ItemModel(5, getText('CMF001_308'))
            ]);
            self.selectedFormat = ko.observable(1);
            
            self.itemsFixedValue = ko.observableArray([
                new model.ItemModel(0, getText('CMF001_322')),
                new model.ItemModel(1, getText('CMF001_323'))
            ]);
            self.selectedFixedValue = ko.observable(1);
            self.fixedValue = ko.observable('');
        }

        saveNumericSetting() {
            console.log(this.selectedFormat());
            console.log(this.selectedFixedValue());
        }
        cancelNumericSetting() {
            nts.uk.ui.windows.close(); //Close current window
        }
    }
}