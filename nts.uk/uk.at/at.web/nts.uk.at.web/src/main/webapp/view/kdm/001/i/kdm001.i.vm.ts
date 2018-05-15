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
            enable: KnockoutObservable<boolean>;

            //CheckBox I9
            checkedSubHoliday: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;

            //CheckBox I12
            checkedSplit: KnockoutObservable<boolean>;

            //Date Picker
            date: KnockoutObservable<string>;
            yearMonth: KnockoutObservable<number>;

            //DueDate Picker
            duedate: KnockoutObservable<string>;
            dueyearMonth: KnockoutObservable<number>;

            //I11_1 date sub holiday
            dateSubHoliday: KnockoutObservable<string>;
            yearMonthSubHoliday: KnockoutObservable<number>;

            //I12_2 
            dateOptionSubHoliday: KnockoutObservable<string>;
            yearOptionSubHoliday: KnockoutObservable<number>;

            //I12_4
            dateOffOptionSubHoliday: KnockoutObservable<string>;
            yearOffOptionSubHoliday: KnockoutObservable<number>;

            //Combobox Holiday
            itemListHoliday: KnockoutObservableArray<ItemModel>;
            selectedCodeHoliday: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

            //Combobox SubHoliday
            itemListSubHoliday: KnockoutObservableArray<ItemModel>;
            selectedCodeSubHoliday: KnockoutObservable<string>;
            isEnable: KnockoutObservable<boolean>;
            isEditable: KnockoutObservable<boolean>;

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
                self.enable = ko.observable(true);

                //CheckBox I9
                self.checkedSubHoliday = ko.observable(true);
                self.enable = ko.observable(true);

                //CheckBox I12 
                self.checkedSplit = ko.observable(false);
                self.enable = ko.observable(true);
                
                self.checkedSplit.subscribe((v) => {
                    if (v == true) {
                       self.isOptionSubHolidayEnable(true);
                    } else {
                       self.isOptionSubHolidayEnable(false);
                    }
                });

                //Date picker
                self.date = ko.observable('20000101');
                self.yearMonth = ko.observable(200001);

                //DueDate picker
                self.duedate = ko.observable('20000101');
                self.dueyearMonth = ko.observable(200001);

                //I11_1
                self.dateSubHoliday = ko.observable('20000101');
                self.yearMonthSubHoliday = ko.observable(200001);

                //I12_2 
                self.dateOptionSubHoliday = ko.observable('20000101');
                self.yearOptionSubHoliday = ko.observable(200001);

                //I12_4
                self.dateOffOptionSubHoliday = ko.observable('20000101');
                self.yearOffOptionSubHoliday = ko.observable(200001);

                //ComboBox Holiday
                self.itemListHoliday = ko.observableArray([
                    new ItemModelHoliday('1', '0.5日'),
                    new ItemModelHoliday('2', '1.0日'),
                    new ItemModelHoliday('3', '1.5日'),
                    new ItemModelHoliday('4', '2.0日'),
                    new ItemModelHoliday('5', '2.5日')
                ]);

                self.selectedCodeHoliday = ko.observable('2');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);

                //ComboBox SubHoliday
                self.itemListSubHoliday = ko.observableArray([
                    new ItemModelSubHoliday('1', '0.5日'),
                    new ItemModelSubHoliday('2', '1.0日'),
                    new ItemModelSubHoliday('3', '1.5日'),
                    new ItemModelSubHoliday('4', '2.0日'),
                    new ItemModelSubHoliday('5', '2.5日')
                ]);

                self.selectedCodeSubHoliday = ko.observable('1');
                self.isEnable = ko.observable(true);
                self.isEditable = ko.observable(true);

                //ComboBox Option SubHoliday
                self.itemListOptionSubHoliday = ko.observableArray([
                    new ItemModelOptionSubHoliday('1', '0.5日'),
                    new ItemModelOptionSubHoliday('2', '1.0日'),
                    new ItemModelOptionSubHoliday('3', '1.5日'),
                    new ItemModelOptionSubHoliday('4', '2.0日'),
                    new ItemModelOptionSubHoliday('5', '2.5日')
                ]);

                self.selectedCodeOptionSubHoliday = ko.observable('5');
                self.isOptionSubHolidayEnable = ko.observable(false);
                self.isEditable = ko.observable(true);
            }

            setDefault() {
                var self = this;
                nts.uk.util.value.reset($("#combo-box-holiday, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
                nts.uk.util.value.reset($("#combo-box-sub-holiday, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
                nts.uk.util.value.reset($("#combo-box-option-sub-holiday, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
            }

            /**
             * request to create creation screen
             */
            importScreen(): void {
                let self = this;
                nts.uk.request.jump("/view/kdm/001/i/index.xhtml");
            }

            /**
             * closeDialog
             */
            public closeDialog() {
                nts.uk.ui.windows.close();
            }
        };

        class ItemModelHoliday {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        class ItemModelSubHoliday {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        class ItemModelOptionSubHoliday {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}