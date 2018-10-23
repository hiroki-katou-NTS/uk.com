module nts.uk.pr.view.qmm001.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import block = nts.uk.ui.block;
    import model = qmm001.share.model;
    export class ScreenModel {
        startYearMonth: KnockoutObservable<number> = ko.observable();
        startDate: KnockoutObservable<string> = ko.observable();
        end: KnockoutObservable<string> = ko.observable();
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        startLastDate: KnockoutObservable<string> = ko.observable();
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
                self.isFirst(params.isFirst);
                self.historyAtr(params.historyAtr);
                if (params.historyAtr == 1) {
                    self.startLastYearMonth(params.start);
                    self.end(getText('QMM001_31', [self.convertMonthYearToString(params.end)]));
                    self.startYearMonth(params.start);
                }
                if (params.historyAtr == 0) {
                    self.startLastDate(params.start);
                    self.end(getText('QMM001_31', [params.end]));
                    self.startDate(params.start);
                }

            }
        }
        update(){
            let self = this;
            let data: any = {
                hisId: self.hisId(),
                code: self.code(),
                start: Number(self.startYearMonth()),
                mode: self.methodEditing()
            }
            block.invisible();
            if (self.historyAtr() == PARAHISTORYATR.YMDHIST) {
                if (self.methodEditing() == EDIT_METHOD.DELETE) {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.UpdateHistoryDate(param).done(() => {
                            dialog.info({messageId: "Msg_16"}).then(() => {
                                setShared('QMM011_F_PARAMS_OUTPUT', {
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
                    service.updateEmpInsurHis(data).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            setShared('QMM011_F_PARAMS_OUTPUT', {
                                methodEditing: self.methodEditing()
                            });
                            close();
                        });
                    }).fail(function (res: any) {
                        if (res)
                            dialog.alertError(res);
                    });
                }
            } else {
                if (self.methodEditing() == EDIT_METHOD.DELETE) {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.updateAccInsurHis(param).done(() => {
                            dialog.info({messageId: "Msg_16"}).then(() => {
                                setShared('QMM011_F_PARAMS_OUTPUT', {
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
                    service.updateAccInsurHis(param).done(() => {
                        dialog.info({messageId: "Msg_15"}).then(() => {
                            setShared('QMM011_F_PARAMS_OUTPUT', {
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
            if (!(self.startLastYearMonth() < self.startYearMonth())) {
                dialog.error({messageId: "Msg_79"});
                return true;
            }
            return false;
        }

        validateYearMonthDay(){
            let self = this;
            if (!(moment.utc(self.startLastYearMonthDay(), 'YYYY/MM/DD') < moment.utc(self.startYearMonthDay(), 'YYYY/MM/DD'))) {
                dialog.error({messageId: "Msg_79"});
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

        convertStringToYearMonth(yearMonth: any){
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.substring(3);
            yearMonth = yearMonth.slice(0, 4) + yearMonth.slice(5, 7);
            return yearMonth;
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