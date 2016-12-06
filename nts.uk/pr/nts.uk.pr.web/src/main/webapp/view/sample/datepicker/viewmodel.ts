module sample.datepicker.viewmodel {
    
    export class ScreenModel {
        date: KnockoutObservable<Date>;

        constructor() {
            var self = this;            
            self.date = ko.observable(new Date('2016/01/02'));
        }
    }
}