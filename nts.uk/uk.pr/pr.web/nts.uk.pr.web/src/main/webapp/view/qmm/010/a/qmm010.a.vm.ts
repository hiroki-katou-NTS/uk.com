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
        selectedLaborOffice: KnockoutObservable<model.LaborInsuranceOffice> = ko.observable(new model.LaborInsuranceOffice(null));
        screenMode: KnockoutObservable<number> = ko.observable(model.SCREEN_MODE.NEW);
        constructor() {
            let self = this;
            self.selectedLaborOfficeCode.subscribe(newValue => {
                if (newValue) self.showDataByOfficeCode(newValue);
            })
        }
        changeMode () {
            let self = this;
            let currentScreenMode = self.screenMode();
            let newScreenMode = currentScreenMode * -1 + 1;
            console.log(newScreenMode);
            self.screenMode(newScreenMode);
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.findAllOffice().done(function(data) {
                if (data && data.length) {
                    block.clear();
                    self.laborInsuranceOfficeList(data);
                    self.selectedLaborOfficeCode(data[0].laborOfficeCode);
                }
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                dialog.alertError(err.message);
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }

        showDataByOfficeCode (officeCode: string) {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.findByOfficeCode(officeCode).done(function(data: model.ILaborInsuranceOffice) {
                if (data) {
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                    self.selectedLaborOffice(new model.LaborInsuranceOffice(data));
                } else {
                    self.screenMode(model.SCREEN_MODE.NEW);
                }
                block.clear();
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
                dialog.alertError(err.message);
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }

        searchPostalCode () {

        }

        createNewOffice () {

        }

        registerOffice () {

        }

        printPDF () {

        }

        readFromSocialInsuranceOffice () {

        }

        masterCorrection () {

        }

        deleteOffice () {

        }
    }
}

