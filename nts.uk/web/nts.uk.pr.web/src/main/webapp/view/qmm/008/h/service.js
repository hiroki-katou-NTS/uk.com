var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var h;
                    (function (h) {
                        var service;
                        (function (service) {
                            var paths = {
                                updateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/update",
                                findHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthavgearn/find",
                                findHealthInsuranceRate: "ctx/pr/core/insurance/social/healthrate/find",
                            };
                            function updateHealthInsuranceAvgearn(list) {
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, list);
                            }
                            service.updateHealthInsuranceAvgearn = updateHealthInsuranceAvgearn;
                            function findHealthInsuranceRate(id) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHealthInsuranceRate + '/' + id)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                                ;
                            }
                            service.findHealthInsuranceRate = findHealthInsuranceRate;
                            function findHealthInsuranceAvgEarn(id) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + id)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                                ;
                            }
                            service.findHealthInsuranceAvgEarn = findHealthInsuranceAvgEarn;
                            var model;
                            (function (model) {
                                var HealthInsuranceAvgEarnValue = (function () {
                                    function HealthInsuranceAvgEarnValue() {
                                    }
                                    return HealthInsuranceAvgEarnValue;
                                }());
                                model.HealthInsuranceAvgEarnValue = HealthInsuranceAvgEarnValue;
                                var HealthInsuranceAvgEarnDto = (function () {
                                    function HealthInsuranceAvgEarnDto(historyId, levelCode, companyAvg, personalAvg) {
                                        this.historyId = historyId;
                                        this.levelCode = levelCode;
                                        this.companyAvg = companyAvg;
                                        this.personalAvg = personalAvg;
                                    }
                                    ;
                                    return HealthInsuranceAvgEarnDto;
                                }());
                                model.HealthInsuranceAvgEarnDto = HealthInsuranceAvgEarnDto;
                                var HealthInsuranceRate = (function () {
                                    function HealthInsuranceRate() {
                                    }
                                    return HealthInsuranceRate;
                                }());
                                model.HealthInsuranceRate = HealthInsuranceRate;
                                var InsuranceRateItem = (function () {
                                    function InsuranceRateItem() {
                                    }
                                    return InsuranceRateItem;
                                }());
                                model.InsuranceRateItem = InsuranceRateItem;
                                var HealthInsuranceRounding = (function () {
                                    function HealthInsuranceRounding() {
                                    }
                                    return HealthInsuranceRounding;
                                }());
                                model.HealthInsuranceRounding = HealthInsuranceRounding;
                            })(model = service.model || (service.model = {}));
                        })(service = h.service || (h.service = {}));
                    })(h = qmm008.h || (qmm008.h = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
