module nts.uk.pr.view.qmm011.e {
    import option = nts.uk.ui.option;
    import InsuranceBusinessType = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessType;
    import InsuBizRateItemDto = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItemDto;
    import AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
    import AccidentInsuranceRateDetailModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateDetailModel;
    import InsuranceBusinessTypeUpdateDto = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessTypeUpdateDto;
    export module viewmodel {
        export class ScreenModel {
            insuranceBusinessTypeUpdateModel: KnockoutObservable<InsuranceBusinessTypeUpdateModel>;
            rateInputOptions: any;
            textEditorOption: KnockoutObservable<any>;
            constructor() {
                var self = this;
                self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                var insuranceBusinessTypeUpdateDto = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateDto");
                self.insuranceBusinessTypeUpdateModel = ko.observable(new InsuranceBusinessTypeUpdateModel(insuranceBusinessTypeUpdateDto));
            }
            updateInsuranceBusinessType() {
                var self = this;
                nts.uk.ui.windows.close();
                var insuranceBusinessType: InsuranceBusinessTypeUpdateDto;
                insuranceBusinessType =
                    new InsuranceBusinessTypeUpdateDto(self.insuranceBusinessTypeUpdateModel().bizNameBiz1St(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz2Nd(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz3Rd(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz4Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz5Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz6Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz7Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz8Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz9Th(),
                        self.insuranceBusinessTypeUpdateModel().bizNameBiz10Th());
                service.updateInsuranceBusinessType(insuranceBusinessType);

            }
        }
        export class InsuranceBusinessTypeUpdateModel {
            bizNameBiz1St: KnockoutObservable<string>;
            bizNameBiz2Nd: KnockoutObservable<string>;
            bizNameBiz3Rd: KnockoutObservable<string>;
            bizNameBiz4Th: KnockoutObservable<string>;
            bizNameBiz5Th: KnockoutObservable<string>;
            bizNameBiz6Th: KnockoutObservable<string>;
            bizNameBiz7Th: KnockoutObservable<string>;
            bizNameBiz8Th: KnockoutObservable<string>;
            bizNameBiz9Th: KnockoutObservable<string>;
            bizNameBiz10Th: KnockoutObservable<string>;
            constructor(insuranceBusinessTypeUpdateDto: InsuranceBusinessTypeUpdateDto) {
                this.bizNameBiz1St = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz1St);
                this.bizNameBiz2Nd = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz2Nd);
                this.bizNameBiz3Rd = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz3Rd);
                this.bizNameBiz4Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz4Th);
                this.bizNameBiz5Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz5Th);
                this.bizNameBiz6Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz6Th);
                this.bizNameBiz7Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz7Th);
                this.bizNameBiz8Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz8Th);
                this.bizNameBiz9Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz9Th);
                this.bizNameBiz10Th = ko.observable(insuranceBusinessTypeUpdateDto.bizNameBiz10Th);
            }
        }
    }

}
