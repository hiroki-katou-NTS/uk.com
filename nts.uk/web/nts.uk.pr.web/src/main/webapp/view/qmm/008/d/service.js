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
                    var d;
                    (function (d) {
                        var service;
                        (function (service) {
                            var paths = {
                                saveListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate/",
                                getListHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthrate/findAllHealthInsuranceAvgearn",
                            };
                            function save(list) {
                                return nts.uk.request.ajax(paths.saveListHealthInsuranceAvgEarn, list);
                            }
                            service.save = save;
                            function getListHealthInsuranceAvgEarn() {
                                return nts.uk.request.ajax(paths.getListHealthInsuranceAvgEarn);
                            }
                            service.getListHealthInsuranceAvgEarn = getListHealthInsuranceAvgEarn;
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
                        })(service = d.service || (d.service = {}));
                    })(d = qmm008.d || (qmm008.d = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
