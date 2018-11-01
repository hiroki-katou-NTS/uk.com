module nts.uk.pr.view.qmm001.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import model = qmm001.share.model;
    import service = nts.uk.pr.view.qmm001.c.service;
    export class ScreenModel {
        startYearMonth: KnockoutObservable<number> = ko.observable();
        startDate: KnockoutObservable<string> = ko.observable('');
        end: KnockoutObservable<string> = ko.observable('');
        endYearMonth: KnockoutObservable<number> = ko.observable();
        endDate: KnockoutObservable<number> = ko.observable();
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        startLastDate: KnockoutObservable<string> = ko.observable('');
        itemList: KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        methodEditing: KnockoutObservable<number> = ko.observable(1);
        historyAtr: KnockoutObservable<number> = ko.observable(1);

        // validate disable item
        isFirst: KnockoutObservable<boolean> = ko.observable(true);
        insuranceName: KnockoutObservable<string> = ko.observable('');
        // data
        name : KnockoutObservable<string> = ko.observable('');
        code : KnockoutObservable<string> = ko.observable('');
        hisId: KnockoutObservable<string> = ko.observable('');

        constructor() {
            block.invisible();
            let self = this;
            self.initScreen();
            block.clear();
        }
        initScreen() {
            let self = this;
            let params: any = getShared('QMM001_PARAMS_TO_SCREEN_C');
            if (params) {
                self.code(params.code);
                self.name(params.name);
                self.hisId(params.hisId);
                self.isFirst(params.isFirst);
                self.historyAtr(params.historyAtr);
                if (params.historyAtr == 1) {
                    self.startLastYearMonth(params.startLastYearMonth);
                    self.end(getText('QMM001_31', [self.convertMonthYearToString(params.end)]));
                    self.startYearMonth(params.start);
                    self.endYearMonth(params.end);
                }
                if (params.historyAtr == 0) {
                    self.startLastDate((params.startLastDate == 0) ? 0 : self.convertStringToDate(params.startLastDate));
                    self.end(getText('QMM001_31', [params.end]));
                    self.startDate(params.start);
                    self.endDate(params.end);
                }

            }
        }
        update(){
            let self = this;
            if(self.historyAtr() == PARAHISTORYATR.YMHIST && self.validateYearMonth()) {
                return;
            }
            if(self.historyAtr() == PARAHISTORYATR.YMDHIST && self.validateYearMonthDay()) {
                return;
            }

            block.invisible();
            if (self.historyAtr() == PARAHISTORYATR.YMDHIST) {
                let data: any = {
                    hisId: self.hisId(),
                    code: self.code(),
                    paraNo: self.code(),
                    start: moment.utc(self.startDate(), 'YYYY/MM/DD').toISOString(),
                    end: moment.utc(self.endDate(), 'YYYY/MM/DD').toISOString(),
                    mode: self.methodEditing()
                }
                if (self.methodEditing() == EDIT_METHOD.DELETE) {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.updateHistoryDate(data).done(() => {
                            dialog.info({messageId: "Msg_16"}).then(() => {
                                setShared('QMM001_C_PARAMS_OUTPUT', {
                                    result: true
                                });
                                close();
                            });
                        }).fail(function (res: any) {
                            if (res)
                                dialog.alertError(res);
                        });
                    });
                } else {
                    service.updateHistoryDate(data).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            setShared('QMM001_C_PARAMS_OUTPUT', {
                                result: true
                            });
                            close();
                        });
                    }).fail(function (res: any) {
                        if (res)
                            dialog.alertError(res);
                    });
                }
            } else {
                let data: any = {
                    hisId: self.hisId(),
                    code: self.code(),
                    paraNo: self.code(),
                    start: Number(self.startYearMonth()),
                    end: self.endYearMonth(),
                    mode: self.methodEditing()
                }
                if (self.methodEditing() == EDIT_METHOD.DELETE) {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.updateHistoryYearMonth(data).done(() => {
                            dialog.info({messageId: "Msg_16"}).then(() => {
                                setShared('QMM001_C_PARAMS_OUTPUT', {
                                    methodEditing: self.methodEditing()
                                });
                                close();
                            });
                        }).fail(function (res: any) {
                            if (res)
                                dialog.alertError(res);
                        });
                    });
                } else {
                    service.updateHistoryYearMonth(data).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            setShared('QMM001_C_PARAMS_OUTPUT', {
                                methodEditing: self.methodEditing()
                            });
                            close();
                        });
                    }).fail(function (res: any) {
                        if (res)
                            dialog.alertError(res);
                    });
                }

            }
            block.clear();
        }


        hasRequired() {
            return (this.methodEditing() == EDIT_METHOD.UPDATE);
        }

        validateYearMonth() {
            let self = this;
            if (!this.hasRequired()) {
                return false;
            }
            if ((!(self.startLastYearMonth() < self.startYearMonth())) || (self.startYearMonth() > Number(self.endYearMonth()))) {
                $('#C1_12').ntsError('set', { messageId: "Msg_107" });
                return true;
            }
            return false;
        }

        validateYearMonthDay(){
            if (!this.hasRequired()) {
                return false;
            }
            let self = this;
            if ((!(self.startLastDate() < self.startDate())) ||  (self.startYearMonth() > Number(self.endDate())) ) {
                $('#C1_11').ntsError('set', { messageId: "Msg_107" });
                return true;
            }
            return false;
        }

        cancel(){
            close();
        }

        convertMonthYearToString(yearMonth: any) {
            if (yearMonth) {
                let year: string, month: string;
                yearMonth = yearMonth.toString();
                year = yearMonth.slice(0, 4);
                month = yearMonth.slice(4, 6);
                return year + "/" + month;
            }
        }

        convertStringToDate(date: any) {
            date = date.slice(0, 4) + date.slice(5, 7) + date.slice(8,10);
            return Number(date);
        }

    }

    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [ new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM001_40')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM001_41'))
        ];
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum PARAHISTORYATR {
        /*年月日履歴*/
        YMDHIST = 0,

        /*年月履歴*/
        YMHIST = 1
    }
}