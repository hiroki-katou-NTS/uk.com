module nts.uk.pr.view.qmm011.e {
    import option = nts.uk.ui.option;
    import InsuranceBusinessType = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessType;
    import InsuBizRateItemDto = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItemDto;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    import AccidentInsuranceRateDetailModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateDetailModel;
    import InsuranceBusinessTypeDto = service.model.InsuranceBusinessTypeDto;
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
            updateInsuranceBusinessType() {
                var self = this;
                var insuranceBusinessType: InsuranceBusinessTypeDto;
                insuranceBusinessType =
                    new InsuranceBusinessTypeDto(self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.insuranceBusinessType(),
                        self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.insuranceBusinessType());
                service.updateInsuranceBusinessType(insuranceBusinessType);
            }
        }
    }

}
