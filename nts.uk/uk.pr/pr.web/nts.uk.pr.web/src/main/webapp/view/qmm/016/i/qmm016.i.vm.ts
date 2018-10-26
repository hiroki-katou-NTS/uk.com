module nts.uk.pr.view.qmm016.i.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm016.share.model;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        wageTableCode: KnockoutObservable<string> = ko.observable('');
        wageTableName: KnockoutObservable<string> = ko.observable('');
        startMonth: KnockoutObservable<string> = ko.observable('');
        takeoverMethod: KnockoutObservable<number> = ko.observable(1);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        lastHistory: number = 190000;
        constructor() {
            let self = this;
            let params = getShared("QMM016_I_PARAMS");
            block.invisible();
            if (params) {
                let selectedWageTable = params.selectedWageTable, displayLastHistory = "";
                let history = params.history;
                if (history && history.length > 0) {
                    let lastHistory = history[0].startMonth;
                    displayLastHistory = nts.uk.time.formatYearMonth(lastHistory);
                    self.lastHistory = lastHistory;
                    self.startMonth(lastHistory);
                }
                self.wageTableCode(selectedWageTable.wageTableCode);
                self.wageTableName(selectedWageTable.wageTableName);
                if (displayLastHistory.length > 0) {
                    self.takeoverItem.push(new model.ItemModel(model.TAKEOVER_METHOD.FROM_LASTEST_HISTORY, getText('QMM008_200', [displayLastHistory])));
                    self.takeoverMethod(0);
                }
                self.takeoverItem.push(new model.ItemModel(model.TAKEOVER_METHOD.FROM_BEGINNING, getText('QMM008_201')));
            }
            block.clear();
        }
        addNewHistory() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            if (self.startMonth() <= self.lastHistory.toString()) {
                dialog.alertError({ messageId: "Msg_79" });
                return;
            }
            setShared('QMM016_I_RES_PARAMS', { startMonth: self.startMonth(), takeoverMethod: self.takeoverMethod() });
            nts.uk.ui.windows.close();
        }
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}


