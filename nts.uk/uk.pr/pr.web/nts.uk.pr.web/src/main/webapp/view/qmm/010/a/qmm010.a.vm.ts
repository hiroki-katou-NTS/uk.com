module nts.uk.pr.view.qmm010.a.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm010.share.model;
    import LaborInsuranceOffice = nts.uk.pr.view.qmm010.share.model.LaborInsuranceOffice;
    export class ScreenModel {
        laborInsuranceOfficeList: KnockoutObservableArray<model.ILaborInsuranceOffice> = ko.observableArray([]);
        selectedLaborOfficeCode: KnockoutObservable<string> = ko.observable(null);
        constructor() {
            let self = this;
            for(let i = 1 ; i <= 20 ; i ++ ){
                let identifier = ("000" + i).substring(0 , 4);
                self.laborInsuranceOfficeList.push({laborOfficeCode: identifier, officeName: identifier + "Name"});
            }
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
    }
}

