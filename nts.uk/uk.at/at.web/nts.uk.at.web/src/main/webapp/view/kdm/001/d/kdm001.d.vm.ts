module nts.uk.at.view.kdm001.d.viewmodel {
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string>      = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string>  = ko.observable('');
        employeeName: KnockoutObservable<string>  = ko.observable('');
        dayRemaining: KnockoutObservable<string>  = ko.observable('');
        pickup: KnockoutObservable<boolean>    = ko.observable(false);;
        pause: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean>      = ko.observable(false);
        dayOff: KnockoutObservable<string>        = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        subDayoffDate: KnockoutObservable<string>     = ko.observable('');
        dateOptionSubHoliday: KnockoutObservable<string>    = ko.observable('');
        dateOffOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListHoliday: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        selectedCodeHoliday: KnockoutObservable<string>              = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeSubHoliday: KnockoutObservable<string>           = ko.observable('');
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeOptionSubHoliday: KnockoutObservable<string>           = ko.observable('');
        isOptionSubHolidayEnable: KnockoutObservable<boolean>              = ko.observable(false);

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            let self = this;
            self.workCode("100");
            self.workplaceName("営業部");
            self.employeeCode("A0000001");
            self.employeeName("日通　太郎");
            self.dayRemaining("0.5日");
            self.checkedSplit.subscribe((v) => {
                if (v == true) {
                    self.isOptionSubHolidayEnable(true);
                } else {
                    self.isOptionSubHolidayEnable(false);
                }
            });
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}