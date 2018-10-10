module nts.uk.pr.view.qmm010.b.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm010.share.model;
    import service = nts.uk.pr.view.qmm010.b.service;
    export class ScreenModel {
        socialInsuranceOfficeList: KnockoutObservableArray<model.ISocialInsuranceOffice> = ko.observableArray([]);
        selectedSocialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        constructor() {
            let self = this;
        }
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.findAllSocialOffice().done(function(data) {
                self.socialInsuranceOfficeList(data);
                if (data.length == 0) {
                    dialog.alertError({messageId: 'Msg_37'});
                } else {
                    self.selectedSocialInsuranceCode(data[0].officeCode);
                }
                dfd.resolve();
            }).fail(function(err) {
                dfd.reject();
            }).always(function() {
                block.clear();
            });
            return dfd.promise();
        }
        decideCloneData () {
            let self = this;
            setShared('QMM010_A_PARAMS', {selectedSocialInsuranceCode: self.selectedSocialInsuranceCode()});
            nts.uk.ui.windows.close();
        }

        cancelCloneData () {
            nts.uk.ui.windows.close();
        }
    }
}

