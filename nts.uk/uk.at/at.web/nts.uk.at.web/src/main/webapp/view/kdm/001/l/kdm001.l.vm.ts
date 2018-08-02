module nts.uk.at.view.kdm001.l.viewmodel {
    import model = nts.uk.at.view.kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        workCode: KnockoutObservable<string> = ko.observable('');
        workPlaceName: KnockoutObservable<string> = ko.observable('');
        employeeId: KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        leaveDate: KnockoutObservable<string> = ko.observable('');
        unknownDate: KnockoutObservable<number> = ko.observable(1);
        expiredDate: KnockoutObservable<string> = ko.observable('');
        occurredDays: KnockoutObservable<string> = ko.observable('');
        unUsedDays: KnockoutObservable<string> = ko.observable('');
        checkedExpired: KnockoutObservable<boolean> = ko.observable(false);
        leaveId: KnockoutObservable<string> = ko.observable('');
        numberOfDay: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        closureId: KnockoutObservable<number> = ko.observable(0);
        disableCheckedExpired: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
        }

        public startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.initScreen();
            dfd.resolve();
            return dfd.promise();
        }

        public initScreen(): void {
            block.invisible();
            let self = this,
                info = getShared("KDM001_L_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workPlaceName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.leaveId(info.row.id);
                self.leaveDate(info.row.dayOffDate);
                self.expiredDate(info.row.expiredDate);
                self.occurredDays(info.row.occurredDays);
                self.unUsedDays(info.row.unUsedDays);
                self.checkedExpired(info.row.subHDAtr == 2);
                self.disableCheckedExpired(!self.checkedExpired());
                self.closureId(info.closure.closureId);
                self.unknownDate(info.row.unknownDate);
            }
            block.clear();
        }

        /**
         * 登録
         */
        public updateHolidaySetting(): void {
            nts.uk.ui.errors.clearAll();
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let self = this,
                    command = {
                        leaveId: self.leaveId(),
                        employeeId: self.employeeId(),
                        leaveDate: moment.utc(self.leaveDate(), 'YYYY/MM/DD').toISOString(),
                        expiredDate: self.expiredDate(),
                        occurredDays: self.occurredDays(),
                        unUsedDays: self.unUsedDays(),
                        isCheckedExpired: self.checkedExpired(),
                        closureId: self.closureId(),
                        unknownDate: self.unknownDate()
                    };
                service.checkValidate(command).done(result => {
                    if (result && result.length > 0) {
                        if (result.indexOf("Msg_1302") >= 0) {
                            if (self.unknownDate() == 0) {
                                dialog.confirm({ messageId: "Msg_1302" }).ifYes(() => {
                                    self.callUpdateHolidaySetting(command);
                                }).then(() => {
                                    block.clear();
                                    return;
                                });
                            } else {
                                self.callUpdateHolidaySetting(command);
                            }
                        }
                        for (let messageId of result) {
                            switch (messageId) {
                                case "Msg_745": {
                                    $('#L6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_825": {
                                    $('#L9_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_1212": {
                                    $('#L10_4').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_1213": {
                                    $('#L10_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                            }
                        }
                        block.clear();
                        return;
                    }
                    self.callUpdateHolidaySetting(command);
                }).always(() => {
                    block.clear();
                });
            }
        }

        /**
         * 情報メッセージ　Msg_15 登録しました。を表示する。
         */
        public callUpdateHolidaySetting(command) {
            service.updateHolidaySetting(command).done(result => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    setShared('KDM001_L_PARAMS_RES', { isChanged: true });
                    this.closeDialog();
                });
            });
        }

        /**
         * 削除
         */
        public deleteHolidaySetting(): void {
            block.invisible();
            //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                    command = {
                        leaveId: self.leaveId(),
                        employeeId: self.employeeId(),
                        expiredDate: self.expiredDate(),
                        occurredDays: self.occurredDays(),
                        isCheckedExpired: self.checkedExpired()
                    };
                service.deleteHolidaySetting(command).done(() => {
                    //情報メッセージ　Msg-16を表示する
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_L_PARAMS_RES', { isChanged: true });
                        self.closeDialog();
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