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
        lawAtrList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getLawAtr());
        occurredDaysList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getOccurredDays());


        employeeId: KnockoutObservable<string> = ko.observable('');
        payoutId: KnockoutObservable<string> = ko.observable('');
        cID: KnockoutObservable<string> = ko.observable('');
        sID: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<string> = ko.observable('');
        dayoffDate: KnockoutObservable<string> = ko.observable('');
        expiredDate: KnockoutObservable<string> = ko.observable('');
        lawAtr: KnockoutObservable<string> = ko.observable('0');
        occurredDays: KnockoutObservable<string> = ko.observable('0');
        unUsedDays: KnockoutObservable<number> = ko.observable(0);
        stateAtr: KnockoutObservable<number> = ko.observable(0);
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
                self.stateAtr(info.rowValue.stateAtr),
                    self.checkBox(info.rowValue.stateAtr == 2);

            }
            block.clear();

        }


        public updateData() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("#G6_4").trigger("validate");
            $("#G7_2").trigger("validate");
            $("#G9_2").trigger("validate");
            $("#G8_2").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                if (self.checkBox()) {
                    if (self.unknownDate() == "false") {
                        dialog.confirm({ messageId: "Msg_1302" }).ifYes(() => {
                            self.registData();
                        }).ifCancel(() => {
                            block.clear();
                            return;
                        });
                    } else {
                        self.registData();
                    }
                } else {
                    self.registData();
                }

            }
        }

        registData() {
            let self = this;
            let data = {
                closureId: self.closureId(),
                payoutId: self.payoutId(),
                employeeId: self.employeeId(),
                unknownDate: self.unknownDate(),
                dayoffDate: moment.utc(self.dayoffDate(), 'YYYY/MM/DD').toISOString(),
                expiredDate: self.expiredDate(),
                lawAtr: parseInt(self.lawAtr()),
                occurredDays: parseFloat(self.occurredDays()),
                unUsedDays: self.unUsedDays(),
                checkBox: self.checkBox(),
                stateAtr: self.stateAtr()
            };
            service.updatePayout(data).done((result) => {
                if (result && result.length > 0) {
                    for (let messageId of result) {
                        switch (messageId) {
                            case "Msg_740": {
                                $('#G6_4').ntsError('set', { messageId: messageId });
                                break;
                            }
                            case "Msg_825": {
                                $('#G8_2').ntsError('set', { messageId: messageId });
                                break;
                            }
                            case "Msg_1212": {
                                $('#G7_2').ntsError('set', { messageId: messageId });
                                break;
                            }
                            case "Msg_1213": {
                                $('#G9_2').ntsError('set', { messageId: messageId });
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
        public removeData() {
            block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this;
                let data = {
                    payoutId: self.payoutId(),
                    employeeId: self.employeeId(),
                    dayoffDate: moment.utc(self.dayoffDate(), 'YYYY/MM/DD').toISOString()
                };

                service.removePayout(data).done(() => {
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_A_PARAMS', { isSuccess: true });
                        nts.uk.ui.windows.close();
                    });
                }).fail(function(res) {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
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