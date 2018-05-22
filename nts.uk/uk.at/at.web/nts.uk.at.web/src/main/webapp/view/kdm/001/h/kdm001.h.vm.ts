module nts.uk.at.view.kdm001.h.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string> = ko.observable('');
        workName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        substituteHolidayDate: KnockoutObservable<string> = ko.observable('');
        holidayTimeList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        holidayTime: KnockoutObservable<string> = ko.observable('');
        remainDaysList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getremainDay());
        remainDays: KnockoutObservable<string> = ko.observable('');
        itemRequireDay: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRequireDay());
        subOfHDID: KnockoutObservable<string> = ko.observable('');
        cid: KnockoutObservable<string> = ko.observable('');
        sID: KnockoutObservable<string> = ko.observable('');
        holidayDate: KnockoutObservable<string> = ko.observable('');
        requiredDays: KnockoutObservable<string> = ko.observable('');
        dayOff: KnockoutObservable<string> = ko.observable('');
        expirationDate: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<string> = ko.observable('');


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

        closeKDM001H(): void {
            nts.uk.ui.windows.close();
        }

        public updateData() {
            let self = this;
            let data = {
                employeeId: "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
                holidayDate: moment.utc(self.holidayDate(), 'YYYY/MM/DD').toISOString(),
                requiredDays: self.requiredDays(),
                dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString(),
                expirationDate: moment.utc(self.expirationDate(), 'YYYY/MM/DD').toISOString(),
                unknownDate: moment.utc(self.unknownDate(), 'YYYY/MM/DD').toISOString(),
                remainDays: self.remainDays()
            };
            console.log(data);
            service.updatesubOfHD(data).done(() => {
                console.log("Success update!");
            }).fail(function(res: any) {
                console.log("fail update!!");
            });
        }

        public removeData() {
            let self = this;
            let data = {
                employeeId: "ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570",
                sID: "456",
                dayOff: moment.utc(self.dayOff(), 'YYYY/MM/DD').toISOString()
            };
            console.log(data);
            service.removeSubOfHD(data).done(() => {
                console.log("Success delete!");
            }).fail(function(res: any) {
                console.log("Fail delete!");
            });
        }

        openKDM001H(): void {
            modal("/view/kdm/001/h/index.xhtml").onClosed(function() { });
        }
    }





}