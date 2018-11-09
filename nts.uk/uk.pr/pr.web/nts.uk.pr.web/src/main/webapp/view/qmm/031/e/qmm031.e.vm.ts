module nts.uk.pr.view.qmm031.e.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        lstLifeInsurance: KnockoutObservableArray<ILifeInsurance> = ko.observableArray([]);

        constructor() {
            let self = this,
                dfd = $.Deferred();
            let data = getShared("QMM031_E");
            self.lstLifeInsurance(data.lstData);
            $("#E1_3").focus();
        }

        close() {
            let self = this;
            nts.uk.ui.windows.close();
        }
        execution(){
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.copyEarth(self.lstLifeInsurance()).done(function (data: Array<ILifeInsurance>) {
                dialog.info({messageId: "Msg_20"}).then(() => {
                    nts.uk.ui.windows.close();
                });
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }
    }
    export interface ILifeInsurance {
        lifeInsuranceCode: string;
        lifeInsuranceName: string;
    }
}