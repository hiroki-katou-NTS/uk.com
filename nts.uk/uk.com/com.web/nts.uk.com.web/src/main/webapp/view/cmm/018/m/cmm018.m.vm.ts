module nts.uk.at.view.cmm018.m {
    export module viewmodel {
        export class ScreenModel {
            
            //Enable checkbox
            enable3: KnockoutObservable<boolean>;
            enable2: KnockoutObservable<boolean>;
            enable1: KnockoutObservable<boolean>;
            
            date: KnockoutObservable<string>;
            yearMonth: KnockoutObservable<number>;
            constructor() {
                var self = this;
                
                //Enable checkbox
                self.enable3 = ko.observable(true);
                self.enable2 = ko.observable(true);
                self.enable1 = ko.observable(true);
                            
                self.date = ko.observable('20000101');
                self.yearMonth = ko.observable(200001);
            }
        }
    }
}
