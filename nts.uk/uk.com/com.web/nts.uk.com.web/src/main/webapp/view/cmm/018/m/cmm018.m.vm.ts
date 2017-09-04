module nts.uk.at.view.cmm018.m {
    export module viewmodel {
        export class ScreenModel {
            
            //Enable checkbox
            checkValue3: KnockoutObservable<boolean>;
            checkValue2: KnockoutObservable<boolean>;
            checkValue1: KnockoutObservable<boolean>;
            
            date: KnockoutObservable<string>;
            constructor() {
                var self = this;
                
                //Enable checkbox
                self.checkValue3 = ko.observable(true);
                self.checkValue2 = ko.observable(true);
                self.checkValue1 = ko.observable(true);
                            
                //self.date = ko.observable('20000101');
                var currentDate = (new Date()).toISOString().split('T')[0];
                self.date = ko.observable(currentDate);
            }
        }
    }
}
