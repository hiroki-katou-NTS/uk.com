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
        
        /* screen */
        effectDigit: KnockoutObservable<boolean>;
        deffectDigit: KnockoutObservable<boolean>;
        effectMinority: KnockoutObservable<boolean>;
        deffectMinority: KnockoutObservable<boolean>;
        effectFixedVal: KnockoutObservable<boolean>;
        deffectFixedVal: KnockoutObservable<boolean>;
        
        selectedFraction: KnockoutObservable<string>;
        selectedPointIndicator: KnockoutObservable<string>
        //selectedMinorityEdit: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        minorityDigit: KnockoutObservable<number>;
        fixedVal: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.effectDigit = ko.observable(false);
            self.deffectDigit = ko.observable(true);
            self.effectMinority = ko.observable(false);
            self.deffectMinority = ko.observable(false);
            self.effectFixedVal = ko.observable(false);
            self.deffectFixedVal = ko.observable(false);
            self.startDigit = ko.observable(1);
            self.endDigit = ko.observable(8);
            self.minorityDigit = ko.observable(2);
            self.fixedVal = ko.observable(12345678901234567890);
            self.selectedFraction = ko.observable('001');
            self.selectedPointIndicator = ko.observable('001');
            self.enable = ko.observable(true);
            
        }
        open001_K(){
            var self = this;
            alert(self.effectDigit);
            alert(self.deffectDigit);
        }
        saveNumericSetting(){
            
        }
        cancelNumericSetting(){
            
        }
    }
    
}