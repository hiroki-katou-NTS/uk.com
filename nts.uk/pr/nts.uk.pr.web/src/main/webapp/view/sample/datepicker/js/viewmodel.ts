module sample.datepicker.viewmodel {
    
    export class ScreenModel {
        date: KnockoutObservable<Date>;
        
        /**
         * Constructor.
         */
        constructor() {
            var self = this;            
            self.date = ko.observable(new Date('01/01/2001'));
        }
    }
}