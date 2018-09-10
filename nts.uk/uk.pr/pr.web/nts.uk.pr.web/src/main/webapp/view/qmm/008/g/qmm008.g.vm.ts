module nts.uk.com.view.qmm008.g.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.com.view.qmm008.share.model;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        startDate: KnockoutObservable<string> = ko.observable('');
        takeoverMethod: KnockoutObservable<number> = ko.observable(0);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        lastestHistory: number = 190001;
        constructor() {
            let self = this;
            let params = getShared("QMM008_G_PARAMS"); 
            if (params){
                let selectedOffice = params.selectedOffice, displayLastestHistory = "";
                let history = selectedOffice.welfareInsuranceRateHistory.history;
                if (history.length > 0){
                    let lastestHistory = history[history.length-1].start;
                    displayLastestHistory = lastestHistory.substring(0, 4) + "/" + lastestHistory.substring(4, 6)
                    self.lastestHistory = history[history.length-1].start;
                }
                self.code(selectedOffice.code);
                self.name(selectedOffice.name);
                self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_LASTEST_HISTORY, getText('QMM008_200') + displayLastestHistory));
                self.takeoverItem.push(new model.EnumModel(model.TAKEOVER_METHOD.FROM_BEGINNING, getText('QMM008_201')));
            }
        }
        addNewHistory (){ 
            let self = this;
            if (self.startDate() <= self.lastestHistory.toString()){
                dialog.alertError({ messageId: "Msg_79" });
                return;
            }
            setShared('QMM008_G_RES_PARAMS', {startDate: self.startDate(), takeoverMethod: self.takeoverMethod()});
            nts.uk.ui.windows.close();
        }
        cancel (){
            nts.uk.ui.windows.close();
        }
    }
    
    
}


