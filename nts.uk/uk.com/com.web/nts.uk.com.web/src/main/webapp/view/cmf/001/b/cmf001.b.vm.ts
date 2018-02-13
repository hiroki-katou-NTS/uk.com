module nts.uk.com.view.cmf001.b.viewmodel {
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
        itemList: KnockoutObservableArray<model.ItemModel>;
        selectedCode: KnockoutObservable<number>;
        radioItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(1, getText('CMF001_56')),
            new model.ItemModel(0, getText('CMF001_57'))
        ]);
        acceptModes: KnockoutObservableArray<model.ItemModel> = ko.observableArray([
            new model.ItemModel(0, getText('CMF001_66')),
            new model.ItemModel(1, getText('CMF001_67')),
            new model.ItemModel(2, getText('CMF001_68'))
        ]);
        
        listStandardImportSetting: KnockoutObservableArray<model.StandardAcceptanceConditionSetting>;
        selectedStandardImportSettingCode: KnockoutObservable<string>;
        selectedStandardImportSetting: KnockoutObservable<model.StandardAcceptanceConditionSetting>;
        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new model.ItemModel(1, 'Item 1'),
                new model.ItemModel(2, 'Item 2'),
                new model.ItemModel(3, 'Item 3')
            ]);
            self.selectedCode = ko.observable(1);
            
            self.listStandardImportSetting = ko.observableArray([
                new model.StandardAcceptanceConditionSetting('001', 'Import Setting 1', 2, 0, 0, 1),
                new model.StandardAcceptanceConditionSetting('002', 'Import Setting 2', 1, 1, 0, 1), 
                new model.StandardAcceptanceConditionSetting('003', 'Import Setting 3', null, 2, 0, 1),
                new model.StandardAcceptanceConditionSetting('004', 'Import Setting 4', 1, 3, 1, 2),
                new model.StandardAcceptanceConditionSetting('005', 'Import Setting 5', 0, 0, 1, 3)
            ]);
            self.selectedStandardImportSettingCode = ko.observable(self.listStandardImportSetting()[0].dispConditionSettingCode);
            self.selectedStandardImportSetting = ko.observable(self.listStandardImportSetting()[0]);
            self.selectedStandardImportSettingCode.subscribe(data => {
                if (data) {
                    let item = _.find(self.listStandardImportSetting(), x => {return x.dispConditionSettingCode == data;});
                    self.selectedStandardImportSetting(item);
                }
            });
        }
        
        
        
    }
}