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
                        var InsuranceBusinessTypeDto = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessTypeDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    var insuranceBusinessTypeDto = nts.uk.ui.windows.getShared("InsuranceBusinessTypeDto");
                                    self.insuranceBusinessTypeUpdateModel
                                        = ko.observable(new InsuranceBusinessTypeUpdateModel(insuranceBusinessTypeDto));
                                }
                                ScreenModel.prototype.updateInsuranceBusinessType = function () {
                                    var self = this;
                                    var insuranceBusinessType;
                                    insuranceBusinessType =
                                        new InsuranceBusinessTypeDto(self.insuranceBusinessTypeUpdateModel().bizNameBiz1St(), self.insuranceBusinessTypeUpdateModel().bizNameBiz2Nd(), self.insuranceBusinessTypeUpdateModel().bizNameBiz3Rd(), self.insuranceBusinessTypeUpdateModel().bizNameBiz4Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz5Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz6Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz7Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz8Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz9Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz10Th(), self.insuranceBusinessTypeUpdateModel().version());
                                    e.service.updateInsuranceBusinessType(insuranceBusinessType).done(function (data) {
                                        nts.uk.ui.windows.setShared("insuranceBusinessTypeUpdateModel", self.insuranceBusinessTypeUpdateModel());
                                        nts.uk.ui.windows.close();
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var InsuranceBusinessTypeUpdateModel = (function () {
                                function InsuranceBusinessTypeUpdateModel(insuranceBusinessTypeDto) {
                                    this.bizNameBiz1St = ko.observable(insuranceBusinessTypeDto.bizNameBiz1St);
                                    this.bizNameBiz2Nd = ko.observable(insuranceBusinessTypeDto.bizNameBiz2Nd);
                                    this.bizNameBiz3Rd = ko.observable(insuranceBusinessTypeDto.bizNameBiz3Rd);
                                    this.bizNameBiz4Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz4Th);
                                    this.bizNameBiz5Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz5Th);
                                    this.bizNameBiz6Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz6Th);
                                    this.bizNameBiz7Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz7Th);
                                    this.bizNameBiz8Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz8Th);
                                    this.bizNameBiz9Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz9Th);
                                    this.bizNameBiz10Th = ko.observable(insuranceBusinessTypeDto.bizNameBiz10Th);
                                    this.version = ko.observable(insuranceBusinessTypeDto.version);
                                }
                                return InsuranceBusinessTypeUpdateModel;
                            }());
                            viewmodel.InsuranceBusinessTypeUpdateModel = InsuranceBusinessTypeUpdateModel;
                        })(viewmodel = e.viewmodel || (e.viewmodel = {}));
                    })(e = qmm011.e || (qmm011.e = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
