module nts.uk.at.view.kdm001.h.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
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
        cId: KnockoutObservable<string> = ko.observable('');
        sID: KnockoutObservable<string> = ko.observable('');
        holidayDate: KnockoutObservable<string> = ko.observable('');
        requiredDays: KnockoutObservable<string> = ko.observable('');
        dayoffDate: KnockoutObservable<string> = ko.observable('');
        expirationDate: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<string> = ko.observable('');
        employeeId: KnockoutObservable<string> = ko.observable('');
        closureId: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            let self = this,
                info = getShared("KDM001_EFGH_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);

                self.closureId(info.closureId);

                self.subOfHDID(info.rowValue.id);
                self.dayoffDate(info.rowValue.dayoffDateSub);
                self.requiredDays(info.rowValue.requiredDays);
                self.remainDays(info.rowValue.remainDays);
                self.expirationDate(info.rowValue.expiredDate);
                self.unknownDate(info.rowValue.unknownDateSub);
            }
            block.clear();
        }

        closeKDM001H(): void {
            nts.uk.ui.windows.close();
        }

        public updateData() {
            nts.uk.ui.errors.clearAll();
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let self = this;
                let data = {
                    closureId: self.closureId(),
                    subOfHDID: self.subOfHDID(),
                    employeeId: self.employeeId(),
                    requiredDays: self.requiredDays(),
                    dayoffDate: self.dayoffDate(),
                    unknownDate: self.unknownDate(),
                    remainDays: self.remainDays(),
                    expirationDate: self.expirationDate()
                };
                console.log(data);
                service.updatesubOfHD(data).done(result => {
                    if (result && result.length > 0) {
                        for (let messageId of result) {
                            switch (messageId) {
                                case "Msg_744": {
                                    $('#L6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_729": {
                                    $('#L6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                            }
                        }
                        block.clear();
                        return;
                    }
                    
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isChanged: true });
                        nts.uk.ui.windows.close();
                    });
                }).always(() => {
                    block.clear();
                });
            }

        }

        public removeData() {
            block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this;
                let data = {
                    employeeId: self.employeeId(),
                    dayoffDate: self.dayoffDate()
                };
                console.log(data);
                service.removeSubOfHD(data).done(result => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isChanged: true });
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(error: any) {
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