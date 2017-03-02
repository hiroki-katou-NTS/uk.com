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
                    var f;
                    (function (f) {
                        var service;
                        (function (service) {
                            var paths = {
                                deleteAccidentInsuranceRate: "pr/insurance/labor/accidentrate/delete"
                            };
                            function deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto) {
                                var dfd = $.Deferred();
                                var data = { accidentInsuranceRateDeleteDto: accidentInsuranceRateDeleteDto };
                                nts.uk.request.ajax(paths.deleteAccidentInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.deleteAccidentInsuranceRate = deleteAccidentInsuranceRate;
                            var model;
                            (function (model) {
                                var AccidentInsuranceRateDeleteDto = (function () {
                                    function AccidentInsuranceRateDeleteDto() {
                                    }
                                    return AccidentInsuranceRateDeleteDto;
                                }());
                                model.AccidentInsuranceRateDeleteDto = AccidentInsuranceRateDeleteDto;
                            })(model = service.model || (service.model = {}));
                        })(service = f.service || (f.service = {}));
                    })(f = qmm011.f || (qmm011.f = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
