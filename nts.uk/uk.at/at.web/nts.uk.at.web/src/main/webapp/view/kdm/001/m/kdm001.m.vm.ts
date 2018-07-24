module nts.uk.at.view.kdm001.m.viewmodel {
    import model     = nts.uk.at.view.kdm001.share.model;
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
        remainDaysItemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(model.getRemainDaysItemList());
        remainDays: KnockoutObservable<string>                       = ko.observable('');
        comDayOffID: KnockoutObservable<string>                      = ko.observable('');
        closureId: KnockoutObservable<string>                        = ko.observable('');
        unknownDate: KnockoutObservable<number> = ko.observable(1);
        constructor() {
        }

        public startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.initScreen();
            dfd.resolve();
            return dfd.promise();
        }

        private initScreen(): void {
            block.invisible();
            let self = this,
            info = getShared("KDM001_M_PARAMS");
            if (info) {
                self.workCode(info.selectedEmployee.workplaceCode);
                self.workName(info.selectedEmployee.workplaceName);
                self.employeeId(info.selectedEmployee.employeeId);
                self.employeeCode(info.selectedEmployee.employeeCode);
                self.employeeName(info.selectedEmployee.employeeName);
                self.comDayOffID(info.row.comDayOffID);
                self.dayOffDate(info.row.dayOffDate);
                self.requireDays(info.row.requireDays);
                self.remainDays(info.row.remainDays + '');
                self.closureId(info.closure.closureId);
                self.unknownDate(info.row.unknownDate);
            }
            block.clear();
        }

        /**
         * 登録
         */
        public updateComDayOffMana(): void {
            nts.uk.ui.errors.clearAll();
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let self = this,
                command = {
                    employeeId: self.employeeId(),
                    comDayOffID: self.comDayOffID(),
                    dayOffDate: moment.utc(self.dayOffDate(), 'YYYY/MM/DD').toISOString(),
                    requireDays: self.requireDays(),
                    remainDays: self.remainDays(),
                    closureId: self.closureId(),
                    unknownDate: self.unknownDate()
                };
                service.updateComDayOffMana(command).done(result => {
                    if (result.length > 0) {
                        $('#M6_2').ntsError('set', { messageId: result[0]});
                        block.clear();
                        return;
                    }
                    //情報メッセージ　Msg_15 登録しました。を表示する。
                    dialog.info({ messageId: "Msg_15" }).then(() => {
                        setShared('KDM001_M_PARAMS_RES', { isChanged: true });
                        self.closeKDM001M();
                    });
                }).always(() => {
                    block.clear();
                });
            }
        }

        /**
         * 削除
         */
        public deleteComDayOffMana(): void {
            block.invisible();
             //確認メッセージ（Msg_18）を表示する
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                let self = this,
                command = {
                    employeeId: self.employeeId(),
                    comDayOffID: self.comDayOffID(),
                    requireDays: self.requireDays(),
                    remainDays: self.remainDays()
                };
                service.deleteComDayOffMana(command).done(() => {
                    //情報メッセージ　Msg-16を表示する
                    dialog.info({ messageId: "Msg_16" }).then(() => {
                        setShared('KDM001_M_PARAMS_RES', { isChanged: true });
                        self.closeKDM001M();
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

        public closeKDM001M(): void {
            nts.uk.ui.windows.close();
        }
    }
}