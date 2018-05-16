module nts.uk.com.view.kdm001.i {
    export module viewmodel {
        export class ScreenModel {
            //Custom code
            workCode: KnockoutObservableArray<any> = ko.observable('');
            workplaceName: KnockoutObservableArray<any> = ko.observable('');
            employeeCode: KnockoutObservableArray<any> = ko.observable('');
            employeeName: KnockoutObservableArray<any> = ko.observable('');
            //I15_1
            dayRemaining: KnockoutObservableArray<any> = ko.observable('');
            //CheckBox I4
            checkedHoliday: KnockoutObservable<boolean>;
            //CheckBox I9
            checkedSubHoliday: KnockoutObservable<boolean>;
            //CheckBox I12
            checkedSplit: KnockoutObservable<boolean>;
            //Date Picker
            date: KnockoutObservable<string>;
            //DueDate Picker
            duedate: KnockoutObservable<string>;
            //I11_1 date sub holiday
            dateSubHoliday: KnockoutObservable<string>;
            //I12_2 
            dateOptionSubHoliday: KnockoutObservable<string>;
            //I12_4
            dateOffOptionSubHoliday: KnockoutObservable<string>;
            //Combobox Holiday       
            itemListHoliday: KnockoutObservableArray<ItemModel>;
            selectedCodeHoliday: KnockoutObservable<string>;
            //Combobox SubHoliday
            itemListSubHoliday: KnockoutObservableArray<ItemModel>;
            selectedCodeSubHoliday: KnockoutObservable<string>;
            constructor() {
                var self = this;
                //CustomCode
                self.workCode = "100";
                self.workplaceName = "営業部";
                self.employeeCode = "A0000001";
                self.employeeName = "日通　太郎";
                //I15_1 Value
                self.dayRemaining = "0.5日";
                //CheckBox I4
                self.checkedHoliday = ko.observable(true);
                //CheckBox I9
                self.checkedSubHoliday = ko.observable(true);
                //CheckBox I12 
                self.checkedSplit = ko.observable(false);
                self.checkedSplit.subscribe((v) => {
                    if (v == true) {
                        self.isOptionSubHolidayEnable(true);
                    } else {
                        self.isOptionSubHolidayEnable(false);
                    }
                });
                //Date picker
                self.date = ko.observable('20000101');
                //DueDate picker
                self.duedate = ko.observable('20000101');
                //I11_1
                self.dateSubHoliday = ko.observable('20000101');
                //I12_2 
                self.dateOptionSubHoliday = ko.observable('20000101');
                //I12_4
                self.dateOffOptionSubHoliday = ko.observable('20000101');
                self.itemList = ko.observableArray([
                    new ItemModel('1', '0.5日'),
                    new ItemModel('2', '1.0日'),
                    new ItemModel('3', '1.5日'),
                    new ItemModel('4', '2.0日'),
                    new ItemModel('5', '2.5日')
                ]);
                //ComboBox Holiday
                self.itemListHoliday = ko.observableArray([
                    new ItemModel('1', '0.5日'),
                    new ItemModel('2', '1.0日'),
                    new ItemModel('3', '1.5日'),
                    new ItemModel('4', '2.0日'),
                    new ItemModel('5', '2.5日')
                ]);
                self.selectedCodeHoliday = ko.observable('2');
                //ComboBox SubHoliday
                self.itemListSubHoliday = ko.observableArray([
                    new ItemModel('1', '0.5日'),
                    new ItemModel('2', '1.0日'),
                    new ItemModel('3', '1.5日'),
                    new ItemModel('4', '2.0日'),
                    new ItemModel('5', '2.5日')
                ]);
                self.selectedCodeSubHoliday = ko.observable('1');
                //ComboBox Option SubHoliday
                self.itemListOptionSubHoliday = ko.observableArray([
                    new ItemModel('1', '0.5日'),
                    new ItemModel('2', '1.0日'),
                    new ItemModel('3', '1.5日'),
                    new ItemModel('4', '2.0日'),
                    new ItemModel('5', '2.5日')
                ]);
                self.selectedCodeOptionSubHoliday = ko.observable('5');
                self.isOptionSubHolidayEnable = ko.observable(false);
            }
            /**
             * closeDialog
             */
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
        };
        class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    }
}