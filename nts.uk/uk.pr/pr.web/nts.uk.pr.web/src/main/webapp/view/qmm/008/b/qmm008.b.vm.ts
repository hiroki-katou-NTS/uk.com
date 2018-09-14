module nts.uk.pr.view.qmm008.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        isUpdateMode: KnockoutObservable<boolean> = ko.observable(true);
        // History Tree Grid C1_1 -> C1_12 
        socialInsuranceOfficeList: KnockoutObservableArray<model.SocialInsuranceOffice> = ko.observableArray([]);
        isSelectedHistory: KnockoutObservable<boolean> = ko.observable(false);
        healthInsuranceRateTreeList: KnockoutObservableArray<model.TreeGridNode> = ko.observableArray([]);
        // Office and history info
        selectedOffice: any = null;
        selectedHistoryId: string = "";
        selectedHealthInsurance: KnockoutObservable<number> = ko.observable(null);
        constructor() {
            let self = this;
            let params = getShared("QMM008_G_PARAMS");
        }
        register() {
            console.log('register');
        }
        printPDF() {
            console.log('printPDF');
        }
        registerBusinessEstablishment() {
            console.log('registerBusinessEstablishment');
        }
        standardRemunerationMonthlyAmount() {
            console.log('standardRemunerationMonthlyAmount');
        }
        masterCorrectionLog() {
            console.log('masterCorrectionLog');
        }
    }
}

