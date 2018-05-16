module nts.uk.at.view.kdm001.l {
    export module viewmodel {
        export class ScreenModel {

            workCode: KnockoutObservable<string> = ko.observable('');
            workPlaceName: KnockoutObservable<string> = ko.observable('');
            employeeCode: KnockoutObservable<string> = ko.observable('');
            employeeName: KnockoutObservable<string> = ko.observable('');
            required: KnockoutObservable<boolean>;
            required1: KnockoutObservable<boolean>;
            date: KnockoutObservable<string> = ko.observable('');
            deadline: KnockoutObservable<string> = ko.observable('');


            // combobox day off
            daysOff: KnockoutObservableArray<DayOff>;
            selectedCodeDayOff: KnockoutObservable<string>;
            isEnableDayOff: KnockoutObservable<boolean>;
            isEditableDayOff: KnockoutObservable<boolean>;

            // combobox day
            days: KnockoutObservableArray<DayOff>;
            selectedCodeDay: KnockoutObservable<string>;
            isEnableDay: KnockoutObservable<boolean>;
            isEditableDay: KnockoutObservable<boolean>;

            checked: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.required = ko.observable(true);
                self.required1 = ko.observable(true);
                self.initScreen();

                self.daysOff = ko.observableArray([
                    new DayOff('1', '基本給'),
                    new DayOff('2', '役職手当'),
                    new DayOff('3', '基本給ながい文字列ながい文字列ながい文字列')
                ]);

                self.days = ko.observableArray([
                    new DayOff('1', '基本給'),
                    new DayOff('2', '役職手当'),
                    new DayOff('3', '基本給ながい文字列ながい文字列ながい文字列')
                ]);


                self.selectedCodeDayOff = ko.observable('1');
                self.isEnableDayOff = ko.observable(true);
                self.isEditableDayOff = ko.observable(true);

                self.selectedCodeDay = ko.observable('1');
                self.isEnableDay = ko.observable(true);
                self.isEditableDay = ko.observable(true);


            }

            public initScreen(): void {
                var self = this;
                self.workCode('100');
                self.workPlaceName('営業部');
                self.employeeCode('A000001');
                self.employeeName('日通　太郎');
                self.date('20160424');
                self.deadline('20160724');
                self.enable = ko.observable(false);
            }


        }

        class DayOff {
            code: string;
            day: string;

            constructor(code: string, day: string) {
                this.code = code;
                this.day = day;
            }
        }

    }
}