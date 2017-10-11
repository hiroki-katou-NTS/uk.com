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
            { code: "1", name: getText('CPS002_89') },
            { code: "0", name: getText('CPS002_90') }
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
            let command = {
                employeeId: "-1",
                empCodeValType: parseInt(self.empCodeValue()),
                cardNoValType: parseInt(self.cardNoValue()),
                empCodeLetter: parseInt(self.empCodeValue()) != 1 ? "" : self.txtEmpCodeLetter(),
                cardNoLetter: parseInt(self.cardNoValue()) != 1 ? "" : self.txtCardNo(),
                recentRegType: parseInt(self.selectedRuleCode())
            };
            service.setUserSetting(command).done(function(){
                setShared("userSettingStatus", true);
                })
            .fail(function(){setShared("userSettingStatus", false);})
            .always(function(){ self.close();});
        }
        close(){close();}
        
    }
}