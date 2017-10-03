module nts.uk.at.view.kdw006.c {
    export module viewmodel {
        export class ScreenModel {

            //Define DispRestric Knockout Object view in screen
            dispRestric: KnockoutObservable<DispRestric> = ko.observable(new DispRestric());

            constructor() {
                let self = this;
            }

            saveData() {
                var self = this;
                var dispRestric = {
                    yearHolidaySwitch: self.dispRestric().yearHolidaySwitch(),
                    yearHolidayCheck: self.dispRestric().yearHolidayCheck(),
                    substitutionSwitch: self.dispRestric().substitutionSwitch(),
                    substitutionCheck: self.dispRestric().substitutionCheck(),
                    savingYearSwitch: self.dispRestric().savingYearSwitch(),
                    savingYearCheck: self.dispRestric().savingYearCheck(),
                    compensatorySwitch: self.dispRestric().compensatorySwitch(),
                    compensatoryCheck: self.dispRestric().compensatoryCheck()
                };

                service.update(dispRestric).done(function(data) {
                    self.getDispRestric();
                });

            }

            //Get DispRestric
            getDispRestric() {
                let self = this;
                var dfd = $.Deferred();

                service.getDispRestric().done(function(data) {
                    self.dispRestric(new DispRestric(data));

                    dfd.resolve();
                });
                return dfd.promise();
            }

            start(): JQueryPromise<any> {
                let self = this;

                //When start screen, get DispRestric
                //self.getDispRestric();

                //Start init screen model
                var x = {
                    yearHolidaySwitch: 1,
                    yearHolidayCheck: true,
                    substitutionSwitch: 1,
                    substitutionCheck: true,
                    savingYearSwitch: 1,
                    savingYearCheck: true,
                    compensatorySwitch: 1,
                    compensatoryCheck: true
                };
                self.dispRestric(new DispRestric(x));
                //End init screen model

                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }

        export class DispRestric {
            yearHolidaySwitch: KnockoutObservable<number> = ko.observable(1);
            yearHolidayCheck: KnockoutObservable<boolean> = ko.observable(true);
            substitutionSwitch: KnockoutObservable<number> = ko.observable(1);
            substitutionCheck: KnockoutObservable<boolean> = ko.observable(false);
            savingYearSwitch: KnockoutObservable<number> = ko.observable(1);
            savingYearCheck: KnockoutObservable<boolean> = ko.observable(false);
            compensatorySwitch: KnockoutObservable<number> = ko.observable(1);
            compensatoryCheck: KnockoutObservable<boolean> = ko.observable(false);

            constructor(x: any) {
                let self = this;
                if (x) {
                    self.yearHolidaySwitch = ko.observable(x.yearHolidaySwitch);
                    self.yearHolidayCheck = ko.observable(x.yearHolidayCheck);
                    self.substitutionSwitch = ko.observable(x.substitutionSwitch);
                    self.substitutionCheck = ko.observable(x.substitutionCheck);
                    self.savingYearSwitch = ko.observable(x.savingYearSwitch);
                    self.savingYearCheck = ko.observable(x.savingYearCheck);
                    self.compensatorySwitch = ko.observable(x.compensatorySwitch);
                    self.compensatoryCheck = ko.observable(x.compensatoryCheck);
                }
            }
        }
    }
}
