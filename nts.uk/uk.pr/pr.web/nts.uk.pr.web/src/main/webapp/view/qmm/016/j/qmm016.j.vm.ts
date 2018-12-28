module nts.uk.pr.view.qmm016.j.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import service = nts.uk.pr.view.qmm016.j.service;
    export class ScreenModel {
        wageTableCode: KnockoutObservable<string> = ko.observable('');
        wageTableName: KnockoutObservable<string> = ko.observable('');
        startMonth: KnockoutObservable<string> = ko.observable('');
        displayEndMonth: KnockoutObservable<string> = ko.observable('');
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<any> = ko.observableArray([]);
        isLastHistory: KnockoutObservable<boolean> = ko.observable(false);
        selectedHistory: any = null;
        screen: string = "";
        previousHistory: any = null;
        constructor() {
            let self = this;
            let params = getShared("QMM016_J_PARAMS");
            if (params) {
                block.invisible();
                let selectedWageTable = params.selectedWageTable;
                self.selectedHistory = params.selectedHistory;
                self.startMonth(self.selectedHistory.startMonth);
                self.displayEndMonth(getText('QMM016_31') + "  " + nts.uk.time.parseYearMonth(self.selectedHistory.endMonth).format());
                self.screen = params.screen;
                let history = params.history;
                if (history && history.length > 0) {
                    history.forEach((historyItem, index) => {
                        if (self.selectedHistory.historyID == historyItem.historyID) 
                            self.previousHistory = history[index + 1];
                    });
                    self.isLastHistory(params.selectedHistory.startMonth == history[0].startMonth);
                }
                self.wageTableCode(selectedWageTable.wageTableCode);
                self.wageTableName(selectedWageTable.wageTableName);
                self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.DELETE, getText('QMM008_206')));
                self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.UPDATE, getText('QMM008_207')));
            }
            block.clear();
        }
        
        editHistory() {
            let self = this;
            $(".nts-input").filter(":enabled").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                if (self.modifyMethod() == model.MODIFY_METHOD.UPDATE) {
                    if (self.startMonth() > self.selectedHistory.endMonth.toString()) {
                        dialog.alertError({ messageId: "MsgQ_156" });
                        return;
                    }
                    if (self.previousHistory) {
                        if (self.startMonth() <= self.previousHistory.startMonth.toString()) {
                            dialog.alertError({ messageId: "MsgQ_156" });
                            return;
                        }
                    }
                    self.updateHistory();
                } else {
                    dialog.confirm({ messageId: "Msg_18" }).ifYes(function () {
                        self.deleteHistory();
                    });
                }
            }
        }

        updateHistory() {
            let self = this;
            block.invisible();
            let command = { 
                wageTableCode: self.wageTableCode(), 
                historyId: self.selectedHistory.historyID, 
                startYm: self.startMonth(), 
                endYm: self.selectedHistory.endMonth 
            };
            service.editWageTableHistory(command).done(function() {
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    setShared('QMM016_J_RES_PARAMS', { modifyMethod: self.modifyMethod(), historyId: self.selectedHistory.historyID });
                    nts.uk.ui.windows.close();
                })
            }).fail(function(err) {
                dialog.alertError(err);
            }).always(() => {
                block.clear();
            });
        }

        deleteHistory() {
            let self = this;
            block.invisible();
            let command = { wageTableCode: self.wageTableCode(), historyId: self.selectedHistory.historyID };
            service.deleteWageTableHistory(command).done(function(historyId: string) {
                nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                    setShared('QMM016_J_RES_PARAMS', { modifyMethod: self.modifyMethod(), historyId: historyId });
                    nts.uk.ui.windows.close();
                });
            }).fail(function(err) {
                dialog.alertError(err);
            }).always(() => {
                block.clear();
            });
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
    }

}

