module nts.uk.com.view.cas003.a {
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        import alert = nts.uk.ui.dialog.alert;
        export class ScreenModel {
            passPolicyCheck: KnockoutObservable<boolean>;
            firstTimeCheck: KnockoutObservable<boolean>;
            violationPassCheck: KnockoutObservable<boolean>;
            accLockCheck: KnockoutObservable<boolean>;
            simpleValue: KnockoutObservable<string>;
            lowestDigits: KnockoutObservable<number>;
            alphabetDigit: KnockoutObservable<number>;
            numberOfDigits: KnockoutObservable<number>;
            symbolCharacters: KnockoutObservable<number>;
            historyCount: KnockoutObservable<number>;
            validityPeriod: KnockoutObservable<number>;
            notificationPasswordChange: KnockoutObservable<number>;
            lockInterval: KnockoutObservable<number>;
            errorCount: KnockoutObservable<number>;
            lockOutMessage: KnockoutObservable<String>;
            
           
            constructor() {
                var self = this;
                self.simpleValue = ko.observable("");
                self.lowestDigits = ko.observable(null);
                self.alphabetDigit = ko.observable(null);
                self.numberOfDigits = ko.observable(null);
                self.symbolCharacters = ko.observable(null);
                self.historyCount = ko.observable(null);
                self.validityPeriod = ko.observable(null);
                self.notificationPasswordChange = ko.observable(null);
                self.lockInterval = ko.observable(null);
                self.errorCount = ko.observable(null);
                self.lockOutMessage = ko.observable("");
                self.passPolicyCheck = ko.observable(true);
                self.violationPassCheck = ko.observable(true);
                self.firstTimeCheck = ko.observable(true);
                self.accLockCheck = ko.observable(true);
            }




            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            submitData(): void {
                var self = this;

            }


        }

    }
}



