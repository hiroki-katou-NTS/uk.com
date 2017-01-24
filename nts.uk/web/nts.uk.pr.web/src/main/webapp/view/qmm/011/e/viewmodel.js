var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm011;
                (function (qmm011) {
                    var e;
                    (function (e) {
                        var option = nts.uk.ui.option;
                        var InsuranceBusinessTypeDto = e.service.model.InsuranceBusinessTypeDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    self.accidentInsuranceRateModel = nts.uk.ui.windows.getShared("accidentInsuranceRateModel");
                                }
                                ScreenModel.prototype.updateInsuranceBusinessType = function () {
                                    var self = this;
                                    var insuranceBusinessType;
                                    insuranceBusinessType =
                                        new InsuranceBusinessTypeDto(self.accidentInsuranceRateModel().accidentInsuranceRateBiz1StModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz2NdModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz3RdModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz4ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz5ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz6ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz7ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz8ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz9ThModel.insuranceBusinessType(), self.accidentInsuranceRateModel().accidentInsuranceRateBiz10ThModel.insuranceBusinessType());
                                    e.service.updateInsuranceBusinessType(insuranceBusinessType);
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
                    })(e = qmm011.e || (qmm011.e = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
