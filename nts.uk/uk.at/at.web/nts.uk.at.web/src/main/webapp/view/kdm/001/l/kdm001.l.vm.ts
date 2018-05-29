module nts.uk.at.view.kdm001.l.viewmodel {
    import model = nts.uk.at.view.kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    export class ScreenModel {
        workCode: KnockoutObservable<string>        = ko.observable('');
        workPlaceName: KnockoutObservable<string>   = ko.observable('');
        employeeId: KnockoutObservable<string>      = ko.observable('');
        employeeCode: KnockoutObservable<string>    = ko.observable('');
        employeeName: KnockoutObservable<string>    = ko.observable('');
        leaveDate: KnockoutObservable<string>       = ko.observable('');
        expiredDate: KnockoutObservable<string>     = ko.observable('');
        occurredDays: KnockoutObservable<string>    = ko.observable('');
        unUsedDays: KnockoutObservable<string>      = ko.observable('');
        checkedExpired: KnockoutObservable<boolean> = ko.observable(false);
        leaveId: KnockoutObservable<string>         = ko.observable('');
        numberOfDay: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        closureId: KnockoutObservable<number>       = ko.observable(0);

        constructor() {
            let self = this;
            self.initScreen();
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
                self.checkedExpired(info.row.subHDAtr == 1);
                self.closureId(info.closure.closureId);
            }
            block.clear();
        }

        /**
         * 登録
         */
        updateHolidaySetting(): void {
            nts.uk.ui.errors.clearAll();
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                let self = this,
                command = {
                    leaveId: self.leaveId(),
                    employeeId: self.employeeId(),
                    leaveDate: self.leaveDate(),
                    expiredDate: self.expiredDate(),
                    occurredDays: self.occurredDays(),
                    unUsedDays: self.unUsedDays(),
                    isCheckedExpired: self.checkedExpired(),
                    closureId: self.closureId()
                };
                service.updateHolidaySetting(command).done(result => {
                    if (result && result.length > 0) {
                        for (let messageId of result) {
                            switch (messageId) {
                                case "Msg_745": {
                                    $('#L6_2').ntsError('set', { messageId: messageId });
                                    break;
                                }
                                case "Msg_825": {
                                    $('#L6_2').ntsError('set', { messageId: messageId });
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
                    //情報メッセージ　Msg_15 登録しました。を表示する。
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_L_PARAMS_RES', { isChanged: true });
                        self.closeDialog();
                    });
                }).always(() => {
                    block.clear();
                });
            }
        }

        /**
         * 削除
         */
        deleteHolidaySetting(): void {
            block.invisible();
             //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                command = {
                    leaveId: self.leaveId(),
                    employeeId: self.employeeId(),
                    leaveDate: self.leaveDate(),
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
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
    }
}