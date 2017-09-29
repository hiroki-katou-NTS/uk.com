module cps002.g.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;
    

    export class ViewModel {
        
        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_89') },
            { code: 0, name: getText('CPS002_90') }
        ]);
        selectedRuleCode: KnockoutObservable<string> = ko.observable("");
        empCodeValue: KnockoutObservable<string> = ko.observable("");
        cardNoValue: KnockoutObservable<string> = ko.observable("");
        txtEmpCodeLetter: KnockoutObservable<string> = ko.observable("");
        txtCardNo: KnockoutObservable<string> = ko.observable("");
        
        constructor(){
           
           
        }
       
        register(){
            let self = this;
            let objectSending = {
                empCodeValue: self.empCodeValue(),
                cardNoValue: self.cardNoValue(),
                txtEmpCodeLetter: self.empCodeValue() == "1" ? "" : self.txtEmpCodeLetter(),
                txtCardNo: self.cardNoValue() == "1" ? "" : self.txtCardNo()
            };
        }
        
    }
}