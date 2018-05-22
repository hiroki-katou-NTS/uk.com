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
            let self = this,
            info = getShared("KDM001_M_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workCode);
                self.workName(info.selectedEmployee.workName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                
            }
            block.clear();
        }

        /**
         * 登録
         */
        updateComDayOffMana(): void {
            let self = this,
            command = {
                employeeId: self.employeeId(),
                comDayOffID: self.comDayOffID(),
                dayOffDate: self.dayOffDate(),
                requireDays: self.requireDays(),
                remainDays: self.remainDays(),
                executeMode: model.ExecuteMode.UPDATE
            }
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                service.updateComDayOffMana(command).done(result => {
                    if (result.length > 0) {
                        let messageId = result[0];
                        $('#M6_2').ntsError('set', { messageId: messageId });
                        block.clear();
                        return;
                    }
                    //情報メッセージ　Msg_15 登録しました。を表示する。
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_M_PARAMS_RES', command);
                        nts.uk.ui.windows.close();
                    });
                }).always(() => {
                    block.clear();
                });
            }
        }

        /**
         * 削除
         */
        deleteComDayOffMana(): void {
            block.invisible();
             //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                command = {
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
                        setShared('KDM001_M_PARAMS_RES', command);
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