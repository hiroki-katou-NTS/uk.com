module nts.uk.pr.view.qmm008.g.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm008.share.model;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable('');
        socialInsuranceName: KnockoutObservable<string> = ko.observable('');
        startMonth: KnockoutObservable<string> = ko.observable('');
        takeoverMethod: KnockoutObservable<number> = ko.observable(1);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        lastestHistory: number = 190000;
        constructor() {
            let self = this;
            let params = getShared("QMM008_G_PARAMS");
            block.invisible();
            if (params) {
                let selectedOffice = params.selectedOffice, displayLastestHistory = "";
                let history = params.history;
                if (history && history.length > 0) {
                    let lastestHistory = history[0].startMonth;
                    displayLastestHistory = String(lastestHistory).substring(0, 4) + "/" + String(lastestHistory).substring(4, 6)
                    self.lastestHistory = lastestHistory;
                    self.startMonth(lastestHistory);
                }
                self.socialInsuranceCode(selectedOffice.socialInsuranceCode);
                self.socialInsuranceName(selectedOffice.socialInsuranceName);
                if (displayLastestHistory.length > 0) {
                    self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_LASTEST_HISTORY, getText('QMM008_200', [displayLastestHistory])));
                    self.takeoverMethod(0);
                }
                self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_BEGINNING, getText('QMM008_201')));
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
            if (self.startMonth() <= self.lastestHistory.toString()) {
                dialog.alertError({ messageId: "Msg_79" });
                return;
            }
            setShared('QMM008_G_RES_PARAMS', { startMonth: self.startMonth(), takeoverMethod: self.takeoverMethod() });
            nts.uk.ui.windows.close();
        }
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
}


