module nts.uk.com.view.cmf001.m.viewmodel {
    import model = nts.uk.com.view.cmf001.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
 
    export class ScreenModel {
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        simpleValue: KnockoutObservable<string>;
        
        selectionType : string;
        conditionCode: KnockoutObservable<string>;
        conditionName: KnockoutObservable<string>;
        
        constructor() {
            var self = this;
            self.simpleValue = ko.observable('123');    
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            self.checked = ko.observable(false);
            let params = getShared('selectionType'); 
            self.selectionType = params.systemType;
            self.conditionCode = ko.observable(params.conditionCode);
            self.conditionName = ko.observable(params.conditionName);
           
        }
        
         /**
         * Close dialog.
         */
        cancelSetting(): void {
            setShared('CMF001mCancel', true);
            nts.uk.ui.windows.close();
        }
        
        //設定済みの場合は、データを上書きします。
        isOverWriteData() {
            var self = this;
            self.checked = ko.observable(true);
        }
        
        //設定
        saveData() {
            
        }
    }
}