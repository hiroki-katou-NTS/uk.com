module nts.uk.pr.view.qmm011.f.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.errors;
    import model = qmm011.share.model;
    export class ScreenModel {
        startYearMonth:         KnockoutObservable<number> = ko.observable();
        endYearMonth:           KnockoutObservable<number> = ko.observable();
        startLastYearMonth:     KnockoutObservable<number> = ko.observable();
        itemList:               KnockoutObservableArray<model.ItemModel> = ko.observableArray(getHistoryEditMethod());
        selectedId:             KnockoutObservable<string> = ko.observable('');
        methodEditing:          KnockoutObservable<number> = ko.observable(1);
        insurrance:             KnockoutObservable<number> = ko.observable();
        hisId:                  KnockoutObservable<string> = ko.observable('');
        canDelete:              KnockoutObservable<boolean> = ko.observable('');
        insuranceName:          KnockoutObservable<string> = ko.observable('');
        constructor() {
            block.invisible();
            let self = this;
            self.startYearMonth();
            let params = getShared('QMM011_F_PARAMS_INPUT');
            let to = getText('QMM011_9');
            if (params) {
                self.insuranceName(params.insuranceName);
                self.insurrance(params.insurrance);
                self.startYearMonth(params.startYearMonth);
                self.endYearMonth(' '+ to + ' ' + self.convertMonthYearToString(params.endYearMonth));
                self.startLastYearMonth(params.startLastYearMonth);
                self.canDelete(params.canDelete);
                self.hisId(params.hisId);
            }
            block.clear();
        }

        update() {
            let self = this;
            if (self.validateYearMonth()) {
                return;
            }
            let param: any = {
                hisId: self.hisId(),
                methodEditing: self.methodEditing(),
                startMonthYear: $("#F1_9")[0].disabled ? 0 : self.startYearMonth(),
                endMonthYear: self.convertStringToYearMonth(self.endYearMonth())
            }
            block.invisible();
            if (self.insurrance() == INSURRANCE.EMPLOYMENT_INSURRANCE_RATE) {
                if (self.methodEditing() == EDIT_METHOD.DELETE) {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                        service.updateEmpInsurHis(param).done(() => {
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
                    service.updateEmpInsurHis(param).done(() => {
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

        hasRequired(){
            if(this.methodEditing() != EDIT_METHOD.UPDATE) {
                $('#F1_9').ntsError('clear');
                return false;
            }
            return true;
        }

        validateYearMonth(){
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("#F1_9").trigger("validate");
            if($("#F1_9")[0].disabled){
                $("#F1_9").ntsError('clear');
                nts.uk.ui.errors.removeByElement($("#F1_9"));
                return false;
            }
            if (self.startYearMonth() == self.endYearMonth() || Number(self.startYearMonth()) > Number(self.endYearMonth()) ||
                (Number(self.startLastYearMonth()) > Number(self.startYearMonth()) && (this.methodEditing() == EDIT_METHOD.UPDATE))){
                $('#F1_9').ntsError('set', { messageId: "Msg_107" });
                return true;
            }
            return error.hasError();
        }

        cancel(){
            close();
        }

        convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }

        convertStringToYearMonth(yearMonth: any){
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.substring(3);
            yearMonth = yearMonth.slice(0, 4) + yearMonth.slice(5, 7);
            return yearMonth;
        }
        // 「初期データ取得処理
    }

    export function getHistoryEditMethod(): Array<model.ItemModel> {
        return [
            new model.ItemModel(EDIT_METHOD.DELETE, getText('QMM011_54')),
            new model.ItemModel(EDIT_METHOD.UPDATE, getText('QMM011_55'))
        ];
    }

    export enum EDIT_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum INSURRANCE {
        EMPLOYMENT_INSURRANCE_RATE = 1,
        ACCIDENT_INSURRANCE_RATE = 0
    }



}