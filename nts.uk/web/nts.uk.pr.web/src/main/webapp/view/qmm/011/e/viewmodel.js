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
                        var InsuranceBusinessType = nts.uk.pr.view.qmm011.a.service.model.InsuranceBusinessType;
                        var BusinessTypeEnum = nts.uk.pr.view.qmm011.a.service.model.BusinessTypeEnum;
                        var InsuBizRateItem = nts.uk.pr.view.qmm011.a.service.model.InsuBizRateItem;
                        var RoundingMethod = nts.uk.pr.view.qmm011.a.service.model.RoundingMethod;
                        var AccidentInsuranceRateModel = nts.uk.pr.view.qmm011.a.viewmodel.AccidentInsuranceRateModel;
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    var selectionRoundingMethod = [new RoundingMethod(0, "切り捨て"),
                                        new RoundingMethod(1, "切り上げ"),
                                        new RoundingMethod(2, "四捨五入"),
                                        new RoundingMethod(3, "五捨六入"),
                                        new RoundingMethod(4, "五捨五超入")];
                                    self.textEditorOption = ko.mapping.fromJS(new option.TextEditorOption());
                                    var insuranceBusinessTypeBiz1St = new InsuranceBusinessType(BusinessTypeEnum.Biz1St, "事業種類名1");
                                    var insuranceBusinessTypeBiz2Nd = new InsuranceBusinessType(BusinessTypeEnum.Biz2Nd, "事業種類名2");
                                    var insuranceBusinessTypeBiz3Rd = new InsuranceBusinessType(BusinessTypeEnum.Biz3Rd, "事業種類名3");
                                    var insuranceBusinessTypeBiz4Th = new InsuranceBusinessType(BusinessTypeEnum.Biz4Th, "事業種類名4");
                                    var insuranceBusinessTypeBiz5Th = new InsuranceBusinessType(BusinessTypeEnum.Biz5Th, "事業種類名5");
                                    var insuranceBusinessTypeBiz6Th = new InsuranceBusinessType(BusinessTypeEnum.Biz6Th, "事業種類名6");
                                    var insuranceBusinessTypeBiz7Th = new InsuranceBusinessType(BusinessTypeEnum.Biz7Th, "事業種類名7");
                                    var insuranceBusinessTypeBiz8Th = new InsuranceBusinessType(BusinessTypeEnum.Biz8Th, "事業種類名8");
                                    var insuranceBusinessTypeBiz9Th = new InsuranceBusinessType(BusinessTypeEnum.Biz9Th, "事業種類名9");
                                    var insuranceBusinessTypeBiz10Th = new InsuranceBusinessType(BusinessTypeEnum.Biz10Th, "事業種類名11");
                                    var insuBizRateItemBiz1St = new InsuBizRateItem(BusinessTypeEnum.Biz1St, 60, 2);
                                    var insuBizRateItemBiz2Nd = new InsuBizRateItem(BusinessTypeEnum.Biz2Nd, 3, 3);
                                    var insuBizRateItemBiz3Rd = new InsuBizRateItem(BusinessTypeEnum.Biz3Rd, 15, 0);
                                    var insuBizRateItemBiz4Th = new InsuBizRateItem(BusinessTypeEnum.Biz4Th, 6.5, 2);
                                    var insuBizRateItemBiz5Th = new InsuBizRateItem(BusinessTypeEnum.Biz5Th, 13, 3);
                                    var insuBizRateItemBiz6Th = new InsuBizRateItem(BusinessTypeEnum.Biz6Th, 49, 0);
                                    var insuBizRateItemBiz7Th = new InsuBizRateItem(BusinessTypeEnum.Biz7Th, 3, 2);
                                    var insuBizRateItemBiz8Th = new InsuBizRateItem(BusinessTypeEnum.Biz8Th, 7, 3);
                                    var insuBizRateItemBiz9Th = new InsuBizRateItem(BusinessTypeEnum.Biz9Th, 2.5, 0);
                                    var insuBizRateItemBiz10Th = new InsuBizRateItem(BusinessTypeEnum.Biz10Th, 3, 0);
                                    self.lstInsuranceBusinessType = [insuranceBusinessTypeBiz1St, insuranceBusinessTypeBiz2Nd, insuranceBusinessTypeBiz3Rd, insuranceBusinessTypeBiz4Th,
                                        insuranceBusinessTypeBiz5Th, insuranceBusinessTypeBiz6Th, insuranceBusinessTypeBiz7Th, insuranceBusinessTypeBiz8Th, insuranceBusinessTypeBiz9Th, insuranceBusinessTypeBiz10Th];
                                    self.lstInsuBizRateItem = [insuBizRateItemBiz1St, insuBizRateItemBiz2Nd, insuBizRateItemBiz3Rd, insuBizRateItemBiz4Th, insuBizRateItemBiz5Th,
                                        insuBizRateItemBiz6Th, insuBizRateItemBiz7Th, insuBizRateItemBiz8Th, insuBizRateItemBiz9Th, insuBizRateItemBiz10Th];
                                    self.rateInputOptions = ko.mapping.fromJS(new nts.uk.ui.option.NumberEditorOption({
                                        grouplength: 3,
                                        decimallength: 2
                                    }));
                                    self.accidentInsuranceRateModel = ko.observable(new AccidentInsuranceRateModel(self.lstInsuBizRateItem, self.lstInsuranceBusinessType, self.rateInputOptions, selectionRoundingMethod));
                                }
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
