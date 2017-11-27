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
        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);

        employeeInitItemList: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_78') },
            { code: 3, name: getText('CPS002_79') },
            { code: 2, name: getText('CPS002_80') }
        ]);

        empCodeValue: KnockoutObservable<number> = ko.observable(2);

        txtEmpCodeLetter: KnockoutObservable<string> = ko.observable("");

        cardNoInitItemList: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_78') },
            { code: 4, name: getText('CPS002_84') },
            { code: 3, name: getText('CPS002_85') },
            { code: 5, name: getText('CPS002_86') },
            { code: 2, name: getText('CPS002_80') }

        ]);

        cardNoValue: KnockoutObservable<number> = ko.observable(3);

        txtCardNo: KnockoutObservable<string> = ko.observable("");

        constructor() {


        }

        register() {
            let self = this;
            let command = {
                employeeId: "-1",
                empCodeValType: self.empCodeValue(),
                cardNoValType: self.cardNoValue(),
                empCodeLetter: self.empCodeValue() != 1 ? "" : self.txtEmpCodeLetter(),
                cardNoLetter: self.cardNoValue() != 1 ? "" : self.txtCardNo(),
                recentRegType: self.selectedRuleCode()
            };
            service.setUserSetting(command).done(function() {
                setShared("userSettingStatus", true);
            })
                .fail(function() { setShared("userSettingStatus", false); })
                .always(function() { self.close(); });
        }
        close() { close(); }

    }
}