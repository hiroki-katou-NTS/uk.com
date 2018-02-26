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
        effectDigit: KnockoutObservable<boolean>;
        deffectDigit: KnockoutObservable<boolean>;
        effectFixedLen: KnockoutObservable<boolean>;
        deffectFixedLen: KnockoutObservable<boolean>;
        effectFixedVal: KnockoutObservable<boolean>;
        deffectFixedVal: KnockoutObservable<boolean>;
        
        
        startDigit: KnockoutObservable<number>;
        endDigit: KnockoutObservable<number>;
        fixedLen: KnockoutObservable<number>;
        selectedFixedLenMeth: KnockoutObservable<number>;
        fixedVal: KnockoutObservable<string>;
        constructor() {
            var self = this;
            self.effectDigit = ko.observable(false);
            self.deffectDigit = ko.observable(true);
            self.effectFixedLen = ko.observable(false);
            self.deffectFixedLen = ko.observable(true);
            self.startDigit = ko.observable(1);
            self.endDigit = ko.observable(8);
            self.fixedLen = ko.observable(10);
            self.selectedFixedLenMeth = ko.observable(0);
            self.effectFixedVal = ko.observable(false);
            self.deffectFixedVal = ko.observable(true);
            self.fixedVal = ko.observable("00000000000000000000");
        }
        setCharacterSetting(){
            
        }
        cancelCharacterSetting(){
            
        }
        open001_K(){
            
        }
    }
}