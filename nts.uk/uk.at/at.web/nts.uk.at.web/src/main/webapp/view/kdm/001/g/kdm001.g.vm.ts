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
        lawAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getTypeHoliday());
        
        

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
        closureId: KnockoutObservable<string> = ko.observable('');

        constructor() {
            let self = this;
            self.initScreen();
        }

        public initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("KDM001_EFGH_PARAMS");
            if (info) {

                self.workCode(info.selectedEmployee.workplaceCode);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);

                self.closureId(info.closureId);
                self.payoutId(info.rowValue.id);
                self.dayoffDate(info.rowValue.dayoffDatePyout);
                self.occurredDays(info.rowValue.occurredDays);
                self.lawAtr(info.rowValue.lawAtr);
                self.expiredDate(info.rowValue.expiredDate);
                self.unUsedDays(info.rowValue.unUsedDays);
                self.unknownDate(info.rowValue.unknownDatePayout);
//                self.checkBox();
            }
            block.clear();
        }


        public updateData() {
            nts.uk.ui.errors.clearAll();
            if (!nts.uk.ui.errors.hasError()) {
                 block.invisible();
                let self = this;
                let data = {
                    closureId: self.closureId(),
                    payoutId: self.payoutId(),
                    employeeId: self.employeeId(),
                    unknownDate: self.unknownDate(),
                    dayoffDate: self.dayoffDate(),
                    expiredDate: self.expiredDate(), 
                    lawAtr: parseInt(self.lawAtr()),
                    occurredDays: parseInt(self.occurredDays()),
                    unUsedDays: self.unUsedDays(),
                    checkBox: self.checkBox()
                };
                service.updatePayout(data).done(result => {
                    if (result && result.length > 0) {
                        for (let messageId of result) {
                            switch (messageId) {
                                case "Msg_740": {
                                    $('#G6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_825": {
                                    $('#G6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_1212": {
                                    $('#G6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_1213": {
                                    $('#G6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                            }
                        }
                        setShared('KDM001_A_PARAMS', { isSuccess: false });
                        block.clear();
                        return;
                    }
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isSuccess: true });
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
                    payoutId: self.payoutId(),
                    employeeId: self.employeeId(),
                    dayoffDate: self.dayoffDate()
                };
                console.log(data);
                service.removePayout(data).done(() => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isSuccess: true });
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({messageId: res.messageId});
                    setShared('KDM001_A_PARAMS', { isSuccess: false });
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
            setShared('KDM001_A_PARAMS', { isSuccess: false });
            nts.uk.ui.windows.close();
        }
    }
}