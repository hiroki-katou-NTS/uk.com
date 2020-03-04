module nts.uk.pr.view.qmm041.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import model = nts.uk.pr.view.qmm041.share.model;

    export class ScreenModel {
        modifyMethod: KnockoutObservable<number> = ko.observable(model.MODIFY_METHOD.UPDATE);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        startYearMonth: KnockoutObservable<number> = ko.observable(null);
        endYearMonth: string = null;
        isDeletable: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            block.invisible();
            let params = getShared("QMM041_C_PARAMS");
            if (params) {
                self.startYearMonth(params.startYearMonth);
                self.endYearMonth = params.endYearMonth;
                self.endYearMonth = nts.uk.time.parseYearMonth(params.endYearMonth).format();
                if (params.endYearMonth == 999912) {
                    self.isDeletable(true);
                }
            }
            self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.DELETE, getText('QMM041_30')));
            self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.UPDATE, getText('QMM041_31')));
            block.clear();
        }

        editHistory() {
            let self = this;
            if (self.modifyMethod() == model.MODIFY_METHOD.UPDATE) {
                $('.nts-input').trigger('validate');
                if(nts.uk.ui.errors.hasError()) return;
                self.updateHistory();
            } else {
                dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                    self.deleteHistory();
                });
            }
        }

        updateHistory() {
            let self = this;
            let params = getShared("QMM041_C_PARAMS");
            if (self.startYearMonth() <= params.lastStartYearMonth || self.startYearMonth() > params.endYearMonth) {
                dialog.info({messageId: "Msg_127"});
                return;
            }
            let command = {
                personalUnitPriceCode: params.personalUnitPriceCode,
                employeeId: params.employeeId,
                historyId: params.historyId,
                startYearMonth: self.startYearMonth(),
                endYearMonth: params.endYearMonth,
                lastHistoryId: params.lastHistoryId,
                lastStartYearMonth: params.lastStartYearMonth,
                lastEndYearMonth: (self.startYearMonth() - 1) % 100 == 0 ? self.startYearMonth - 89 : self.startYearMonth() - 1,
            };

            service.updateHistory(command).done(() => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    let dto = {
                        modifyMethod: self.modifyMethod(),
                        startYearMonth: self.startYearMonth(),
                        lastEndYearMonth: command.lastEndYearMonth
                    };
                    setShared('QMM041_C_RES_PARAMS', dto);

                    nts.uk.ui.windows.close();
                });
            }).fail(function (err) {
                dialog.alertError(err.message);
            });
        }

        deleteHistory() {
            let self = this;
            let params = getShared("QMM041_C_PARAMS");
            let command = {
                historyId: params.historyId,
                lastHistoryId: params.lastHistoryId
            };
            service.deleteHistory(command).done(() => {
                dialog.info({messageId: "Msg_16"}).then(() => {
                    setShared('QMM041_C_RES_PARAMS', {modifyMethod: self.modifyMethod()});
                    nts.uk.ui.windows.close();
                });
            }).fail((err) => {
                dialog.alertError(err.message);
            });

        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();

        }
    }
}
