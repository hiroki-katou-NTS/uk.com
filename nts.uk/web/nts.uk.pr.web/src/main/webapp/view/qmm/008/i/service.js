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
                    var i;
                    (function (i) {
                        var service;
                        (function (service) {
                            var paths = {
                                updatePensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/update",
                                findPensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/find"
                            };
                            function updatePensionAvgearn(list) {
                                return nts.uk.request.ajax(paths.updatePensionAvgearn, list);
                            }
                            service.updatePensionAvgearn = updatePensionAvgearn;
                            function findPensionAvgearn(id) {
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
                            service.findPensionAvgearn = findPensionAvgearn;
                            var model;
                            (function (model) {
                                var PensionAvgearnValue = (function () {
                                    function PensionAvgearnValue() {
                                    }
                                    return PensionAvgearnValue;
                                }());
                                model.PensionAvgearnValue = PensionAvgearnValue;
                                var HealthInsuranceAvgEarnDto = (function () {
                                    function HealthInsuranceAvgEarnDto() {
                                    }
                                    return HealthInsuranceAvgEarnDto;
                                }());
                                model.HealthInsuranceAvgEarnDto = HealthInsuranceAvgEarnDto;
                            })(model = service.model || (service.model = {}));
                        })(service = i.service || (i.service = {}));
                    })(i = qmm008.i || (qmm008.i = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
