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
        takeoverMethod: KnockoutObservable<number> = ko.observable(0);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        lastestHistory: number = 190001;
        constructor() {
            let self = this;
            let params = getShared("QMM008_G_PARAMS"); 
            if (params){
                let selectedOffice = params.selectedOffice, displayLastestHistory = "";
                let history = params.history;
                if (history && history.length > 0){
                    let lastestHistory = history[history.length-1].startMonth + "";
                    displayLastestHistory = lastestHistory.substring(0, 4) + "/" + lastestHistory.substring(4, 6)
                    self.lastestHistory = history[history.length-1].startMonth;
                }
                self.socialInsuranceCode(selectedOffice.socialInsuranceOfficeCode);
                self.socialInsuranceName(selectedOffice.socialInsuranceOfficeName);
                self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_LASTEST_HISTORY, getText('QMM008_200') + displayLastestHistory));
                self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_BEGINNING, getText('QMM008_201')));
            }
        }
        addNewHistory (){ 
            let self = this;
            if (self.startMonth() <= self.lastestHistory.toString()){
                dialog.alertError({ messageId: "Msg_79" });
                return;
            }
            setShared('QMM008_G_RES_PARAMS', {startMonth: self.startMonth(), takeoverMethod: self.takeoverMethod()});
            nts.uk.ui.windows.close();
        }
        cancel (){
            nts.uk.ui.windows.close();
        }
    }
    
    
}


