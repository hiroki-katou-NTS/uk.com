module nts.uk.com.view.cmf002.p.viewmodel {
    import model = cmf001.share.model;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        //wizard
        stepList: Array<NtsWizardStep> = [];
        stepSelected: KnockoutObservable<NtsWizardStep> = ko.observable(null);
        activeStep: KnockoutObservable<number> = ko.observable(0);

        listCondition: KnockoutObservableArray<model.StandardAcceptanceConditionSetting> = ko.observableArray([]);
        selectedConditionCd: KnockoutObservable<string> = ko.observable('');
        selectedConditionName: KnockoutObservable<string> = ko.observable('');
        selectedConditionLineNumber: KnockoutObservable<number> = ko.observable(0);
        selectedConditionStartLine: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            var self = this;

            //起動する
            self.stepList = [
                { content: '.step-1' },
                { content: '.step-2' },
                { content: '.step-3' }
            ];
            self.stepSelected = ko.observable({ id: 'step-1', content: '.step-1' });

            self.loadListCondition();
        }

        next() {
            $('#ex_output_wizard').ntsWizard("next");
        }
        previous() {
            $('#ex_output_wizard').ntsWizard("prev");
        }

        loadListCondition() {
            let self = this;
            block.grayout();

            self.listCondition([]);

            service.getConditionList().done(function(data: Array<any>) {
                self.listCondition.removeAll();
                if (data && data.length) {
                    let _rspList: Array<model.StandardAcceptanceConditionSetting> = _.map(data, rsp => {
                        return new model.StandardAcceptanceConditionSetting(rsp.conditionSetCd, rsp.conditionSetName, rsp.deleteExistData, rsp.acceptMode, rsp.csvDataLineNumber, rsp.csvDataStartLine, rsp.deleteExtDataMethod);
                    });
                    self.listCondition(_rspList);

                    self.selectedConditionCd(self.listCondition()[0].conditionSettingCode());
                    self.selectedConditionName(self.listCondition()[0].conditionSettingName());
                }
                else {
                    dialog({ messageId: "Msg_907" });
                }
            }).fail(function(error) {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }
    }
}