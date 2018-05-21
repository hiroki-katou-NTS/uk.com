module nts.uk.at.view.kdm001.i.viewmodel {
    import model = kdm001.share.model;
    export class ScreenModel {
        employeeId: KnockoutObservable<string> = ko.observable('');
        workCode: KnockoutObservable<string> = ko.observable('');
        workplaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dayRemaining: KnockoutObservable<string> = ko.observable('');
        checkedHoliday: KnockoutObservable<boolean> = ko.observable(false);;
        checkedSubHoliday: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean> = ko.observable(false);
        dateHoliday: KnockoutObservable<string> = ko.observable('');
        duedateHoliday: KnockoutObservable<string> = ko.observable('');
        dateSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOffOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeHoliday: KnockoutObservable<string> = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListOptionSubHoliday: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        isOptionSubHolidayEnable: KnockoutObservable<boolean> = ko.observable(false);

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
        public submitData() {
            let self = this;
            let data = {
                employeeId: "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
                checkedHoliday: self.checkedHoliday(),
                dateHoliday: moment.utc(self.dateHoliday(), 'YYYY/MM/DD').toISOString(),
                selectedCodeHoliday: self.selectedCodeHoliday(),
                duedateHoliday: moment.utc(self.duedateHoliday(), 'YYYY/MM/DD').toISOString(),
                checkedSubHoliday: self.checkedSubHoliday(),
                dateSubHoliday: moment.utc(self.dateSubHoliday(), 'YYYY/MM/DD').toISOString(),
                selectedCodeSubHoliday: self.selectedCodeSubHoliday(),
                checkedSplit: self.checkedSplit(),
                dateOptionSubHoliday: moment.utc(self.dateOptionSubHoliday(), 'YYYY/MM/DD').toISOString(),
                selectedCodeOptionSubHoliday: self.selectedCodeOptionSubHoliday(),
                dayRemaining: self.dayRemaining()
            };

            console.log(data);
            service.add(data).done(() => {

            }).fail(function(res: any) {

            });
        }

        public closeDialog() {
            nts.uk.ui.windows.close();
        }
    }


}

