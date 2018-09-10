module nts.uk.com.view.qmm008.h.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.com.view.qmm008.share.model;
    export class ScreenModel {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        isLastestHistory: KnockoutObservable<boolean> = ko.observable(false);
        selectedHistory: any = null;
        previousHistory: any = null;
        constructor() {
            let self = this;
            let params = getShared("QMM008_H_PARAMS");
            if (params) {
                let selectedOffice = params.selectedOffice;
                self.selectedHistory = params.selectedHistory;
                self.startDate(self.selectedHistory.start);
                let history = selectedOffice.welfareInsuranceRateHistory.history;
                if (history.length > 0) {
                    history.forEach((historyItem, index) => {
                        if (self.selectedHistory.historyID == historyItem.historyID) self.previousHistory = history[index - 1];
                    });
                    self.isLastestHistory(params.selectedHistory.start == history[history.length - 1].start);
                }
                self.code(selectedOffice.code);
                self.name(selectedOffice.name);
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.DELETE, getText('QMM008_206')));
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.UPDATE, getText('QMM008_207')));
            }
        }
        editHistory() {
            let self = this;
            if (self.modifyMethod() == model.MOFIDY_METHOD.UPDATE) {
                if (self.startDate() > self.selectedHistory.end.toString()) {
                    dialog.alertError({ messageId: "Msg_107" });
                    return;
                }
                if (self.previousHistory) {
                    if (self.startDate() <= self.previousHistory.start.toString()) {
                        dialog.alertError({ messageId: "Msg_107" });
                        return;
                    }
                }
            }
            setShared('QMM008_H_RES_PARAMS', { startDate: self.startDate(), modifyMethod: self.modifyMethod() });
            nts.uk.ui.windows.close();
        }
        cancel() {
            nts.uk.ui.windows.close();
        } 
    }
        
}

