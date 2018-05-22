module nts.uk.com.view.kwr002.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
//    import model = kwr002.share.model;
//    import confirm = nts.uk.ui.dialog.confirm;
//    import alertError = nts.uk.ui.dialog.alertError;
//    import info = nts.uk.ui.dialog.info;
//    import modal = nts.uk.ui.windows.sub.modal;
//    import setShared = nts.uk.ui.windows.setShared;
//    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
//        systemTypes: KnockoutObservableArray<model.BoxModel> = ko.observableArray([]);
//        systemType: KnockoutObservable<number>;
//        screenMode: KnockoutObservable<number>;
        
        //B5_1
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;

        
        //B2_2
        listStandardImportSetting : KnockoutObservableArray<string>;
        selectedStandardImportSettingCode : KnockoutObservable<string>;
        dispConditionSettingCode : KnockoutObservable<string>;
        dispConditionSettingName : KnockoutObservable<string>;
        
        //B3_2 B3_3
        inputSettingCode: KnockoutObservable<string>;
        inputProjectName: KnockoutObservable<string>;
        
        //B5_3
        itemRadio: KnockoutObservableArray<any>;
        selectedItemRadio: KnockoutObservable<number>;
        
        //B2_2
        items: KnockoutObservableArray<model.ItemModel>;
        columns: KnockoutObservableArray<model.ItemModel>;       
        
        constructor() {
            var self = this;
            
            //B2_2
            self.listStandardImportSetting = ko.observableArray([]);
            self.selectedStandardImportSettingCode = ko.observable('');
            self.dispConditionSettingName = ko.observable('');
            self.dispConditionSettingCode = ko.observable('');
            
                 
            //B5_1
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            
            //B3_2 B3_3
            self.inputSettingCode = ko.observable('');
            self.inputProjectName = ko.observable('');
            
            //B5_3
            self.itemRadio = ko.observableArray([
                {id: 0, name : getText('CMF001_37')},
                {id: 1, name : getText('CMF001_38')},
                {id: 2, name : getText('CMF001_39')},
            ]);
            self.selectedItemRadio = ko.observable(1);
            
            
            //table fixed
            $("#fixed-table").ntsFixedTable({ height: 304, width: 900 });
        }
        
        startPage(): JQueryPromise<any> {
            var self = this;
            
            var test = $.Deferred();
            return test.promise();            
        }
    }
}
