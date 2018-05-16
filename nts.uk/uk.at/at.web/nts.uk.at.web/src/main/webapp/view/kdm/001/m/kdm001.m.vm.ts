module nts.uk.at.view.kdm001.m.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        workCode: KnockoutObservable<string>     = ko.observable('');
        workName: KnockoutObservable<string>     = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        substituteHolidayDate: KnockoutObservable<string>   = ko.observable('');
        holidayTimeList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        holidayTime: KnockoutObservable<string>             = ko.observable('1');
        remainDaysList: KnockoutObservableArray<ItemModel>  = ko.observableArray([]);
        remainDays: KnockoutObservable<string>              = ko.observable('1');

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            let self = this;
            self.workCode('100');
            self.workName('営業部');
            self.employeeCode('A0000001');
            self.employeeName('日通　太郎');
            self.substituteHolidayDate('20160424');
            let days: Array<ItemModel> = [
                new ItemModel('1', '0.5日'),
                new ItemModel('2', '1.0日'),
                new ItemModel('3', '1.5日'),
                new ItemModel('4', '2.0日'),
                new ItemModel('5', '2.5日'),
                new ItemModel('6', '3.0日')
            ];
            self.holidayTimeList(days);
            self.remainDaysList(days);
        }

        closeKDM001M(): void {
            nts.uk.ui.windows.close();
        }
        openKDM001M(): void {
            modal("/view/kdm/001/m/index.xhtml").onClosed(function() { });
        }
    }
    export class ItemModel {
        code: string;
        value: string;
        constructor(code: string, value: string) {
            this.code = code;
            this.value = value;
        }
    }
}