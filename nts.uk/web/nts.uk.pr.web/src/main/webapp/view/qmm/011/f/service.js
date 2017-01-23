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
                                updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update"
                            };
                            function updateHistoryInfoUnemployeeInsurance(historyInfo) {
                                var dfd = $.Deferred();
                                var data = { historyInfoDto: historyInfo, comanyCode: "CC0001" };
                                nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateHistoryInfoUnemployeeInsurance = updateHistoryInfoUnemployeeInsurance;
                        })(service = f.service || (f.service = {}));
                    })(f = qmm011.f || (qmm011.f = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
