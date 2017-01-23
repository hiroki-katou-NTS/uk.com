module nts.uk.pr.view.qmm011.e {
    import option = nts.uk.ui.option;
    import InsuranceBusinessType = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessType;
    import InsuBizRateItemDto = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItemDto;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    export module viewmodel {
        export class ScreenModel {
            accidentInsuranceRateModel: KnockoutObservable<AccidentInsuranceRateModel>;
            rateInputOptions: any;
            textEditorOption: KnockoutObservable<any>;
            constructor() {
                var self = this;
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                self.accidentInsuranceRateModel = nts.uk.ui.windows.getShared("accidentInsuranceRateModel");
            }
        }
    }

}
