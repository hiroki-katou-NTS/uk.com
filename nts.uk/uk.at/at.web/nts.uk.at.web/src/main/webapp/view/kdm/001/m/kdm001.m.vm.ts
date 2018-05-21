module nts.uk.at.view.kdm001.m.viewmodel {
    import modal     = nts.uk.ui.windows.sub.modal;
    import model     = kdm001.share.model;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog    = nts.uk.ui.dialog;
    import block     = nts.uk.ui.block;
    export class ScreenModel {
        workCode: KnockoutObservable<string>     = ko.observable('');
        workName: KnockoutObservable<string>     = ko.observable('');
        employeeId  : KnockoutObservable<string> = ko.observable('');
        employeeCode: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');
        dayOffDate: KnockoutObservable<string>         = ko.observable('');
        requireDayItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        requireDays: KnockoutObservable<any>                         = ko.observable('');
        remainDaysItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getNumberOfDays());
        remainDays: KnockoutObservable<string>                       = ko.observable('');
        comDayOffID: KnockoutObservable<string>                      = ko.observable('');

        constructor() {
            let self = this;
            self.initScreen();
        }

        initScreen(): void {
            block.invisible();
            let self = this;
            let info = getShared("KDM001_B_DATA");
            if (info) {
                self.workCode(info.workCode);
                self.workName(info.workName);
                self.employeeId(info.employeeId);
                self.employeeCode(info.employeeCode);
                self.employeeName(info.employeeName);
                self.comDayOffID(info.comDayOffID);
            }
            //TODO: Mock data
            self.comDayOffID('5a4470e6-3825-48b6-ae5a-43fd9febcb91');
            self.employeeId('9bb2f690-b856-4bcc-b9d3-f73cc0f97ba3');
            service.getCompensatoryByComDayOffID(self.comDayOffID()).done(result => {
                if (result) {
                    self.dayOffDate(result.dayOffDate);
                    self.requireDays(result.requireDays);
                    self.remainDays(result.remainDays);
                }
            }).always(() => {
                block.clear();
            });
        }

        /**
         * 登録
         */
        updateComDayOffMana(): void {
            block.invisible();
            let self = this;
            let command = {
                employeeId: self.employeeId(),
                comDayOffID: self.comDayOffID(),
                dayOffDate: self.dayOffDate(),
                requireDays: self.requireDays(),
                remainDays: self.remainDays(),
                executeMode: model.ExecuteMode.UPDATE
            }
            service.updateComDayOffMana(command).done(result => {
                if (result.length > 0) {
                    let messageId = result[0];
                    $('#M6_2').ntsError('set', { messageId: messageId});
                    return;
                }
                //情報メッセージ　Msg_15 登録しました。を表示する。
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    setShared('KDM001_M_DATA', command);
                    nts.uk.ui.windows.close();
                });
            }).always(() => {
                block.clear();
            });
        }

        /**
         * 削除
         */
        deleteComDayOffMana(): void {
            block.invisible();
             //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this;
                let command = {
                    employeeId: self.employeeId(),
                    comDayOffID: self.comDayOffID(),
                    dayOffDate: self.dayOffDate(),
                    requireDays: self.requireDays(),
                    remainDays: self.remainDays(),
                    executeMode: model.ExecuteMode.DELETE
                }
                service.deleteComDayOffMana(command).done(() => {
                    //情報メッセージ　Msg-16を表示する
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_M_DATA', command);
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

        closeKDM001M(): void {
            nts.uk.ui.windows.close();
        }
    }
}