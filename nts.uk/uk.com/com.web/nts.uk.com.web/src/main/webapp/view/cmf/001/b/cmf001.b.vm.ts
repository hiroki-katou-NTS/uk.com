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
        systemTypes: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getSystemTypes());
        systemType: KnockoutObservable<number>;
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
            self.systemType = ko.observable(0);
            
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
            
            self.systemType.subscribe((data) => {
                self.listStandardImportSetting([
                    new model.StandardAcceptanceConditionSetting('0'+data+'1', 'Import Setting '+data+'1', 2, 0, 0, 1),
                    new model.StandardAcceptanceConditionSetting('0'+data+'2', 'Import Setting '+data+'2', 1, 1, 0, 1), 
                    new model.StandardAcceptanceConditionSetting('0'+data+'3', 'Import Setting '+data+'3', null, 2, 0, 1),
                    new model.StandardAcceptanceConditionSetting('0'+data+'4', 'Import Setting '+data+'4', 1, 3, 1, 2),
                    new model.StandardAcceptanceConditionSetting('0'+data+'5', 'Import Setting '+data+'5', 0, 0, 1, 3)
                ]);
            });
        }
        
        openCMF001d() {
            let self = this;
            nts.uk.request.jump("/view/cmf/001/d/index.xhtml", {
                systemType: self.systemType(),
                conditionSetting: ko.toJS(self.selectedStandardImportSetting)
            });
        }
        
        openCMF001m() {
            let self = this;
            setShared('CMF001mParams', {
                activation: model.M_ACTIVATION.Duplicate_Standard,
                systemType: self.systemType(),
                conditionCode: self.selectedStandardImportSetting().conditionSettingCode(),
                conditionName: self.selectedStandardImportSetting().conditionSettingName()
            }, true);
            
            modal("/view/cmf/001/m/index.xhtml").onClosed(function() {
                var output = getShared('CMF001mOutput');
                if (output)
                    self.selectedItem(output);
            });
        }
    }
}