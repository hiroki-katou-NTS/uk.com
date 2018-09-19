module nts.uk.pr.view.qmm008.h.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm008.share.model;
    import service = nts.uk.pr.view.qmm008.h.service;
    export class ScreenModel {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable('');
        socialInsuranceName: KnockoutObservable<string> = ko.observable('');        
        startMonth: KnockoutObservable<string> = ko.observable('');
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
                self.startMonth(self.selectedHistory.startMonth);
                let history = params.history;
                if (history && history.length > 0) {
                    history.forEach((historyItem, index) => {
                        if (self.selectedHistory.historyId == historyItem.historyId) self.previousHistory = history[index - 1];
                    });
                    self.isLastestHistory(params.selectedHistory.startMonth == history[history.length - 1].startMonth);
                }
                self.socialInsuranceCode(selectedOffice.code);
                self.socialInsuranceName(selectedOffice.name);
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.DELETE, getText('QMM008_206')));
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.UPDATE, getText('QMM008_207')));
            }
        }
        editHistory() {
            let self = this;
            if (self.modifyMethod() == model.MOFIDY_METHOD.UPDATE) {
                if (self.startMonth() > self.selectedHistory.endMonth.toString()) {
                    dialog.alertError({ messageId: "Msg_107" });
                    return;
                }
                if (self.previousHistory) {
                    if (self.startMonth() <= self.previousHistory.startMonth.toString()) {
                        dialog.alertError({ messageId: "Msg_107" });
                        return;
                    }
                }
                let newHistory = self.selectedHistory;
                newHistory.startMonth = self.startMonth();
                service.editHealthInsuranceHistory (newHistory).done(function(){
                    dialog.info({messageId: 15}).then(function(){
                        setShared('QMM008_H_RES_PARAMS', {modifyMethod: self.modifyMethod() });
                        nts.uk.ui.windows.close();
                    })
                }).fail(function(err){
                    dialog.alertError(err.message);
                });
            } else {
                service.deleteHealthInsuranceHistory (self.selectedHistory).done(function(){
                    dialog.info({messageId: 15}).then(function(){
                        setShared('QMM008_H_RES_PARAMS', {modifyMethod: self.modifyMethod() });
                        nts.uk.ui.windows.close();
                    })
                }).fail(function(err){
                    dialog.alertError(err.message);
                });
            }
        }
        
        editHealthInsuranceHistory (){
            let self = this;    
        }
        
        cancel() {
            nts.uk.ui.windows.close();
        } 
    }
        
}

