module nts.uk.pr.view.qmm007.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm007.share.model;

    export class ScreenModel {
        cId:  KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        constructor() {
            let self = this;
        }

    class AcquiCondiPayrollHis{
        cId :string;
        code :string;
        startYearMonth:number

    }




}