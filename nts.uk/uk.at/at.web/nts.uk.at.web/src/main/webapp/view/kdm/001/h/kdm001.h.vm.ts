module nts.uk.at.view.kdm001.h.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import model = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
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
        employeeId: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            let self = this,
            info = getShared("KDM001_EFGH_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceId);
                self.workName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);

                
                self.employeeId(info.selectedEmployee.sID);
                self.dayOff(info.rowValue.dayOffDate);
                self.requiredDays(info.rowValue.requiredDays);
                self.remainDays(info.rowValue.remainDays);
                self.expirationDate(info.rowValue.expiredDate);
            }
            block.clear();
        }

        closeKDM001H(): void {
            nts.uk.ui.windows.close();
        }

        public updateData() {
            let self = this;
            let data = {
                employeeId: self.employeeId(),
                requiredDays: self.requiredDays(),
                dayOff:   self.dayOff(),
                unknownDate:  false,
                remainDays: self.remainDays(),
                expirationDate: self.expirationDate()
            };
            console.log(data);
            service.updatesubOfHD(data).done(() => {
                console.log("Success update!");
            }).fail(function(res: any) {
                console.log("fail update!!");
            });
        }

        public removeData() {
            block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this;
                let data = {
                    employeeId: self.employeeId(),
                    dayOff: self.dayOff()
                };
                console.log(data);
                service.removeSubOfHD(data).done(() => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                }).fail(error => {
                    dialog.alertError(error);
                }).always(function() {
                    block.clear();
                });
            }).then(() => {
                block.clear();
            });




        }

        openKDM001H(): void {
            modal("/view/kdm/001/h/index.xhtml").onClosed(function() { });
        }
    }





}