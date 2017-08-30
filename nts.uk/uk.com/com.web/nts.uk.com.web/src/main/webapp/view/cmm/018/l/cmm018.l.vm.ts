module nts.uk.at.view.cmm018.l {
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<string>;
            yearMonth: KnockoutObservable<number>;
            constructor() {
                var self = this;            
                self.date = ko.observable('20000101');
                self.yearMonth = ko.observable(200001);
            }
        }
    }
}
