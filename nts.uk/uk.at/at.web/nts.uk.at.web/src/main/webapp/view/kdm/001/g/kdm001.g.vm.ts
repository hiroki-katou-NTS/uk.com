module nts.uk.at.view.kdm001.g.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import model = kdm001.share.model;
    export class ScreenModel {
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        date: KnockoutObservable<string> = ko.observable('');
        deadline: KnockoutObservable<string> = ko.observable('');
        daysOff: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeDayOff: KnockoutObservable<string> = ko.observable('');
        days: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        selectedCodeDay: KnockoutObservable<string> = ko.observable('');
        checkedExpired: KnockoutObservable<boolean> = ko.observable(false);

        employeeId: KnockoutObservable<string> = ko.observable('');
        payoutId: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        sID: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<string> = ko.observable('');
        dayoffDate: KnockoutObservable<string> = ko.observable('');
        expiredDate: KnockoutObservable<string> = ko.observable('');
        lawAtr: KnockoutObservable<number> = ko.observable('');
        occurredDays: KnockoutObservable<number> = ko.observable('');
        unUsedDays: KnockoutObservable<number> = ko.observable('');
        stateAtr: KnockoutObservable<number> = ko.observable('');
        disapearDate: KnockoutObservable<string> = ko.observable('');
        checkBox: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.initScreen();
        }

        public initScreen(): void {
            let self = this,
                info = getShared("KDM001_EFGH_PARAMS");
            if (info) {

                self.workCode(info.selectedEmployee.workplaceId);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);

                //self.employ()eeId(info.selectedEmployee.sID);
                self.payoutId(info.rowValue.id);
                self.dayoffDate(info.rowValue.dayoffDatePyout);
                self.occurredDays(info.rowValue.occurredDays);
                self.lawAtr(info.rowValue.lawAtr);
                self.expiredDate(info.rowValue.expiredDate);
                self.unUsedDays(info.rowValue.unUsedDays);
                self.unknownDate(info.rowValue.unknownDatePayout);
            }
            block.clear();
        }


        public updateData() {
            nts.uk.ui.errors.clearAll();
            if (!nts.uk.ui.errors.hasError()) {
                let self = this;
                let data = {
                    payoutId:self.payoutId(),
                    employeeId: self.employeeId(),
                    unknownDate: self.unknownDate(),
                    dayoffDate: self.dayoffDate(),
                    expiredDate: moment.utc(self.expiredDate(), 'YYYY/MM/DD').toISOString(),
//                    lawAtr: self.lawAtr().parsedInteger(),
                    lawAtr: 1,
//                    occurredDays: self.occurredDays(),
                    occurredDays: 1,
                    unUsedDays: self.unUsedDays(),
                    checkBox: self.checkBox()
                };

                console.log(data);
                service.updatePayout(data).done(result => {
                    if (result.length > 0) {
                        $('#G6_2').ntsError('set', { messageId: result[0] });
                        block.clear();
                        return;
                    }
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', {isSuccess: true});
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
                service.removePayout(data).done(() => {
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


        /**
        * closeDialog
        */
        public closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}