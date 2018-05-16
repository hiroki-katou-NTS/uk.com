module nts.uk.at.view.kdm001.m.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {
        workCode: KnockoutObservable<string> = ko.observable('');
        workName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        substituteHolidayDate: KnockoutObservable<string> = ko.observable('');
        holidayTimeList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        holidayTime: KnockoutObservable<string> = ko.observable('1');
        remainDaysList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        remainDays: KnockoutObservable<string> = ko.observable('1');
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
                { code: '1', name: '0.5日' },
                { code: '2', name: '1.0日' },
                { code: '3', name: '1.5日' },
                { code: '4', name: '2.0日' },
                { code: '5', name: '2.5日' },
                { code: '6', name: '3.0日' }
            ];
            self.remainDaysList(days);
            self.holidayTimeList(days);
        }

        closeKDM001M(): void {
            close();
        }
        openKDM001M(): void {
            modal("/view/kdm/001/m/index.xhtml").onClosed(function() { });
        }
    }
    export class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}