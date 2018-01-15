module nts.uk.at.view.ksu001.ntsExCalendar.viewmodel {
    export class ScreenModel {
        startDate: KnockoutObservable<Date> = ko.observable(new Date('2017/06/01'));
        endDate: KnockoutObservable<Date> = ko.observable(new Date('2017/06/30'));
        selectedIds: KnockoutObservableArray<any> = ko.observableArray([]);
        dayIsChecked: KnockoutObservable<any> = ko.observable();
        constructor() {
            let self = this;
        }

        result(): void {
            let self = this;
            self.dayIsChecked(self.selectedIds().length == 0 ? 'NO DAY IS CHECKED!' : self.selectedIds());
        }
    }
}