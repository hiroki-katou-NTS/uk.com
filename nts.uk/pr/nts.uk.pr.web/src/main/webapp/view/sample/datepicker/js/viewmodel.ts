module sample.datepicker.viewmodel {
    
    export class ScreenModel {
        date: KnockoutObservable<Date>;
        formatDate: KnockoutComputed<any>;
        /**
         * Constructor.
         */
        constructor() {
            var self = this;            
            self.date = ko.observable(new Date('2016/01/01'));
            self.formatDate = ko.computed(function() {
                return nts.uk.time.formatDate(self.date(),'yyyy/MM/dd');
            });
        }
    }
}