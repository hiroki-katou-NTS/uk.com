module nts.uk.at.view.ksp001.d.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import info = nts.uk.ui.dialog.info;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        displayAtrArr: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('KSP001_26') },
            { code: 0, name: getText('KSP001_27') }
        ]);
        selectedNoti: KnockoutObservable<number> = ko.observable(0);
        selectedOver: KnockoutObservable<number> = ko.observable(0);
        selectedStatus: KnockoutObservable<number> = ko.observable(0);
        isEnableNoti: KnockoutObservable<boolean> = ko.observable(false);
        isEnableStatus: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            self.selectedNoti.subscribe(newValue => {
                if (newValue) {
                    self.isEnableNoti(true);
                } else {
                    self.isEnableNoti(false);
                }
            });

            self.selectedStatus.subscribe(newValue => {
                if (newValue) {
                    self.isEnableStatus(true);
                } else {
                    self.isEnableStatus(false);
                }
            });

        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            self.getToppageSet();
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * get data from SP_MENU_TOPPAGE
         */
        getToppageSet(): JQueryPromise<any> {
            block.grayout();
            let self = this, dfd = $.Deferred();
            service.getTopPageSet().done((data) => {
                _.each(data, dt => {
                    switch (dt.type) {
                        case TYPE.NOTIFICATION:
                            self.selectedNoti(dt.displayAtr);
                            break;
                        case TYPE.STATUS:
                            self.selectedStatus(dt.displayAtr);
                            break;
                        case TYPE.OVERTIME:
                            self.selectedOver(dt.displayAtr);
                            break;
                    }
                });
                dfd.resolve();
            }).fail(err => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }
        
        /**
         * save data
         */
        saveData(): JQueryPromise<any> {
            block.grayout();
            let self = this,
                dfd = $.Deferred(),
                params: any = [
                    {
                        systemAtr: 1, // 勤次郎
                        type: TYPE.NOTIFICATION,
                        displayAtr: self.selectedNoti()
                    }, {
                        systemAtr: 1, // 勤次郎
                        type: TYPE.STATUS,
                        displayAtr: self.selectedStatus()
                    }, {
                        systemAtr: 1, // 勤次郎
                        type: TYPE.OVERTIME,
                        displayAtr: self.selectedOver()
                    }];
            service.saveData({ listSPTopPageSetDto: params }).done(() => {
                info({ messageId: "Msg_15" });
                $($('div.first-swBtn')[0]).focus();
                dfd.resolve();
            }).fail((err) => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });

            return dfd.promise();
        }

        openENoti() {
            setShared('dataD', { typeAtr: TYPE.NOTIFICATION.valueOf() });
            nts.uk.ui.windows.sub.modal("/view/ksp/001/e/index.xhtml", { height: 250 });
        }

        openEStatus() {
            setShared('dataD', { typeAtr: TYPE.STATUS.valueOf() });
            nts.uk.ui.windows.sub.modal("/view/ksp/001/e/index.xhtml", { height: 400 });
        }
    }

    enum TYPE {
        // 通知
        NOTIFICATION = 0,
        // 勤怠状況
        STATUS = 1,
        //時間外労働
        OVERTIME = 2,
        //打刻入力"
        STAMPING = 3
    }
}
