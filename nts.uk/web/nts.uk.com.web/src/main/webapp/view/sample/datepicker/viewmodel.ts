module sample.datepicker.viewmodel {
    
    export class ScreenModel {
        date: KnockoutObservable<Date>;
        yearMonth: any;
        constructor() {
            var self = this;            
            self.date = ko.observable(new Date('2016/12/01'));
            self.yearMonth = ko.observable('2016/12');
        }
    }
}