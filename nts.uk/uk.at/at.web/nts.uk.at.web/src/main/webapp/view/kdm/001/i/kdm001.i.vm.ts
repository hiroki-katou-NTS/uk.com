module nts.uk.com.view.kdm001.i.viewmodel {

    export class ScreenModel {
        //Custom code
        workCode: KnockoutObservable<any> = ko.observable('');
        workplaceName: KnockoutObservable<any> = ko.observable('');
        employeeCode: KnockoutObservable<any> = ko.observable('');
        employeeName: KnockoutObservable<any> = ko.observable('');
        dayRemaining: KnockoutObservable<any> = ko.observable('');
        checkedHoliday: KnockoutObservable<boolean> = ko.observable(false);;
        checkedSubHoliday: KnockoutObservable<boolean> = ko.observable(false);
        checkedSplit: KnockoutObservable<boolean> = ko.observable(false);
        date: KnockoutObservable<string> = ko.observable('');
        duedate: KnockoutObservable<string> = ko.observable('');
        dateSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        dateOffOptionSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListHoliday: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedCodeHoliday: KnockoutObservable<string> = ko.observable('');
        itemListSubHoliday: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedCodeSubHoliday: KnockoutObservable<string> = ko.observable('');
        itemListOptionSubHoliday: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
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
            self.date('20000101');
            self.duedate('20000101');
            self.dateSubHoliday('20000101');
            self.dateOptionSubHoliday('20000101');
            self.dateOffOptionSubHoliday('20000101');
            let days: Array<ItemModel> = [
                new ItemModel('1', '0.5日'),
                new ItemModel('2', '1.0日'),
                new ItemModel('3', '1.5日'),
                new ItemModel('4', '2.0日'),
                new ItemModel('5', '2.5日')
            ];
            self.itemListHoliday(days);
            self.selectedCodeHoliday('1');
            self.itemListSubHoliday(days);
            self.selectedCodeSubHoliday('1');
            self.itemListOptionSubHoliday(days);
            self.selectedCodeOptionSubHoliday('1');
        }

        /**
         * closeDialog
         */
        public closeDialog() {
            nts.uk.ui.windows.close();
        }
    }

    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}