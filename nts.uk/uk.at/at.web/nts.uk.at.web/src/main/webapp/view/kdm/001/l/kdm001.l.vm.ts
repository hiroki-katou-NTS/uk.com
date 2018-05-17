module nts.uk.at.view.kdm001.l.viewmodel {
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string>      = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string>  = ko.observable('');
        employeeName: KnockoutObservable<string>  = ko.observable('');
        date: KnockoutObservable<string>          = ko.observable('');
        deadline: KnockoutObservable<string>      = ko.observable('');
        daysOff: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeDayOff: KnockoutObservable<string>    = ko.observable('');
        days: KnockoutObservableArray<model.ItemModel>    = ko.observableArray(model.getNumberOfDays());
        selectedCodeDay: KnockoutObservable<string>       = ko.observable('');
        checkedExpired: KnockoutObservable<boolean>       = ko.observable(false);

        constructor() {
            var self = this;
            self.initScreen();
        }

        public initScreen(): void {
            var self = this;
            self.workCode('100');
            self.workPlaceName('営業部');
            self.employeeCode('A000001');
            self.employeeName('日通　太郎');
            self.date('20160424');
            self.deadline('20160724');
        }

        /**
        * closeDialog
        */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}