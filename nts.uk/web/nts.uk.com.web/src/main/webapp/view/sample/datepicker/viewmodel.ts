module sample.datepicker.viewmodel {  
    export class ScreenModel {
        dateString: KnockoutObservable<string>;
        date: KnockoutObservable<Date>;
        yearMonth: KnockoutObservable<number>;
        constructor() {
            var self = this;            
            self.dateString = ko.observable('20000101');
            
            // Un-comment to see diffirent between Date and UTC Date 
            //self.date = ko.observable(new Date(2000,1,2));
            self.date = ko.observable(new Date(Date.UTC(2000,0,1)));
            self.yearMonth = ko.observable(200001);
        }
    }
}