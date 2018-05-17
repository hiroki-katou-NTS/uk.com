module nts.uk.at.view.kdm001.m.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string>     = ko.observable('');
        workName: KnockoutObservable<string>     = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        substituteHolidayDate: KnockoutObservable<string>         = ko.observable('');
        holidayTimeList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        holidayTime: KnockoutObservable<string>                   = ko.observable('');
        remainDaysList: KnockoutObservableArray<model.ItemModel>  = ko.observableArray(model.getNumberOfDays());
        remainDays: KnockoutObservable<string>                    = ko.observable('');

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
        }

        closeKDM001M(): void {
            nts.uk.ui.windows.close();
        }
        openKDM001M(): void {
            modal("/view/kdm/001/m/index.xhtml").onClosed(function() { });
        }
    }
}