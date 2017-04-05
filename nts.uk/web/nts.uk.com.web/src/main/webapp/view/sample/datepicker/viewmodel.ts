module sample.datepicker.viewmodel {  
    export class ScreenModel {
        date: KnockoutObservable<string>;
        date1: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<string>;
        year: KnockoutObservable<string>;
        month: KnockoutObservable<string>;
        day: KnockoutObservable<string>;
        constructor() {
            var self = this;            
            self.date = ko.observable('');
            self.date1 = ko.observable('20161201');
            self.yearMonth = ko.observable('201601');
            self.year = ko.observable('2016');
            self.month = ko.observable('01');
            self.day = ko.observable('01');
        }
    }
}