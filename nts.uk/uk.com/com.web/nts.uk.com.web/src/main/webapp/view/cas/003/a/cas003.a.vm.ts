module nts.uk.com.view.cas003.a {
    export module viewmodel {
        import href = nts.uk.request.jump;
        import getText = nts.uk.resource.getText;
        import infor = nts.uk.ui.dialog.info;
        import alert = nts.uk.ui.dialog.alert;
        export class ScreenModel {
            PassPolicyCheck: KnockoutObservable<boolean>;
            firstTimeCheck: KnockoutObservable<boolean>;
            violationPassCheck: KnockoutObservable<boolean>;
            accLockCheck: KnockoutObservable<boolean>;
            simpleValue: KnockoutObservable<string>;
            
           
            constructor() {
                var self = this;
                self.simpleValue = ko.observable("");
                self.PassPolicyCheck = ko.observable(true);
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



