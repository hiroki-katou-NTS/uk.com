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
                        var InsuranceBusinessTypeUpdateDto = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessTypeUpdateDto;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    var insuranceBusinessTypeUpdateDto = nts.uk.ui.windows.getShared("insuranceBusinessTypeUpdateDto");
                                    self.insuranceBusinessTypeUpdateModel = ko.observable(new InsuranceBusinessTypeUpdateModel(insuranceBusinessTypeUpdateDto));
                                }
                                ScreenModel.prototype.updateInsuranceBusinessType = function () {
                                    var self = this;
                                    var insuranceBusinessType;
                                    insuranceBusinessType =
                                        new InsuranceBusinessTypeUpdateDto(self.insuranceBusinessTypeUpdateModel().bizNameBiz1St(), self.insuranceBusinessTypeUpdateModel().bizNameBiz2Nd(), self.insuranceBusinessTypeUpdateModel().bizNameBiz3Rd(), self.insuranceBusinessTypeUpdateModel().bizNameBiz4Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz5Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz6Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz7Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz8Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz9Th(), self.insuranceBusinessTypeUpdateModel().bizNameBiz10Th(), self.insuranceBusinessTypeUpdateModel().version());
                                    e.service.updateInsuranceBusinessType(insuranceBusinessType).done(function (data) {
                                        nts.uk.ui.windows.setShared("insuranceBusinessTypeUpdateModel", self.insuranceBusinessTypeUpdateModel());
                                        nts.uk.ui.windows.close();
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var InsuranceBusinessTypeUpdateModel = (function () {
                                function InsuranceBusinessTypeUpdateModel(insuranceBusinessTypeUpdateDto) {
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
                                    this.version = ko.observable(insuranceBusinessTypeUpdateDto.version);
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
