module nts.uk.com.view.cmf002.t.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.errors;
    
    export class ScreenModel {
        systemType: model.ItemModel;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        checked: KnockoutObservable<boolean>;
        newCondCode: KnockoutObservable<string>;
        newCondName: KnockoutObservable<string>;
        selectionType : string;
        conditionCode: KnockoutObservable<string>;
        conditionName: KnockoutObservable<string>;
        targetType: string;
        
        constructor() {
            var self = this; 
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            self.checked = ko.observable(true);
            self.newCondCode = ko.observable('');
            self.newCondName = ko.observable('');
            let params = getShared('CMF001mParams'); 
            self.selectionType = params.systemType;
            self.conditionCode = ko.observable(params.conditionCode);
            self.conditionName = ko.observable(params.conditionName);
        }
         /**
         * Close dialog.
         */
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }
        
        //設定
        saveData() {
            var self = this;
            $(".nts-editor").trigger("validate");
//            if (!nts.uk.ui.errors.hasError()) {
//                service.checkExistCode(self.systemType.code, self.newCondCode()).done((result) => {
//                    if(result && !self.checked()){
//                        nts.uk.ui.dialog.alertError({ messageId: "Msg_3", messageParams: [nts.uk.resource.getText("T3_2")] }).then(() => {
//                            $("#T4").focus();  
//                        });
//                    } else {
//                        setShared('CMF001mOutput', {
//                            checked: self.checked(),
//                            code: self.newCondCode(),
//                            name: self.newCondName()
//                        }, true);  
//                        nts.uk.ui.windows.close();
//                    }
//                });
//                
//            }
        }
    }
}