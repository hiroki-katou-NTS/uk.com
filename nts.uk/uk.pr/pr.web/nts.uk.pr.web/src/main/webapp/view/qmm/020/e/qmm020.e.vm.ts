module nts.uk.pr.view.qmm020.e.viewmodel {
    export  class ScreenModel {
        date: KnockoutObservable<string>;
        yearMonth: KnockoutObservable<number>;
        constructor() {
            var self = this;
            self.date = ko.observable('20000101');
            self.yearMonth = ko.observable(200001);
        }
    }
}