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
                        var service;
                        (function (service) {
                            var paths = {
                                updateInsuranceBusinessType: "pr/insurance/labor/businesstype/update"
                            };
                            function updateInsuranceBusinessType(insuranceBusinessType) {
                                var dfd = $.Deferred();
                                var data = { insuranceBusinessType: insuranceBusinessType, comanyCode: "CC0001" };
                                nts.uk.request.ajax(paths.updateInsuranceBusinessType, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                            }
                            service.updateInsuranceBusinessType = updateInsuranceBusinessType;
                            var model;
                            (function (model) {
                                var InsuranceBusinessTypeDto = (function () {
                                    function InsuranceBusinessTypeDto(bizNameBiz1St, bizNameBiz2Nd, bizNameBiz3Rd, bizNameBiz4Th, bizNameBiz5Th, bizNameBiz6Th, bizNameBiz7Th, bizNameBiz8Th, bizNameBiz9Th, bizNameBiz10Th) {
                                        this.bizNameBiz1St = bizNameBiz1St;
                                        this.bizNameBiz2Nd = bizNameBiz2Nd;
                                        this.bizNameBiz3Rd = bizNameBiz3Rd;
                                        this.bizNameBiz4Th = bizNameBiz4Th;
                                        this.bizNameBiz5Th = bizNameBiz5Th;
                                        this.bizNameBiz6Th = bizNameBiz6Th;
                                        this.bizNameBiz7Th = bizNameBiz7Th;
                                        this.bizNameBiz8Th = bizNameBiz8Th;
                                        this.bizNameBiz9Th = bizNameBiz9Th;
                                        this.bizNameBiz10Th = bizNameBiz10Th;
                                    }
                                    return InsuranceBusinessTypeDto;
                                }());
                                model.InsuranceBusinessTypeDto = InsuranceBusinessTypeDto;
                            })(model = service.model || (service.model = {}));
                        })(service = e.service || (e.service = {}));
                    })(e = qmm011.e || (qmm011.e = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
