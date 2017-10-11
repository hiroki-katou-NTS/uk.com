module nts.uk.at.view.kdw006.c.viewmodel {
    export class ScreenModelC {
        yearDisplayAtr: KnockoutObservable<boolean>;
        yearRemainingNumberCheck: KnockoutObservable<boolean>;
        savingYearDisplayAtr: KnockoutObservable<boolean>;
        savingYearRemainingNumberCheck: KnockoutObservable<boolean>;
        compensatoryDisplayAtr: KnockoutObservable<boolean>;
        compensatoryRemainingNumberCheck: KnockoutObservable<boolean>;
        substitutionDisplayAtr: KnockoutObservable<boolean>;
        substitutionRemainingNumberCheck: KnockoutObservable<boolean>;

        roundingRules: KnockoutObservableArray<any>;

        constructor() {
            let self = this;
            self.yearDisplayAtr = ko.observable(false);
            self.yearRemainingNumberCheck = ko.observable(false);
            self.savingYearDisplayAtr = ko.observable(false);
            self.savingYearRemainingNumberCheck = ko.observable(false);
            self.compensatoryDisplayAtr = ko.observable(false);
            self.compensatoryRemainingNumberCheck = ko.observable(false);
            self.substitutionDisplayAtr = ko.observable(false);
            self.substitutionRemainingNumberCheck = ko.observable(false);

            self.roundingRules = ko.observableArray([
                { code: true, name: 'する' },
                { code: false, name: 'しない' }
            ]);
        }


        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            self.getDispRestric().done(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        //Get DispRestric
        getDispRestric(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getDispRestric().done(function(data) {
                self.yearDisplayAtr(data.yearDisplayAtr);
                self.yearRemainingNumberCheck(data.yearRemainingNumberCheck);
                self.savingYearDisplayAtr(data.savingYearDisplayAtr);
                self.savingYearRemainingNumberCheck(data.savingYearRemainingNumberCheck);
                self.compensatoryDisplayAtr(data.compensatoryDisplayAtr)
                self.compensatoryRemainingNumberCheck(data.compensatoryRemainingNumberCheck);
                self.substitutionDisplayAtr(data.substitutionDisplayAtr);
                self.substitutionRemainingNumberCheck(data.substitutionRemainingNumberCheck);
                dfd.resolve();
            });
            return dfd.promise();
        }


        saveData() {
            let self = this;
            let dispRestric = {
                yearDisplayAtr: self.yearDisplayAtr(),
                yearRemainingNumberCheck: self.yearRemainingNumberCheck(),
                savingYearDisplayAtr: self.savingYearDisplayAtr(),
                savingYearRemainingNumberCheck: self.savingYearRemainingNumberCheck(),
                compensatoryDisplayAtr: self.compensatoryDisplayAtr(),
                compensatoryRemainingNumberCheck: self.compensatoryRemainingNumberCheck(),
                substitutionDisplayAtr: self.substitutionDisplayAtr(),
                substitutionRemainingNumberCheck: self.substitutionRemainingNumberCheck()
            };
            service.update(dispRestric).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            });
        }
    }

}
