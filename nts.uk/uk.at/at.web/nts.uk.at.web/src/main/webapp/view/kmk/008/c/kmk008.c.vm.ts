module nts.uk.at.view.kmk008.c {
    export module viewmodel {
        export class ScreenModel {
            timeOfCompany: KnockoutObservable<TimeOfCompanyModel>;
            constructor() {
                let self = this;
                self.timeOfCompany = ko.observable(new TimeOfCompanyModel(null));
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

        }

         export class TimeOfCompanyModel {
            alarmWeek: KnockoutObservable<string> = ko.observable(null);
            errorWeek: KnockoutObservable<string> = ko.observable(null);
            limitWeek: KnockoutObservable<string> = ko.observable(null);
            alarmTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            errorTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            limitTwoWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmFourWeeks: KnockoutObservable<string> = ko.observable(null);
            errorFourWeeks: KnockoutObservable<string> = ko.observable(null);
            limitFourWeeks: KnockoutObservable<string> = ko.observable(null);
            alarmOneMonth: KnockoutObservable<string> = ko.observable(null);
            errorOneMonth: KnockoutObservable<string> = ko.observable(null);
            limitOneMonth: KnockoutObservable<string> = ko.observable(null);
            alarmTwoMonths: KnockoutObservable<string> = ko.observable(null);
            errorTwoMonths: KnockoutObservable<string> = ko.observable(null);
            limitTwoMonths: KnockoutObservable<string> = ko.observable(null);
            alarmThreeMonths: KnockoutObservable<string> = ko.observable(null);
            errorThreeMonths: KnockoutObservable<string> = ko.observable(null);
            limitThreeMonths: KnockoutObservable<string> = ko.observable(null);
            alarmOneYear: KnockoutObservable<string> = ko.observable(null);
            errorOneYear: KnockoutObservable<string> = ko.observable(null);
            limitOneYear: KnockoutObservable<string> = ko.observable(null);
            constructor(data: any) {
               let self = this;
               if(!data) return;
               self.alarmWeek(data.alarmWeek);
               self.errorWeek(data.errorWeek);
               self.limitWeek(data.limitWeek);
               self.alarmTwoWeeks(data.alarmTwoWeeks);
               self.errorTwoWeeks(data.errorTwoWeeks);
               self.limitTwoWeeks(data.limitTwoWeeks);
               self.alarmFourWeeks(data.alarmFourWeeks);
               self.errorFourWeeks(data.errorFourWeeks);
               self.limitFourWeeks(data.limitFourWeeks);
               self.alarmOneMonth(data.alarmOneMonth);
               self.errorOneMonth(data.errorOneMonth);
               self.limitOneMonth(data.limitOneMonth);
               self.alarmTwoMonths(data.alarmTwoMonths);
               self.errorTwoMonths(data.errorTwoMonths);
               self.limitTwoMonths(data.limitTwoMonths);
               self.alarmThreeMonths(data.alarmThreeMonths);
               self.errorThreeMonths(data.errorThreeMonths);
               self.limitThreeMonths(data.limitThreeMonths);
               self.alarmOneYear(data.alarmOneYear);
               self.errorOneYear(data.errorOneYear);
               self.limitOneYear(data.limitOneYear);
            }
        }
        
        export class UpdateInsertTimeOfCompanyModel {
            alarmWeek: number = 0;
            errorWeek: number = 0;
            limitWeek: number = 0;
            alarmTwoWeeks: number = 0;
            errorTwoWeeks: number = 0;
            limitTwoWeeks: number = 0;
            alarmFourWeeks: number = 0;
            errorFourWeeks: number = 0;
            limitFourWeeks: number = 0;
            alarmOneMonth: number = 0;
            errorOneMonth: number = 0;
            limitOneMonth: number = 0;
            alarmTwoMonths: number = 0;
            errorTwoMonths: number = 0;
            limitTwoMonths: number = 0;
            alarmThreeMonths: number = 0;
            errorThreeMonths: number = 0;
            limitThreeMonths: number = 0;
            alarmOneYear: number = 0;
            errorOneYear: number = 0;
            limitOneYear: number = 0;
            constructor(data: TimeOfCompanyModel) {
               let self = this;
               if(!data) return;
               self.alarmWeek = data.alarmWeek();
               self.errorWeek = data.errorWeek();
               self.limitWeek = data.limitWeek();
               self.alarmTwoWeeks = data.alarmTwoWeeks();
               self.errorTwoWeeks = data.errorTwoWeeks();
               self.limitTwoWeeks = data.limitTwoWeeks();
               self.alarmFourWeeks = data.alarmFourWeeks();
               self.errorFourWeeks = data.errorFourWeeks();
               self.limitFourWeeks = data.limitFourWeeks();
               self.alarmOneMonth = data.alarmOneMonth();
               self.errorOneMonth = data.errorOneMonth();
               self.limitOneMonth = data.limitOneMonth();
               self.alarmTwoMonths = data.alarmTwoMonths();
               self.errorTwoMonths = data.errorTwoMonths();
               self.limitTwoMonths = data.limitTwoMonths();
               self.alarmThreeMonths = data.alarmThreeMonths();
               self.errorThreeMonths = data.errorThreeMonths();
               self.limitThreeMonths = data.limitThreeMonths();
               self.alarmOneYear = data.alarmOneYear();
               self.errorOneYear = data.errorOneYear();
               self.limitOneYear = data.limitOneYear();
            }
        }
    }
}
